package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

abstract class AspAtom extends AspSyntax {

    AspAtom(int n){
        super(n);
    }

    // Copied from 'https://www.uio.no/studier/emner/matnat/ifi/IN2030/h22/timeplan/uke-37-utdeling.pdf' by Dag Langmyhr
    static AspAtom parse(Scanner s) {
        enterParser("atom");

        AspAtom aa = null;
        switch (s.curToken().kind) {
            case falseToken:
            case trueToken:
                aa = AspBooleanLiteral.parse(s); break;
            case floatToken:
                aa = AspFloatLiteral.parse(s); break;
            case integerToken:
                aa = AspIntegerLiteral.parse(s); break;
            case leftBraceToken:
                aa = AspDictDisplay.parse(s); break;
            case leftBracketToken:
                aa = AspListDisplay.parse(s); break;
            case leftParToken:
                aa = AspInnerExpr.parse(s); break;
            case nameToken:
                aa = AspName.parse(s); break;
            case noneToken:
                aa = AspNoneLiteral.parse(s); break;
            case stringToken:
                aa = AspStringLiteral.parse(s); break;
            default:
                parserError("Expected an expression atom but found a " +
            s.curToken().kind + "!", s.curLineNum());
            }

        leaveParser("atom");
        return aa;
    }

}