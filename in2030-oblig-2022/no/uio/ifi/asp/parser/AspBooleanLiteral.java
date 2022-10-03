package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLiteral extends AspAtom {
    String boolValue;

    AspBooleanLiteral(int n){
        super(n);
    }
    
    static AspBooleanLiteral parse(Scanner s){
        enterParser("boolean literal");

        AspBooleanLiteral bool = new AspBooleanLiteral(s.curLineNum());
        if(s.curToken().kind == trueToken){
            bool.boolValue = "True"; skip(s, trueToken);
        }
        else{
            bool.boolValue = "False"; skip(s, falseToken);
        }
        
        // skip(s, s.curToken().kind);
        leaveParser("boolean literal");
        return bool;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite(boolValue);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}

