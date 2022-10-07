package no.uio.ifi.asp.runtime;

import java.util.HashMap;

public class RuntimeDict extends RuntimeValue {
    // Key is the type representation of the runtimeval for instance {"int": RuntimeInteger}
    HashMap<String, RuntimeValue> runTimeDict;

    public RuntimeDict(HashMap<String, RuntimeValue> v) {
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
}