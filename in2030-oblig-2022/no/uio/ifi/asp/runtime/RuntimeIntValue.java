package no.uio.ifi.asp.runtime;

public class RuntimeIntValue extends RuntimeValue {
    Long intValue;

    public RuntimeIntValue(Long v) {
        intValue = v;
    }

    @Override
    String typeName() {
        return "int";
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }

}
