package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspStmt extends AspSyntax {
    AspSmallStmtList smallStmtList;
    AspCompoundStmt compoundStmt;
    Boolean isSmall = false;

    AspStmt(int n){
        super(n);
    }

    static AspStmt parse(Scanner s){
        enterParser("stmt");
        
        AspStmt ss = null;
        switch (s.curToken().kind) {
            // First check if it is small stmt
            case nameToken:
            case andToken:
            case globalToken:
            case passToken:
            case returnToken:
            ss.isSmall = true;
            ss.smallStmtList = AspSmallStmtList.parse(s); break;
            // Check if it is compound stmt
            case forToken:
            case ifToken:
            case whileToken:
            case defToken:
            ss.compoundStmt = AspCompoundStmt.parse(s); break; 
            default:
            parserError("Expected an expression stmt but found a " +
            s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("stmt");
        return ss;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        if(isSmall){
            smallStmtList.prettyPrint();
        }
        else{
            compoundStmt.prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
    
}
