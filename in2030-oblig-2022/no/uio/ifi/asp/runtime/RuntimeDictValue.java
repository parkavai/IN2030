package no.uio.ifi.asp.runtime;

import java.util.HashMap;
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
    public String toString() {
        String prettyString = "";
        for(RuntimeValue v: runTimeDict.values()){
            prettyString += v.toString();
        }
        return prettyString;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (runTimeDict.size() != 0);
    }
}