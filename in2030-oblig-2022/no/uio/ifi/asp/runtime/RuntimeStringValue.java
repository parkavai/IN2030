package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue{
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    public String typeName() {
        return "string";
    }

    @Override
    public String toString() {
        // Will have quotation marks around string
        return "\'" + stringValue + "\'";
    }
    
    @Override
    public String showInfo() {
        return "\'" + stringValue + "\'";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return Long.valueOf(stringValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return Float.valueOf(stringValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (stringValue.length() != 0);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where){
        return new RuntimeIntValue((long) stringValue.length());
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeStringValue(stringValue + v.getStringValue(" + operand", where));
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue("== operand", where)));
        }
        else if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(!stringValue.equals(v.getStringValue("== operand", where)));
        }
        else if(v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("< operand", where)) < 0);
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("< operand", where)) > 0);
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("< operand", where)) <= 0);
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("< operand", where)) >= 0);
        }
        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            String newString = "";
            for(int i = 0; i < v.getIntValue("* operand", where); i++){
                newString += stringValue;
            }
            return new RuntimeStringValue(newString);
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeIntValue){
            // Check if the index is valid in our list
            int index = (int) v.getIntValue("[] operand", where);
            if(index > stringValue.length()){
                Main.panic("Index is bigger than the string length.");
            }
            return new RuntimeStringValue(Character.toString(stringValue.charAt(index)));
        }
        runtimeError("Type error for [].", where);
        return null; // Required by the compiler.
    }
}
