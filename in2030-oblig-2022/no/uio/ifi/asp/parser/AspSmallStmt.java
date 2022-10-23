package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspSmallStmt extends AspSmallStmtList {

    AspSmallStmt(int n){
        super(n);
    }

    static AspSmallStmt parse(Scanner s){
        enterParser("small stmt");
        
        AspSmallStmt sss = null;
        switch (s.curToken().kind) {
            case integerToken:
			case floatToken:
			case stringToken: 
			case trueToken: 
			case falseToken: 
			case noneToken:
			case leftParToken:
			case leftBraceToken:
			case leftBracketToken:
            case nameToken:
            if(s.anyEqualToken()){
                sss = AspAssignment.parse(s); break; 
            }
            else{
                sss = AspExprStmt.parse(s); break;
            }
            case globalToken:
            sss = AspGlobalStmt.parse(s); break;
            case passToken:
            sss = AspPassStmt.parse(s); break;
            case returnToken:
            sss = AspReturn.parse(s); break;
            default:
            parserError("Expected an expression stmt but found a " +
            s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("small stmt");
        return sss;
    }

}
