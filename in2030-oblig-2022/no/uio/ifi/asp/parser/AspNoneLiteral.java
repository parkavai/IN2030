package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNoneLiteral extends AspAtom {

    AspNoneLiteral(int n){
        super(n);
    }
    
    static AspNoneLiteral parse(Scanner s){
        enterParser("none literal");

        AspNoneLiteral none = new AspNoneLiteral(s.curLineNum()); skip(s, noneToken);

        leaveParser("none literal");
        return none;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite(" none");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return new RuntimeNoneValue();
    }
}
