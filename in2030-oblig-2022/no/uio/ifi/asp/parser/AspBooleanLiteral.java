package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLiteral extends AspAtom {
    Boolean value;
    String printValue;

    AspBooleanLiteral(int n){
        super(n);
    }
    
    static AspBooleanLiteral parse(Scanner s){
        enterParser("boolean literal");

        AspBooleanLiteral bool = new AspBooleanLiteral(s.curLineNum());
        if(s.curToken().kind == trueToken){
            bool.printValue = "True"; bool.value = true; skip(s, trueToken);
        }
        else{
            bool.printValue = "False"; bool.value = false; skip(s, falseToken);
        }
        
        // skip(s, s.curToken().kind);
        leaveParser("boolean literal");
        return bool;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(printValue);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeBoolValue(value);
    }
}

