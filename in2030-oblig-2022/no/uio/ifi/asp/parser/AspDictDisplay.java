package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspDictDisplay extends AspAtom{
    ArrayList<AspStringLiteral> strList = new ArrayList<>();
    ArrayList<AspExpr> exprList = new ArrayList<>();
    Boolean isString = false;

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
            // There is  a string literal
            while(true){
                dictDisp.strList.add(AspStringLiteral.parse(s));
                // Must you check if there is a ":" token?
                skip(s, colonToken);
                dictDisp.exprList.add(AspExpr.parse(s));
                if(s.curToken().kind != commaToken) break; 
            }
        }
        skip(s, TokenKind.rightBraceToken); // Skip past the second parenthesis ')'

        leaveParser("dict display");
        return dictDisp;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("{ ");
        if(isString){
            strList.get(0).prettyPrint();
            prettyWrite(": ");
            for(int i = 1; i < strList.size(); i++){
                System.out.print(": ");
                exprList.get(i-1).prettyPrint();
                System.out.print(", ");
                strList.get(i).prettyPrint();
            }
        }
        prettyWrite("} ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}