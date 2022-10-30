package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;
import java.lang.Math;

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

    @Override
    public String showInfo() {
        return this.toString();
    }

    // Copied from Dag Langmyhr: https://screencast.uninett.no/relay/ansatt/daguio.no/2019/17.10/2746600/IN2030-42-1_-_20191017_110157_39.html
    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    // Copied from Dag Langmyhr: https://screencast.uninett.no/relay/ansatt/daguio.no/2019/17.10/2746600/IN2030-42-1_-_20191017_110157_39.html
    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return (double)intValue;
    }

    // Copied from Dag Langmyhr: https://screencast.uninett.no/relay/ansatt/daguio.no/2019/17.10/2746600/IN2030-42-1_-_20191017_110157_39.html
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (intValue != 0);
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return String.valueOf(intValue);
    }

    // Copied from Dag Langmyhr: https://screencast.uninett.no/relay/ansatt/daguio.no/2019/17.10/2746600/IN2030-42-1_-_20191017_110157_39.html
    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue + v.getIntValue("+ operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operand",where));
        }
        runtimeError("Type error for +.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue / v.getIntValue("/ operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operand",where));
        }
        runtimeError("Type error for /.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue == v.getIntValue("== operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operand",where));
        }
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue > v.getIntValue("> operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue > v.getFloatValue("> operand",where));
        }
        runtimeError("Type error for >.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue >= v.getIntValue(">= operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue >= v.getFloatValue(">= operand",where));
        }
        runtimeError("Type error for >=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(Math.floorDiv(intValue, v.getIntValue("// operand",where)));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("// operand",where)));
        }
        runtimeError("Type error for /.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue < v.getIntValue("< operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue < v.getFloatValue("< operand",where));
        }
        runtimeError("Type error for <.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue <= v.getIntValue("<= operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue <= v.getFloatValue("<= operand",where));
        }
        runtimeError("Type error for <=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(Math.floorMod(intValue, v.getIntValue("% operand",where)));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue - v.getFloatValue("% operand",where) 
                                        * Math.floor(intValue / v.getFloatValue("% operand",where)));
        }
        runtimeError("Type error for %.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue * v.getIntValue("* operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue((double) intValue * v.getFloatValue("* operand",where));
        }
        runtimeError("Type error for *.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(-1 * intValue); // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("! operand", where)); // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeBoolValue(intValue != v.getIntValue("!= operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue != v.getFloatValue("!= operand",where));
        }
        else if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error for !=.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(intValue); // Required by the compiler.
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            return new RuntimeIntValue(intValue - v.getIntValue("- operand",where));
        } 
        else if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue - v.getFloatValue("- operand",where));
        }
        runtimeError("Type error for -.", where);
        return null; // Required by the compiler.
    }

}
