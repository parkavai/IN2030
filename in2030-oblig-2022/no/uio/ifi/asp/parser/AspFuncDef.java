package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt {
    public ArrayList<AspName> nameList = new ArrayList<>();
    AspSuite suite;
    ArrayList<String> commaList = new ArrayList<>();

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
                func.commaList.add(", ");
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
        prettyWrite("def "); nameList.get(0).prettyPrint(); prettyWrite(" (");
        int commaAmount = 0;
        if(nameList.size() > 1){
            for(int i = 1; i < nameList.size(); i++){
                nameList.get(i).prettyPrint();
                if(commaAmount < commaList.size()){
                    prettyWrite(commaList.get(commaAmount));
                    commaAmount += 1;
                }
            }
        }
        prettyWrite(")"); prettyWrite(":");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        String functionName = nameList.get(0).value;
        trace("def " + functionName);
        ArrayList<RuntimeValue> args = new ArrayList<>(); 
        // Important to remove the function name as it isn´t a formal parameter
        nameList.remove(0);
        for(int i = 0; i < nameList.size(); i++){
            args.add(new RuntimeStringValue(nameList.get(i).value));
        }
        RuntimeFunc newFunc = new RuntimeFunc(this, curScope, functionName);
        curScope.assign(functionName, newFunc);
        return null;
    }

    public RuntimeValue runFunction(RuntimeScope curScope) throws RuntimeReturnValue{
        return suite.eval(curScope);
    }
}
