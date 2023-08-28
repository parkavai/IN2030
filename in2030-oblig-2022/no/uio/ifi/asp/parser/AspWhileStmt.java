package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt{
    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n){
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");

        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken); aws.test = AspExpr.parse(s);
        skip(s, colonToken); aws.body = AspSuite.parse(s);

        leaveParser("while stmt");
        return aws;
        }

    @Override
    public void prettyPrint() {
        prettyWrite("while "); test.prettyPrint();
        prettyWrite(":"); body.prettyPrint();
    }

    // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h21/forelesninger/uke-44-utdeling.pdf
    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        while (true) {
            RuntimeValue t = test.eval(curScope);
            if (! t.getBoolValue("while loop test", this)) break;
            trace("while True: ...");
            body.eval(curScope);
        }
        trace("while False:");
        return null;
    }

}