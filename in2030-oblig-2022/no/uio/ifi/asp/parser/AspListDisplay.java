package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();
    Boolean isExpr = false;

    AspListDisplay(int n){
        super(n);
    }
    
    static AspListDisplay parse(Scanner s){
        enterParser("list display");

        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum()); skip(s, leftBracketToken);

        if(s.curToken().kind != rightBracketToken){
            listDisplay.isExpr = true;
            while(true){
                listDisplay.expr.add(AspExpr.parse(s));
                if(s.curToken().kind != commaToken) break;
                skip(s, commaToken);
            }
        }

        skip(s, rightBracketToken);
        leaveParser("list display");
        return listDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");
        if(expr.size() > 1){
            for(int i = 0; i < expr.size(); i++){
                expr.get(i).prettyPrint();
                if(i < expr.size()-1){
                    prettyWrite(", ");
                }
            }
        }
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> v = new ArrayList<>();
        for(int i = 0; i < expr.size(); i++){
            v.add(expr.get(i).eval(curScope));
        }
        return new RuntimeListValue(v);
    }
}
