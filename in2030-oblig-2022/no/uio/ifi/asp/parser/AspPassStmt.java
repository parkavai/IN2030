package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPassStmt extends AspSmallStmt{

    AspPassStmt(int n){
        super(n);
    }

    static AspPassStmt parse(Scanner s){
        enterParser("pass stmt");
        
        AspPassStmt passStmt= new AspPassStmt(s.curLineNum()); skip(s, passToken);

        leaveParser("pass stmt");
        return passStmt;
    }
    
    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("pass");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }

}
