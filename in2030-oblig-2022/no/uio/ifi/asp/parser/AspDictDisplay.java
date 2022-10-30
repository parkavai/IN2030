package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> strList = new ArrayList<>();
    ArrayList<AspExpr> exprList = new ArrayList<>();
    Boolean isString = false;
    ArrayList<String> commaList = new ArrayList<>();

    AspDictDisplay(int n){
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        enterParser("dict display");

        AspDictDisplay dictDisp = new AspDictDisplay(s.curLineNum());
        skip(s, TokenKind.leftBraceToken); 
        // Skip the first curly bracket '{'
        
        /**
         * Maybe there isn´t a string literal so we must check if the next token is a string 
         * literal or not. 
         */
        if(s.curToken().kind != rightBraceToken){
            dictDisp.isString = true;
            // There is a string literal
            while(true){
                dictDisp.strList.add(AspStringLiteral.parse(s));
                skip(s, colonToken);
                dictDisp.exprList.add(AspExpr.parse(s));
                if(s.curToken().kind != commaToken) break; 
                skip(s, commaToken);
                dictDisp.commaList.add(", ");
            }
        }
        skip(s, TokenKind.rightBraceToken); // Skip past the second parenthesis ')'

        leaveParser("dict display");
        return dictDisp;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");
        if(isString){
            for(int i = 0; i < strList.size(); i++){
                strList.get(i).prettyPrint();
                prettyWrite(":");
                exprList.get(i).prettyPrint();
                if(i < commaList.size()){
                    prettyWrite(commaList.get(i));
                }
            }
        }
        prettyWrite("}");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        HashMap<String, RuntimeValue> v = new HashMap<>();
        for(int i = 0; i < exprList.size(); i++){
            String string = strList.get(i).string;
            RuntimeValue expr = exprList.get(i).eval(curScope);
            v.put(string, expr);
        }
        return new RuntimeDictValue(v);
    }
}