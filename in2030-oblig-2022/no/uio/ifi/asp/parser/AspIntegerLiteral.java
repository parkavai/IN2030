package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIntegerLiteral extends AspAtom {
    Long intValue;

    AspIntegerLiteral(int n){
        super(n);
    }
    
    static AspIntegerLiteral parse(Scanner s){
        enterParser("integer literal");

        AspIntegerLiteral integer = new AspIntegerLiteral(s.curLineNum());
        String value = String.valueOf(s.curToken().stringLit);
        if(value.charAt(0) == '0' && value.length() > 1){
            parserError("Asp only accepts integer values \'0\' or every other number!", s.curLineNum());
        }
        else{
            integer.intValue = s.curToken().integerLit;
        }
        skip(s, integerToken);
        leaveParser("integer literal");
        return integer;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite(String.valueOf(intValue));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return new RuntimeIntValue(intValue);
    }
}
