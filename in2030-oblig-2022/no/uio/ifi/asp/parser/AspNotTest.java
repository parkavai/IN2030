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
        if(isNot){
            prettyWrite("not ");
        }
        comp.prettyPrint();
    }

    // Copied from Dag Langmyhr: https://screencast.uninett.no/relay/ansatt/daguio.no/2019/17.10/2746600/IN2030-42-1_-_20191017_110157_39.html
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = comp.eval(curScope);
        if(isNot){
            v = v.evalNot(this);
        }
        return v;
    }
}
