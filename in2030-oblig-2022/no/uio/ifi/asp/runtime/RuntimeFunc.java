package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {

    public RuntimeFunc() {
    }

    @Override
    String typeName() {
        return "function";
    }
}