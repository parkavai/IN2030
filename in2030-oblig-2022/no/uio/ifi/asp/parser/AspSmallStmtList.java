package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmt = new ArrayList<>();
    Boolean isEndSemi = false;

    AspSmallStmtList(int n){
        super(n);
    }

    static AspSmallStmtList parse(Scanner s){
        enterParser("small stmt list");
        
        AspSmallStmtList smallStmtList = new AspSmallStmtList(s.curLineNum());
        smallStmtList.smallStmt.add(AspSmallStmt.parse(s));
        while(true){
            // Could be a newline
            if(s.curToken().kind != semicolonToken) break;
            skip(s, semicolonToken);
            /**
             * We break if there is another ';' right after the one that is skipped since
             * that one is connected to end of the diagram
            */ 
            if(s.curToken().kind == semicolonToken){
                smallStmtList.isEndSemi = true; 
                break;
            } 
            smallStmtList.smallStmt.add(AspSmallStmt.parse(s));
        }
        if(s.curToken().kind == semicolonToken) skip(s, semicolonToken);
        skip(s, newLineToken);
        
        leaveParser("small stmt list");
        return smallStmtList;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        smallStmt.get(0).prettyPrint();
        if(smallStmt.size() > 1){
            for(int i = 1; i < smallStmt.size(); i++){
                smallStmt.get(i).prettyPrint();
                prettyWrite("; ");
            }
        }
        if(isEndSemi){
            prettyWrite("; ");
        }
        prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        RuntimeValue v = null;
        for(AspSmallStmt s: smallStmt){
            v = s.eval(curScope);
        }
        return v;
    }
    
}
