package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    AspName name;
    AspExpr expr;
    AspSuite suite;

    AspForStmt(int n){
        super(n);
    }
    
    static AspForStmt parse(Scanner s){
        enterParser("for stmt");

        AspForStmt forStmt = new AspForStmt(s.curLineNum());
        skip(s, forToken);
        forStmt.name = AspName.parse(s);
        skip(s, inToken);
        forStmt.expr = AspExpr.parse(s);
        skip(s, colonToken);
        forStmt.suite = AspSuite.parse(s);
        
        leaveParser("for stmt");
        return forStmt;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("for "); name.prettyPrint();
        prettyWrite(" in "); expr.prettyPrint();
        prettyWrite(":"); suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
