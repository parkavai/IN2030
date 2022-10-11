package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspArguments extends AspPrimarySuffix{
    ArrayList<AspExpr> expression = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();
    Boolean isExpr = false;

    AspArguments(int n){
        super(n);
    }

    static AspArguments parse(Scanner s){
        enterParser("arguments");

        AspArguments arguments = new AspArguments(s.curLineNum());

        skip(s, leftParToken);
        // Skip the first parenthesis '('
        // It is possible for multiple commaTokens, therefore we have a while-loop
        if(s.curToken().kind != rightParToken){
            arguments.isExpr = true;
            while(true){
                arguments.expression.add(AspExpr.parse(s));
                if(s.curToken().kind != commaToken) break;
                arguments.stringList.add(", "); skip(s, commaToken);
            }
        }

        skip(s, rightParToken); // Skip past the second parenthesis ')'
        leaveParser("arguments");
        return arguments;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        prettyWrite("(");
        if(isExpr){
            expression.get(0).prettyPrint();
            for(int i = 1; i < expression.size(); i++){
                prettyWrite(stringList.get(i-1)); 
                expression.get(i).prettyPrint();
            }   
        }
        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        if(isExpr){
            ArrayList<RuntimeValue> v = new ArrayList<>();
            for(int i = 0; i < expression.size(); i++){
                v.add(expression.get(i).eval(curScope));
            }
            return new RuntimeListValue(v);
        }
        return null;
    }
}
