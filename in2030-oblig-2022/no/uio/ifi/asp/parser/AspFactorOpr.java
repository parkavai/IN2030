package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorOpr extends AspSyntax {
    String value;
    TokenKind kind;

    AspFactorOpr(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr factOpr = new AspFactorOpr(s.curLineNum());
        if(s.curToken().kind == astToken){
            skip(s, astToken); factOpr.value = " * "; factOpr.kind = astToken;
        }
        else if(s.curToken().kind == slashToken){
            skip(s, slashToken); factOpr.value = " / "; factOpr.kind = slashToken;
        }
        else if(s.curToken().kind == percentToken){
            skip(s, percentToken); factOpr.value = " % "; factOpr.kind = percentToken;
        }
        else {
            skip(s, doubleSlashToken); factOpr.value = " // "; factOpr.kind = doubleSlashToken;
        }
        
        leaveParser("factor opr");
        return factOpr;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite(value);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
