package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspCompOpr extends AspSyntax {
    String value;
    TokenKind kind; 

    AspCompOpr(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr compOpr = new AspCompOpr(s.curLineNum());

        if(s.curToken().kind == lessToken){
            skip(s, lessToken); compOpr.value = " < "; compOpr.kind = lessToken;
        }
        else if(s.curToken().kind == greaterToken){
            skip(s, greaterToken); compOpr.value = " > "; compOpr.kind = greaterToken;
        }
        else if(s.curToken().kind == doubleEqualToken){
            skip(s, doubleEqualToken); compOpr.value = " == "; compOpr.kind = doubleEqualToken;
        }
        else if(s.curToken().kind == greaterEqualToken){
            skip(s, greaterEqualToken); compOpr.value = " >= "; compOpr.kind = greaterEqualToken;
        }
        else if(s.curToken().kind == lessEqualToken){
            skip(s, lessEqualToken); compOpr.value = " <= "; compOpr.kind = lessEqualToken;
        }
        else {
            skip(s, notEqualToken); compOpr.value = " != "; compOpr.kind = notEqualToken;
        }
        
        leaveParser("comp opr");
        return compOpr;
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