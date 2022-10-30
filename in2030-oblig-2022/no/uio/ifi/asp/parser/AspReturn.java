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
        prettyWrite("return ");
        expr.prettyPrint();
    }

    // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h21/forelesninger/uke-45-utdeling.pdf
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = expr.eval(curScope);
        trace("return "+ v.showInfo());
        // Ensures that we cancel the chain of eval-methods run since we have come to a "return"-stmt to return the value. 
        throw new RuntimeReturnValue(v,lineNum);
    }

}