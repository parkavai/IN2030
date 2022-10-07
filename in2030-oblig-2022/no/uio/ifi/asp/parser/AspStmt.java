package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspStmt extends AspSyntax {

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
            ss = AspSmallStmtList.parse(s); break;
            // Check if it is compound stmt
            case forToken:
            case ifToken:
            case whileToken:
            case defToken:
            ss = AspCompoundStmt.parse(s); break; 
            default:
            parserError("Expected an expression stmt but found a " +
            s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("stmt");
        return ss;
    }
    
}
