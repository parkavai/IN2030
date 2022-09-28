package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();

    AspListDisplay(int n){
        super(n);
    }
    
    static AspListDisplay parse(Scanner s){
        enterParser("list display");

        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum());
        skip(s, leftBracketToken);

        if(s.curToken().kind != rightBracketToken){
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
        // -- Must be changed in part 2:
        prettyWrite("[");
        if(expr.size() > 1){
            for(int i = 0; i < expr.size(); i++){
                expr.get(i).prettyPrint();
                prettyWrite(",");
            }
        }
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
