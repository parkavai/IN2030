package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

public class AspExprStmt extends AspSmallStmt {
    AspExpr expr;

    AspExprStmt(int n){
        super(n);
    }

    public static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");

        // -- Must be changed in part 2:
        AspExprStmt exprStmt = new AspExprStmt(s.curLineNum());
        exprStmt.expr = AspExpr.parse(s);

        leaveParser("expr stmt");
        return exprStmt;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 3:
        return expr.eval(curScope);
    }
}
