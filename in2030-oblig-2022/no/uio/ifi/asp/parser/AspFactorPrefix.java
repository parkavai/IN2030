package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorPrefix extends AspSyntax {
    String value;
    TokenKind kind; 

    AspFactorPrefix(int n) {
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix factPrefix = new AspFactorPrefix(s.curLineNum());
        if(s.curToken().kind == plusToken){
            skip(s, plusToken); factPrefix.value = "+ "; factPrefix.kind = plusToken;
        }
        else{
            skip(s, minusToken); factPrefix.value = "- "; factPrefix.kind = minusToken;
        }
        
        leaveParser("factor prefix");
        return factPrefix;
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