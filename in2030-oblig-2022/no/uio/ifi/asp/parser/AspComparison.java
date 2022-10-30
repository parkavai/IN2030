package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComparison extends AspSyntax {
    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compList = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspComparison parse(Scanner s) {
        enterParser("comparison");

        AspComparison comp = new AspComparison(s.curLineNum());
        while (true) {
            comp.terms.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            comp.compList.add(AspCompOpr.parse(s));
        }
    
        leaveParser("comparison");
        return comp;
    }

    @Override
    public void prettyPrint() {
        terms.get(0).prettyPrint();
        for(int i = 1; i < terms.size(); i++){
            compList.get(i-1).prettyPrint();
            terms.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = terms.get(0).eval(curScope);
        for(int i = 1; i < terms.size(); i++){
            v = terms.get(i-1).eval(curScope);
            TokenKind k = compList.get(i-1).kind; 
            RuntimeValue v2 = terms.get(i).eval(curScope);
            switch (k){
                case lessToken:
                    v = v.evalLess(v2, this); break;
                case greaterToken:
                    v = v.evalGreater(v2, this); break;
                case doubleEqualToken:
                    v = v.evalEqual(v2, this); break;
                case greaterEqualToken:
                    v = v.evalGreaterEqual(v2, this); break;
                case lessEqualToken:
                    v = v.evalLessEqual(v2, this); break;
                case notEqualToken:
                    v = v.evalNotEqual(v2, this); break;
                default:
                    Main.panic("Illegal comparison operator: " + k + "!");
            }
            if (! v.getBoolValue("and operand", this))
                return v;
        }
        return v;
    }
}
