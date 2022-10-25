package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimary extends AspSyntax {
    AspAtom atom;
    ArrayList<AspPrimarySuffix> primarySuffixList = new ArrayList<>();
    Boolean isSuffix = false;
    
    AspPrimary(int n){
        super(n);
    }
    
    static AspPrimary parse(Scanner s){
        enterParser("primary");

        AspPrimary primary = new AspPrimary(s.curLineNum());
        primary.atom = AspAtom.parse(s);

        while(true){
            // Can either be a subscription or arguments, or none of them in which we break out of loop
            if(s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken){
                primary.isSuffix = true;
                primary.primarySuffixList.add(AspPrimarySuffix.parse(s));
            } 
            else break;
        }

        leaveParser("primary");
        return primary;
    }

    @Override
    public void prettyPrint() {
        // -- Must be changed in part 2:
        atom.prettyPrint();
        if(isSuffix){
            for(int i = 0; i < primarySuffixList.size(); i++){
                primarySuffixList.get(i).prettyPrint();
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // -- Must be changed in part 4:
        RuntimeValue v = atom.eval(curScope);
        if(isSuffix){
            ArrayList<RuntimeValue> arguments = new ArrayList<>();
            for(int i = 0; i < primarySuffixList.size(); i++){
                RuntimeValue v2 = primarySuffixList.get(i).eval(curScope);
                if(v instanceof RuntimeListValue || v instanceof RuntimeDictValue){
                    // Is subscription
                    v = v.evalSubscription(v2, this);
                }
                // Is Argument
                else{
                    // Arguments are made out of lists which is why we get the lists of the arguments through casting runtimevalue to runtimelistvalue
                    if(v2 instanceof RuntimeListValue){
                        // Cast value to runtimelistvalue
                        RuntimeListValue list = (RuntimeListValue) v2; 
                        // System.out.println(list.showInfo());
                        // Get the arguments from runtimelistvalue
                        arguments = list.getList();
                        AspName functionName = (AspName) atom; 
                        trace("Call function " + functionName.value + " with params: " + arguments);
                        v = v.evalFuncCall(arguments, this);
                    }
                }
            }
        }
        // If print is called, then we should write none after. 
        if(v instanceof RuntimeNoneValue) trace("None");
        return v; 
    }
}

