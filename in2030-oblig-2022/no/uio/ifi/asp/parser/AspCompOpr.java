package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspCompOpr extends AspSyntax {
    String value;

    AspCompOpr(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr compOpr = new AspCompOpr(s.curLineNum());

        if(s.curToken().kind == lessToken){
            skip(s, lessToken); compOpr.value = " < ";
        }
        else if(s.curToken().kind == greaterToken){
            skip(s, greaterToken); compOpr.value = " > ";
        }
        else if(s.curToken().kind == doubleEqualToken){
            skip(s, doubleEqualToken); compOpr.value = " == ";
        }
        else if(s.curToken().kind == greaterEqualToken){
            skip(s, greaterEqualToken); compOpr.value = " >= ";
        }
        else if(s.curToken().kind == lessEqualToken){
            skip(s, lessEqualToken); compOpr.value = " <= ";
        }
        else {
            skip(s, notEqualToken); compOpr.value = " != ";
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