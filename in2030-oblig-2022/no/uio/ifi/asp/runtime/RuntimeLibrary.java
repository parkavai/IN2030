// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        // -- Must be changed in part 4:

        // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h21/forelesninger/uke-45-utdeling.pdf
        // len
        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }
        });   

        // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h21/forelesninger/uke-45-utdeling.pdf
        // print
        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                for (int i = 0; i < actualParams.size(); ++i) {
                    if (i > 0) System.out.print(" ");
                    System.out.print(actualParams.get(i).getStringValue("print", where));
                }
                System.out.println();
                return new RuntimeNoneValue();
            }
    });

        // int
        assign("int", new RuntimeFunc("int") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "int", where);
                return new RuntimeIntValue(actualParams.get(0).getIntValue("int", where));
            }
        });   

        // float
        assign("float", new RuntimeFunc("float") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "float", where);
                return new RuntimeFloatValue(actualParams.get(0).getFloatValue("float", where));
            }
        });  
        
        // str
        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "str", where);
                return new RuntimeStringValue(actualParams.get(0).getStringValue("str", where));
            }
        });  
        
        // input
        assign("input", new RuntimeFunc("input") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "input", where);
                String inputLine = actualParams.get(0).getStringValue("input", where);
                // Read line from keyboard and prints it out
                System.out.println(inputLine);
                // Get the next input line 
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });   

        // range
        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                // Must be 2 arguments
                checkNumParams(actualParams, 2, "range", where);
                long v1 = actualParams.get(0).getIntValue("range", where);
                long v2 = actualParams.get(1).getIntValue("range", where);
                if(v1 > v2){
                    return null;
                } 
                ArrayList<RuntimeValue> range = new ArrayList<>();
                for(long i = v1; i < v2; i++){
                    range.add(new RuntimeIntValue(i));
                }
                return new RuntimeListValue(range);
            }
        }); 
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs,
            int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect)
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
    }
}
