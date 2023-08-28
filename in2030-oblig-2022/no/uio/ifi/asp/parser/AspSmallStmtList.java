package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmt = new ArrayList<>();
    ArrayList<String> commaList = new ArrayList<>();
    boolean isSemi = false; 

    AspSmallStmtList(int n){
        super(n);
    }

    static AspSmallStmtList parse(Scanner s){
        enterParser("small stmt list");
        
        AspSmallStmtList smallStmtList = new AspSmallStmtList(s.curLineNum());
        smallStmtList.smallStmt.add(AspSmallStmt.parse(s));
        while(s.curToken().kind != newLineToken){
            skip(s, semicolonToken);
            smallStmtList.isSemi = true;
            smallStmtList.commaList.add("; ");
            // Break if there is a newLineToken right after
            if(s.curToken().kind == newLineToken){
                break;
            } 
            smallStmtList.smallStmt.add(AspSmallStmt.parse(s));
        }
        skip(s, newLineToken);
        
        leaveParser("small stmt list");
        return smallStmtList;
    }

    @Override
    public void prettyPrint() {
        smallStmt.get(0).prettyPrint();
        if(isSemi){
            prettyWrite("; ");
        }
        if(smallStmt.size() > 1){
            for(int i = 1; i < smallStmt.size(); i++){
                smallStmt.get(i).prettyPrint();
                int colonIndex = i - 1;
                if(colonIndex < commaList.size()) prettyWrite(commaList.get(colonIndex));
            }
        }
        prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = null;
        for(AspSmallStmt s: smallStmt){
            v = s.eval(curScope);
        }
        return v;
    }
    
}
