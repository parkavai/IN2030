package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspName;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
    AspFuncDef def;
    RuntimeScope defScope;
    String name;
    ArrayList<AspName> args;

    public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name) {
        this.def = def; 
        args = def.nameList;
        this.defScope = defScope;
        this.name = name; 
    }

    // For runTimeLibrary only
    public RuntimeFunc(String function) {
        this.name = function; 
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actPars, AspSyntax where) {
        // The formal and actual arguments must be the same in terms of length
        if(args.size() == actPars.size()){
            RuntimeScope newScope = new RuntimeScope(defScope);
            for(int i = 0; i < args.size(); i++){
                AspName pointer = args.get(i);
                newScope.assign(pointer.value, actPars.get(i));
            }
            // Must handle the RuntimeReturnValue here 
            try{
                return def.runFunction(newScope);
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