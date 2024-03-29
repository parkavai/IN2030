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

        AspStringLiteral stringLiteral = new AspStringLiteral(s.curLineNum());
        stringLiteral.string = s.curToken().stringLit; 
        skip(s, stringToken);

        leaveParser("string literal");
        return stringLiteral;
    }

    @Override
    public void prettyPrint() {
        if(string.isEmpty()){
            prettyWrite("");
        }
        else if(string.charAt(0) == '\''){
            prettyWrite("\'" + string + "\'");
        }
        prettyWrite("\"" + string + "\"");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeStringValue(string);
    }
}
