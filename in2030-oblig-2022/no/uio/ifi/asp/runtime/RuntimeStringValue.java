package no.uio.ifi.asp.runtime;

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
    
}
