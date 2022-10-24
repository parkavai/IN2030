package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
    AspFuncDef def;
    RuntimeScope defScope;
    String name;

    public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name) {
        this.def = def; 
        this.defScope = defScope;
        this.name = name; 
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actPars, AspSyntax where) {
        // The formal and actual arguments must be the same in terms of length
        if(def.getArgs().size() == actPars.size()){
            RuntimeScope newScope = new RuntimeScope(defScope);
            for(RuntimeValue v: actPars){
                newScope.assign(name, v);
            }
            // Must handle the RuntimeReturnValue here 
            try{
                def.runFunction(newScope);
            }
            catch(RuntimeReturnValue v){
                return v.value; 
            }
        }
        else{
            runtimeError("The formal and actual arguments length are not the same", where);
        }
        return null; 
    }

    @Override
    String typeName() {
        return "function";
    }
}