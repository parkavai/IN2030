package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    Integer intValue;

    public RuntimeIntValue(Integer v) {
        intValue = v;
    }

    @Override
    String typeName() {
        return "integer";
    }

    @Override
    public String toString() {
        return "value";
    }


}
