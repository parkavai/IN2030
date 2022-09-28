package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom{
    AspExpr expr;

    AspInnerExpr(int n){
        super(n);
    }

    static AspInnerExpr parse(Scanner s){
        enterParser("inner expr");

        AspInnerExpr innExpr = new AspInnerExpr(s.curLineNum());
        skip(s, leftParToken); // Skip the first parenthesis '('
        innExpr.expr = AspExpr.parse(s);
        skip(s, rightParToken); // Skip past the second parenthesis ')'

        leaveParser("inner expr");
        return innExpr;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("("); expr.prettyPrint(); prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
