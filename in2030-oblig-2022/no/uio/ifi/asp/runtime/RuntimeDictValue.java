package no.uio.ifi.asp.runtime;

import java.util.HashMap;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    // Key is the type representation of the runtimeval for instance {"int": RuntimeInteger}
    HashMap<String, RuntimeValue> runTimeDict;

    public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
        runTimeDict = v;
    }

    @Override
    String typeName() {
        return "dict";
    }

    @Override 
    public String showInfo(){
        String prettyString = "";
        for(RuntimeValue v: runTimeDict.values()){
            prettyString += v.toString();
        }
        return prettyString;
    }

    @Override
    public String toString() {
        String prettyString = "{";
        for(String keys: runTimeDict.keySet()){
            prettyString += keys; prettyString += ": "; prettyString += runTimeDict.get(keys);
        }
        return prettyString;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (runTimeDict.size() != 0);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long) runTimeDict.size());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        String key = v.getStringValue("{} operand", where);
        if(runTimeDict.get(key) == null){
            Main.panic("Key is not in dictionary");
            return null;
        }
        return runTimeDict.get(key);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("! operand", where)); // Required by the compiler.
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler.
    }
}