package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSmallStmt {
    AspName name;
    ArrayList<AspSubscription> subs = new ArrayList<>();
    AspExpr expr;
    Boolean isSubscription = false;

    AspAssignment(int n){
        super(n);
    }
    
    static AspAssignment parse(Scanner s){
        enterParser("assignment");
        
        AspAssignment assignment = new AspAssignment(s.curLineNum());

        assignment.name = AspName.parse(s);

        // Could be a subscription or not, so have to check
        if(s.curToken().kind == leftBracketToken){
            assignment.isSubscription = true;
            // It is a subscription
            while(true){
                assignment.subs.add(AspSubscription.parse(s));
                if(s.curToken().kind != leftBracketToken) break;
                skip(s, leftBracketToken);
            }
        }

        skip(s, equalToken);
        assignment.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return assignment;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        name.prettyPrint();
        if(isSubscription){
            for(int i = 0; i < subs.size(); i++){
                subs.get(i).prettyPrint();
            }
        }
        prettyWrite(" = ");
        expr.prettyPrint();
        
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
