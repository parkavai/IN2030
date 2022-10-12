package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();
    ArrayList<String> andList = new ArrayList<>();

    AspAndTest(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspAndTest parse(Scanner s) {
        enterParser("and test");

        AspAndTest aat = new AspAndTest(s.curLineNum());
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken) break;
            aat.andList.add(" and "); skip(s, andToken);
        }

        leaveParser("and test");
        return aat;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        notTests.get(0).prettyPrint();
        for(int i = 1; i < notTests.size(); i++){
            prettyWrite(andList.get(i-1)); 
            notTests.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        RuntimeValue v = notTests.get(0).eval(curScope);
        for(int i = 1; i < notTests.size(); i++){
            if (v.getBoolValue("and operand", this))
                return v;
            v = notTests.get(i).eval(curScope);
        }
        return v;
    }
}