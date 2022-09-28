package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {
    AspSmallStmtList smallStmtList;
    ArrayList<AspStmt> stmt = new ArrayList<>();
    Boolean isSmallStmtList = false;
    
    AspSuite(int n){
        super(n);
    }
    
    static AspSuite parse(Scanner s){
        enterParser("suite");
        AspSuite suite = new AspSuite(s.curLineNum());
        if(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
            skip(s, indentToken);
            while(true){
                suite.stmt.add(AspStmt.parse(s));
                if(s.curToken().kind == dedentToken) break;
            }
            skip(s, dedentToken);
        }
        else{
            suite.isSmallStmtList = true;
            suite.smallStmtList = AspSmallStmtList.parse(s);
        }

        leaveParser("suite");
        return suite;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        if(isSmallStmtList){
            smallStmtList.prettyPrint();
        }
        else{
            // Må kanskje skrive prettyWriteLn siden vi har en newline her
            prettyWriteLn();
            prettyIndent();
            if(stmt.size() > 1){
                for(int i = 0; i < stmt.size(); i++){
                    stmt.get(i).prettyPrint();
                }
            }
            else{
                stmt.get(0).prettyPrint();
            }
            prettyDedent();
        }

    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
