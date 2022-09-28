package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax {
    ArrayList<AspPrimary> primaryList = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprList = new ArrayList<>();
    ArrayList<AspFactorPrefix> factorPrefixList = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {
        enterParser("factor");

        AspFactor fact = new AspFactor(s.curLineNum());
        while (true) {
            if (s.isFactorPrefix()){
                fact.factorPrefixList.add(AspFactorPrefix.parse(s));
            }
            fact.primaryList.add(AspPrimary.parse(s));

            if(s.isFactorOpr() == false) break;
            fact.factorOprList.add(AspFactorOpr.parse(s));
        }

        leaveParser("factor");
        return fact;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        for(int i = 0; i < primaryList.size(); i++){
            if(i < factorPrefixList.size()){
                factorPrefixList.get(i).prettyPrint();
            }
            primaryList.get(i).prettyPrint();
            if(i < factorOprList.size()){
                factorOprList.get(i).prettyPrint();
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
