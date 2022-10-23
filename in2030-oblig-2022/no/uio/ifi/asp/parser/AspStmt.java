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
			case integerToken:
			case floatToken:
			case stringToken: 
			case trueToken: 
			case falseToken: 
			case noneToken:
			case leftParToken:
			case leftBraceToken:
			case leftBracketToken:
            case notToken:
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
