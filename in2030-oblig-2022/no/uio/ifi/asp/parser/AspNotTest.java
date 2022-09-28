package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNotTest extends AspSyntax {
    AspComparison comp;
    Boolean isNot = false;

    AspNotTest(int n) {
        super(n);
    }

    static AspNotTest parse(Scanner s) {
        enterParser("not test");

        AspNotTest nnt = new AspNotTest(s.curLineNum());
        if(s.curToken().kind == notToken){
            skip(s, notToken);
            nnt.isNot = true;
        }
        nnt.comp = AspComparison.parse(s);

        leaveParser("not test");
        return nnt;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        if(isNot){
            prettyWrite(" not ");
        }
        comp.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
