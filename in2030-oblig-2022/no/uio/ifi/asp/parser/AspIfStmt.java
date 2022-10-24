package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> exprList = new ArrayList<>();
    ArrayList<AspSuite> suiteList = new ArrayList<>();
    Boolean isElif = false;
    Boolean isElse = false;

    AspIfStmt(int n){
        super(n);
    }
    
    static AspIfStmt parse(Scanner s){
        enterParser("if stmt");

        AspIfStmt ifStmt = new AspIfStmt(s.curLineNum());
        skip(s, ifToken);

        while(true){
            ifStmt.exprList.add(AspExpr.parse(s));
            skip(s, colonToken);
            ifStmt.suiteList.add(AspSuite.parse(s));
            if(s.curToken().kind != elifToken) break;
            ifStmt.isElif = true;
            skip(s, elifToken);
        }

        if(s.curToken().kind == elseToken){
            skip(s, elseToken);
            skip(s, colonToken);
            ifStmt.suiteList.add(AspSuite.parse(s));
            ifStmt.isElse = true;
        }

        leaveParser("if stmt");
        return ifStmt;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("if "); exprList.get(0).prettyPrint();
        prettyWrite(": "); suiteList.get(0).prettyPrint();
        if(isElif){
            prettyWrite("elif ");
            for(int i = 1; i < exprList.size(); i++){
                exprList.get(i).prettyPrint(); prettyWrite(": ");
                suiteList.get(i).prettyPrint(); prettyWrite("elif ");
            }
        }
        if(isElse){
            prettyWrite("else"); prettyWrite(": ");
            suiteList.get(suiteList.size() - 1).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        Boolean areAnyTrue = false; 
        for(int i = 0; i < exprList.size(); i++){
            RuntimeValue expr = exprList.get(i).eval(curScope);
            if(expr.getBoolValue("if", this)){
                trace(String.format("if True alt #%d: ...", i));
                suiteList.get(i).eval(curScope);
                areAnyTrue = true; 
                break;
            }
        }
        // Should no if or elif statements be true, aswell as there is an else, then we traverse to the else
        if(areAnyTrue == false && isElse){
            trace("else: ....");
            suiteList.get(suiteList.size()-1).eval(curScope);
        }
        return null;
    }
}
