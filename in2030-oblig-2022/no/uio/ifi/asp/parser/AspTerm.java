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

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
