package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> runTimeList;

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
        runTimeList = v;
    }

    @Override
    String typeName() {
        return "list";
    }

    @Override
    public String showInfo(){
        String showString = "";
        for(RuntimeValue v: runTimeList){
            showString += v.toString();
        }
        return showString;
    }

    @Override
    public String toString() {
        String prettyString = "[";
        for(int i = 0; i < runTimeList.size(); i++){
            prettyString += runTimeList.get(i).toString();
            if(i != runTimeList.size()-1){
                prettyString += ",";
            }
        }
        prettyString += "]";
        return prettyString;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (runTimeList.size() != 0);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            ArrayList<RuntimeValue> newList = new ArrayList<>();
            for(int i = 0; i < v.getIntValue("* operand", where); i++){
                newList.add(v);
            }
            return new RuntimeListValue(newList);
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler.
    }
}