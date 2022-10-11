package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factors = new ArrayList<>();
    ArrayList<AspTermOpr> termOprList = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term");

        AspTerm term = new AspTerm(s.curLineNum());
        while (true) {
            term.factors.add(AspFactor.parse(s));
            if (s.isTermOpr() == false) break;
            term.termOprList.add(AspTermOpr.parse(s));
        }
        
        leaveParser("term");
        return term;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        factors.get(0).prettyPrint();
        for(int i = 1; i < factors.size(); i++){
            termOprList.get(i-1).prettyPrint();
            factors.get(i).prettyPrint();
        }
    }
    
    // Copied from Dag Langmyhr: https://screencast.uninett.no/relay/ansatt/daguio.no/2019/17.10/2746600/IN2030-42-1_-_20191017_110157_39.html
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        RuntimeValue v = factors.get(0).eval(curScope);
        for (int i = 1; i < factors.size(); ++i) {
            TokenKind k = termOprList.get(i-1).kind;
            switch (k) {
                case minusToken:
                    v = v.evalSubtract(factors.get(i).eval(curScope), this); break;
                case plusToken:
                    v = v.evalAdd(factors.get(i).eval(curScope), this); break;
            default:
                Main.panic("Illegal term operator: " + k + "!");
            }
        }
        return v;
    }
}
