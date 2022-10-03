package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturn extends AspSmallStmt{
    AspExpr expr;

    AspReturn(int n){
        super(n);
    }

    static AspReturn parse(Scanner s){
        enterParser("return stmt");
        
        AspReturn returnStmt= new AspReturn(s.curLineNum()); skip(s, returnToken);
        returnStmt.expr = AspExpr.parse(s);

        leaveParser("return stmt");
        return returnStmt;
    }
    
    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("return ");
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }

}