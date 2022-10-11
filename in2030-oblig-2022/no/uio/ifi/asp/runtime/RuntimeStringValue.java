package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue{
    String stringValue;

    public RuntimeStringValue(String v) {
        stringValue = v;
    }

    @Override
    String typeName() {
        return "str";
    }

    @Override
    public String toString() {
        return String.valueOf(stringValue);
    }
    
    @Override
    public String showInfo() {
    if (stringValue.indexOf('\'') >= 0)
        return '"' + stringValue + '"';
    else
        return "’" + stringValue + "’";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
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
            return new RuntimeStringValue(stringValue + v.getStringValue(" \"\" operand", where));
        }
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
            return new RuntimeBoolValue(stringValue.length() < v.getStringValue("< operand", where).length());
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.length() > v.getStringValue("> operand", where).length());
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.length() <= v.getStringValue("<= operand", where).length());
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeStringValue){
            return new RuntimeBoolValue(stringValue.length() >= v.getStringValue(">= operand", where).length());
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
}
