package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
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
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long) runTimeList.size());
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

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            // Check if the index is valid in our list
            int index = (int) v.getIntValue("[] operand", where);
            if(index > runTimeList.size()){
                Main.panic("Index is bigger than the list size.");
            }
            return runTimeList.get(index);
        }
        runtimeError("Type error for [].", where);
        return null; // Required by the compiler.
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