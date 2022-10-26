package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt{
    ArrayList<AspName> nameList = new ArrayList<>();

    AspGlobalStmt(int n){
        super(n);
    }

    static AspGlobalStmt parse(Scanner s){
        enterParser("global");
        AspGlobalStmt global = new AspGlobalStmt(s.curLineNum()); skip(s, globalToken);

        while(true){
            global.nameList.add(AspName.parse(s));
            if(s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        leaveParser("global");
        return global;
    }
    
    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("global ");
        nameList.get(0).prettyPrint();
        for(int i = 1; i < nameList.size(); i++){
            prettyWrite(", "); nameList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        for(AspName v: nameList){
            curScope.registerGlobalName(v.value);
        }
        return null;
    }

}
