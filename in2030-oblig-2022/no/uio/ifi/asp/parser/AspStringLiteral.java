package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspStringLiteral extends AspAtom {
    String string;

    AspStringLiteral(int n){
        super(n);
    }
    
    static AspStringLiteral parse(Scanner s){
        enterParser("string literal");

        AspStringLiteral string_literal = new AspStringLiteral(s.curLineNum());
        string_literal.string = s.curToken().stringLit; 
        // Must propably solve issue in where you have to check if string ends with " or ', If not then we write an error
        // String not terminated
        /* 
        if(string_literal.string != ""){
            if(check.charAt(check.length() - 1) != '\'' || check.charAt(check.length() - 1) != '\"' ){
                parserError("String literal not terminated!", s.curLineNum());
            }
        }
        */
        skip(s, stringToken);

        leaveParser("string literal");
        return string_literal;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        if(string.isEmpty()){
            prettyWrite(string);
        }
        else if(string.charAt(0) == '\''){
            prettyWrite("\'" + string + "\'");
        }
        else{
            prettyWrite("\"" + string + "\"");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
