package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

public class RuntimeList extends RuntimeValue {
    ArrayList<RuntimeValue> runTimeList;

    public RuntimeList(ArrayList<RuntimeValue> v) {
        runTimeList = v;
    }

    @Override
    String typeName() {
        return "list";
    }

    @Override
    public String toString() {
        String prettyString = "";
        for(RuntimeValue v: runTimeList){
            prettyString += v.toString() + "\n";
        }
        return prettyString;
    }
}