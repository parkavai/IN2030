package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    Float floatValue;

    public RuntimeFloatValue(Float v) {
        floatValue = v;
    }

    @Override
    String typeName() {
        return "floatr";
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
    }

}
