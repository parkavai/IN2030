// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();
    Boolean isStmts = false;

    AspProgram(int n) {
        super(n);
    }

    public static AspProgram parse(Scanner s) {
        enterParser("program");

        AspProgram ap = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            ap.isStmts = true;
            ap.stmts.add(AspStmt.parse(s));
        }
        skip(s, eofToken);

        leaveParser("program");
        return ap;
    }

    @Override
    public void prettyPrint() {
        if(isStmts){
            for(int i = 0; i < stmts.size(); i++){
                stmts.get(i).prettyPrint();
            }
        }
    }

    // Copied from: https://www.uio.no/studier/emner/matnat/ifi/IN2030/h19/timeplan/uke-46-utdeling.pdf
    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspStmt as: stmts) {
            try {
                as.eval(curScope);
            } 
            catch (RuntimeReturnValue rrv) {
                RuntimeValue.runtimeError("Return statement outside function!",
                rrv.lineNum);
            }
        }
        return null;
    }
}
