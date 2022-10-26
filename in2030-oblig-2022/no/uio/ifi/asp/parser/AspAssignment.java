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
        RuntimeValue ex = expr.eval(curScope);
        if(isSubscription){
            // Iterate untill the last index
            RuntimeValue v = name.eval(curScope);
            for(int i = 0; i < subs.size()-1; i++){
                v = v.evalSubscription(subs.get(i).eval(curScope), this);
            }
            // The last subscription value 
            AspSubscription last = subs.get(subs.size()-1);
            v.evalAssignElem(last.eval(curScope), ex, this);
        }
        else{
            if(curScope.hasGlobalName(name.value)){
                Main.globalScope.assign(name.value, ex);
            }
            else{
                curScope.assign(name.value, ex);
            }
            trace(name.value + " = " + ex.toString());
        }
        return null;
    }
}
