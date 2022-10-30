package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFloatLiteral extends AspAtom {
    Double floatValue;

    AspFloatLiteral(int n){
        super(n);
    }
    
    static AspFloatLiteral parse(Scanner s){
        enterParser("float literal");

        AspFloatLiteral floatLiteral = new AspFloatLiteral(s.curLineNum());
        floatLiteral.floatValue = s.curToken().floatLit;
        skip(s, floatToken);

        leaveParser("float literal");
        return floatLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(String.valueOf(floatValue));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(floatValue);
    }
}
