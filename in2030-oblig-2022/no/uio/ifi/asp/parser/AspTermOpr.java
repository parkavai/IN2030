package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTermOpr extends AspSyntax {
    String value;
    TokenKind kind;

    AspTermOpr(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr termOpr = new AspTermOpr(s.curLineNum());
        if(s.curToken().kind == plusToken){
            skip(s, plusToken); termOpr.value = " + "; termOpr.kind = plusToken;
        }
        else{
            skip(s, minusToken); termOpr.value = " - "; termOpr.kind = minusToken;
        }
        
        leaveParser("term opr");
        return termOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(value);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
