package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt {
    ArrayList<AspName> nameList = new ArrayList<>();
    AspSuite suite;


    AspFuncDef(int n){
        super(n);
    }
    
    static AspFuncDef parse(Scanner s){
        enterParser("func def");

        AspFuncDef func = new AspFuncDef(s.curLineNum());
        skip(s, defToken);
        func.nameList.add(AspName.parse(s));
        skip(s, leftParToken);
        if(s.curToken().kind != rightParToken){

            while(true){
                func.nameList.add(AspName.parse(s));
                if(s.curToken().kind != commaToken) break;
                skip(s, commaToken);
            }
        }

        skip(s, rightParToken);
        skip(s, colonToken);
        func.suite = AspSuite.parse(s);
        leaveParser("func def");
        return func;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("def "); nameList.get(0).prettyPrint();
        prettyWrite("(");
        if(nameList.size() > 1){
            for(int i = 1; i < nameList.size(); i++){
                nameList.get(i).prettyPrint();
                prettyWrite(", ");
            }
        }
        prettyWrite(")");
        prettyWrite(":");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        return null;
    }
}
