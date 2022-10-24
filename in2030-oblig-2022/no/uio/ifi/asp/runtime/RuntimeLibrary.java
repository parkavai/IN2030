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
    }

    // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h21/forelesninger/uke-45-utdeling.pdf
    // len
    /* 
    assign("len", new RuntimeFunc("len") {
        @Override
        public RuntimeValue evalFuncCall(
        ArrayList<RuntimeValue> actualParams,
        AspSyntax where) {
        checkNumParams(actualParams, 1, "len", where);
        return actualParams.get(0).evalLen(where);
    }});
    */

    private void checkNumParams(ArrayList<RuntimeValue> actArgs,
            int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect)
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
    }

    // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h21/forelesninger/uke-45-utdeling.pdf
    // print
    /* 
    assign("print", new RuntimeFunc("print") {
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,
        AspSyntax where) {
        for (int i = 0; i < actualParams.size(); ++i) {
        if (i > 0) System.out.print(" ");
        System.out.print(actualParams.get(i).toString());
        }
        System.out.println();
        return new RuntimeNoneValue();
    }});
    */
}
