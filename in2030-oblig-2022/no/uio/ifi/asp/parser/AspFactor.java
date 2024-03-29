package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

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
        RuntimeValue v = primaryList.get(0).eval(curScope);
        TokenKind k;
        if(factorPrefixList.size() > 0){
            k = factorPrefixList.get(0).kind;
            switch (k){
                case plusToken:
                    v = v.evalPositive(this); break;
                case minusToken:
                    v = v.evalNegate(this); break;
                default:
                    Main.panic("Illegal factorPrefix operator: " + k + "!");
            }
        }
        for(int i = 1; i < primaryList.size(); i++){
            k = factorOprList.get(i-1).kind;
            RuntimeValue v2 = primaryList.get(i).eval(curScope);
            switch (k) {
                case astToken:
                    v = v.evalMultiply(v2, this); break; 
                case slashToken:
                    v = v.evalDivide(v2, this); break;
                case percentToken:
                    v = v.evalModulo(v2, this); break; 
                case doubleSlashToken:
                    v = v.evalIntDivide(v2, this); break;
                default:
                    Main.panic("Illegal factor operator: " + k + "!");
            }
            if(i < factorPrefixList.size()){
                k = factorPrefixList.get(i).kind;
                switch (k) {
                    case plusToken:
                        v = v.evalPositive(this); break;
                    case minusToken:
                        v = v.evalNegate(this); break;
                    default:
                        Main.panic("Illegal factorPrefix operator: " + k + "!");
                }
            }
        }
        return v; 
    }
}
