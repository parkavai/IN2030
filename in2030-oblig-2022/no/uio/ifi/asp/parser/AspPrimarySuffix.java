package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspPrimarySuffix extends AspSyntax {

    AspPrimarySuffix(int n){
        super(n);
    }
    
    static AspPrimarySuffix parse(Scanner s){
        enterParser("primary suffix");

        AspPrimarySuffix primarySuffix = null;
        if(s.curToken().kind == leftParToken){
            primarySuffix = AspArguments.parse(s);
        }
        else{
            primarySuffix = AspSubscription.parse(s);
        }

        leaveParser("primary suffix");
        return primarySuffix;
    }

}
