package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimarySuffix extends AspSyntax {
    AspArguments argument;
    AspSubscription subscription;
    Boolean isArgument = false;
    
    AspPrimarySuffix(int n){
        super(n);
    }
    
    static AspPrimarySuffix parse(Scanner s){
        enterParser("primary suffix");

        AspPrimarySuffix primarySuffix = new AspPrimarySuffix(s.curLineNum());
        if(s.curToken().kind == leftParToken){
            primarySuffix.isArgument = true; 
            primarySuffix.argument = AspArguments.parse(s);
        }
        else{
            primarySuffix.subscription = AspSubscription.parse(s);
        }

        leaveParser("primary suffix");
        return primarySuffix;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        if(isArgument){
            argument.prettyPrint();
        }
        else{
            subscription.prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
