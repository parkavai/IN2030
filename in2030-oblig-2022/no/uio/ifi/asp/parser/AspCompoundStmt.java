package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspCompoundStmt extends AspSyntax{

    AspCompoundStmt(int n){
        super(n);
    }

    static AspCompoundStmt parse(Scanner s){
        enterParser("compound stmt");
        
        AspCompoundStmt css = null;
        switch (s.curToken().kind) {
            // First check if it is a compound stmt
            case forToken:
            css = AspForStmt.parse(s); break; 
            case ifToken:
            css = AspIfStmt.parse(s); break;
            case whileToken:
            css = AspWhileStmt.parse(s); break;
            case defToken:
            css = AspFuncDef.parse(s); break;
            default:
            parserError("Expected an expression stmt but found a " +
            s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("compund stmt");
        return css;
    }

}