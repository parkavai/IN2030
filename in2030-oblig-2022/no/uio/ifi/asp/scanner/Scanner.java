// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
	private LineNumberReader sourceFile = null;
	private String curFileName;
	private ArrayList<Token> curLineTokens = new ArrayList<>();
	private Stack<Integer> indents = new Stack<>();
	private final int TABDIST = 4;

	public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
							new FileInputStream(fileName),
							"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
	}

	private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
	}

	public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
	}
	
	public void readNextToken() {
		if (!curLineTokens.isEmpty())
			curLineTokens.remove(0);
	}

	private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} 
			else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}
	
		// -- Must be changed in part 1:
		if(sourceFile == null){
			terminateLine(false);
			return;
		}

		// Check for whitespace, if so then we ignore 
		if(line.trim().length() <= 0){
			return;
		}

		boolean isCommentOnly = true;

		// Check if there are comments
		for(int x = 0; x < line.length(); x++){
			char c = line.charAt(x);
			if(c == '#'){
				return;
			}
			else if(isLetterAZ(c)){
				break;
			}
			else if(isDigit(c)){
				break;
			}
			else{
				continue;
			}
		}

		// Changes all TABS to " "
		line = expandLeadingTabs(line);

		// Amount of empty spaces
		int n = findIndent(line);
		if(n > indents.peek()){
			indents.push(n);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		}
		else{
			while (n < indents.peek()){
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
		}

		if(n != indents.peek()){
			scannerError("There is an Indentationfault");
			return;
		}

		for(int i = 0; i < line.length(); i++){
			char c = line.charAt(i);
			Token pointer = null;
			if(c == '#'){
				break;
			}
			else {
				isCommentOnly = false;
				if (isLetterAZ(c)){
					// Fortsette traversering fra det punktet inntill du møter whitespace, da har du hele variabelnavnet
					int start = i;
					int end = syntaxIteration(line, start);
					String syntax = line.substring(start, end);
					if(syntax.equals("and")){
						pointer = new Token(TokenKind.andToken, curLineNum());
					}
					else if(syntax.equals("def")){
						pointer = new Token(TokenKind.defToken, curLineNum());
					}
					else if(syntax.equals("elif")){
						pointer = new Token(TokenKind.elifToken, curLineNum());
					}
					else if(syntax.equals("else")){
						pointer = new Token(TokenKind.elseToken, curLineNum());
					}
					else if(syntax.equals("False")){
						pointer = new Token(TokenKind.falseToken, curLineNum());
					}
					else if(syntax.equals("for")){
						pointer = new Token(TokenKind.forToken, curLineNum());
					}
					else if(syntax.equals("global")){
						pointer = new Token(TokenKind.globalToken, curLineNum());
					}
					else if(syntax.equals("if")){
						pointer = new Token(TokenKind.ifToken, curLineNum());
					}
					else if(syntax.equals("in")){
						pointer = new Token(TokenKind.inToken, curLineNum());
					}
					else if(syntax.equals("None")){
						pointer = new Token(TokenKind.noneToken, curLineNum());
					}
					else if(syntax.equals("not")){
						pointer = new Token(TokenKind.notToken, curLineNum());
					}
					else if(syntax.equals("or")){
						pointer = new Token(TokenKind.orToken, curLineNum());
					}
					else if(syntax.equals("pass")){
						pointer = new Token(TokenKind.passToken, curLineNum());
					}
					else if(syntax.equals("return")){
						pointer = new Token(TokenKind.returnToken, curLineNum());
					}
					else if(syntax.equals("True")){
						pointer = new Token(TokenKind.trueToken, curLineNum());
					}
					else if(syntax.equals("while")){
						pointer = new Token(TokenKind.whileToken, curLineNum());
					}
					else{
						pointer = new Token(TokenKind.nameToken, curLineNum());
						pointer.name = syntax; 
					}
					end -= 1;
					i = end;
				}
	
				// Digit
				else if(isDigit(c)){
					int start = i;
					int end = numberIteration(line, start);
					String check = line.substring(i, end);
					if(check.contains(".")){
						pointer = new Token(TokenKind.floatToken, curLineNum());
						pointer.floatLit = Float.parseFloat(check); pointer.stringLit = check;
					}
					else{
						pointer = new Token(TokenKind.integerToken, curLineNum());
						pointer.integerLit = Integer.parseInt(check); pointer.stringLit = check;
					}
					i = end - 1;
				}
	
				// String
				else if(c == '"' || c == '\''){
					int start = i;
					boolean isDoubleQuotation = true;
					if(c == '\''){
						isDoubleQuotation = false;
					} 
					int end = stringIteration(line, start, isDoubleQuotation);
					start += 1;
					String str = line.substring(start, end);;
					pointer = new Token(TokenKind.stringToken, curLineNum());
					pointer.stringLit = str;
					i = end;
				}
	
				else if(c == '*'){
					pointer = new Token(TokenKind.astToken, curLineNum());
				}
	
				else if(c == '='){
					pointer = checkIfEndEqualOrSlash(line, c, i, curLineNum());
					if(pointer == null){
						pointer = new Token(TokenKind.equalToken, curLineNum());
					} 
					// Ensure that we access a new syntax instead of the last "=" in "==" for instance
					else {
						i += 1;
					}
				}
	
				else if(c == '/'){
					pointer = checkIfEndEqualOrSlash(line, c, i, curLineNum());
					if(pointer == null) {
						pointer = new Token(TokenKind.slashToken, curLineNum());
					}
					else{
						i += 1;
					} 
				}
	
				else if(c == '>'){
					pointer = checkIfEndEqualOrSlash(line, c, i, curLineNum());
					if(pointer == null){
						pointer = new Token(TokenKind.greaterToken, curLineNum());
					} 
					else{
						i += 1;
					} 
				}
	
				else if(c == '<'){
					pointer = checkIfEndEqualOrSlash(line, c, i, curLineNum());
					if(pointer == null){
						pointer = new Token(TokenKind.lessToken, curLineNum());
					} 
					else {
						i += 1;
					}
				}

				else if(c == '!'){
					pointer = checkIfEndEqualOrSlash(line, c, i, curLineNum());
					if(pointer == null) {
						pointer = new Token(TokenKind.notToken, curLineNum());
					}
					else{
						i += 1;
					} 
				}
	
				else if(c == '-'){
					pointer = new Token(TokenKind.minusToken, curLineNum());
				}
	
				else if(c == '%'){
					pointer = new Token(TokenKind.percentToken, curLineNum());
				}
	
				else if(c == '+'){
					pointer = new Token(TokenKind.plusToken, curLineNum());
				}
	
				else if(c == ':'){
					pointer = new Token(TokenKind.colonToken, curLineNum());
				}
	
				else if(c == ';'){
					pointer = new Token(TokenKind.semicolonToken, curLineNum());
				}
	
				else if(c == ','){
					pointer = new Token(TokenKind.commaToken, curLineNum());
				}
	
				else if(c == ')'){
					pointer = new Token(TokenKind.rightParToken, curLineNum());
				}
	
				else if(c == '}'){
					pointer = new Token(TokenKind.rightBraceToken, curLineNum());
				}
	
				else if(c == ']'){
					pointer = new Token(TokenKind.rightBracketToken, curLineNum());
				}			
	
				else if(c == '('){
					pointer = new Token(TokenKind.leftParToken, curLineNum());
				}
	
				else if(c == '{'){
					pointer = new Token(TokenKind.leftBraceToken, curLineNum());
				}
	
				else if(c == '['){
					pointer = new Token(TokenKind.leftBracketToken, curLineNum());
				}
	
				else if(c == '\n'){
					pointer = new Token(TokenKind.newLineToken, curLineNum()); curLineTokens.add(pointer);	
					break;
				}
				else{
					continue;
				}
			}	
			curLineTokens.add(pointer);		
		}
		if(isCommentOnly){
			return;
		}
		terminateLine(true);
	}

	/**
	 * Method used for checking if an operator is a (>=, //, <=, etc)
	 * 
	 * @param pointer
	 * @param line
	 * @param c
	 * @param index
	 * @param curLineNum
	 * @return Token and the found operator
	 */
	private Token checkIfEndEqualOrSlash(String line, char c, int index, int curLineNum){
		int tmp = index + 1;
		Token operator = null;
		if(tmp < line.length()){
			if(c == '>' && line.charAt(tmp) == '='){
				operator = new Token(TokenKind.greaterEqualToken, curLineNum);
			}
			else if(c == '=' && line.charAt(tmp) == '='){
				operator = new Token(TokenKind.doubleEqualToken, curLineNum);
			}
			else if(c == '<' && line.charAt(tmp) == '='){
				operator = new Token(TokenKind.lessEqualToken, curLineNum);
			}
			else if(c == '!' && line.charAt(tmp) == '='){
				operator = new Token(TokenKind.notEqualToken, curLineNum);
			}
			else if(c == '/' && line.charAt(tmp) == '/'){
				operator = new Token(TokenKind.doubleSlashToken, curLineNum);
			}
			else{
				return null;
			}	
		}
		return operator;
	}

	/**
	 * A method which is used specifically for strings where we return index where string ends. 
	 * That way, we can use substring from where the string starts and ends in order to get 
	 * the value of the string literal. It is important to check wether the string ends in 
	 * the same line or not. 
	 * 
	 * @param line
	 * @param index
	 * @return index
	 */
	private int stringIteration(String line, int index, boolean isDoubleQuotation){
		int length = line.length();
		index ++;
        while (index < length){
			char c = line.charAt(index);
			if(isDoubleQuotation){
				if(c == '\"'){
					break;
				}
			}
            else{
				if(c == '\''){
					break;
				}
			}
            index ++;
        }
		if(index == length){
			scannerError("String line is not terminated!");	
		}
        return index;
	}

	/**
	 * A method which is used specifically for ints or floats where we return index where number ends. 
	 * That way, we can use substring from where the string starts and ends in order to get 
	 * the value of the number. We also have to check wether the number is a float or int when we get the substring 
	 * in order to correctly get the numbers value
	 * 
	 * @param line
	 * @param index
	 * @return index
	 */
	private int numberIteration(String line, int index){
        int length = line.length();
		index ++;
        while (index < length){
			char c = line.charAt(index);
            if(isDigit(c) == false){
				if(c == '.'){
					index ++;
					continue;
				}
                break;
            }
            index ++;
        }
        return index;
	}

	/**
	 * A method which is used specifically for syntax such as "and, if, else" where we return the index where the syntax ends. 
	 * That way, we can use substring from where the string starts and ends in order to get 
	 * the value of the syntax. 
	 * 
	 * @param line
	 * @param index
	 * @return index
	 */
	private int syntaxIteration(String line, int index){
        int length = line.length();
		index ++;
        while (index < length){
			char c = line.charAt(index);
            if(isLetterAZ(c) != true){
                if(isDigit(c)){
					index++;
					continue;
				}
				break; 
            }
            index++;
        }
        return index;
	}

	/**
	 * A method used for terminating the line in where we either add a newline-token
	 * or end-of-line token based on parameter
	 * 
	 * @param isNewLine
	 */
	private void terminateLine(boolean isNewLine){
		// Add newline:
		if(isNewLine){
			curLineTokens.add(new Token(newLineToken, curLineNum()));
		}
		// Add End-of-line
		else{
			while (indents.size() > 1){
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
			curLineTokens.add(new Token(eofToken, curLineNum()));
		}

		for (Token t : curLineTokens)
			Main.log.noteToken(t);
	}

	public int curLineNum() {
		return sourceFile != null ? sourceFile.getLineNumber() : 0;
	}

	private int findIndent(String s) {
		int indent = 0;
		while (indent < s.length() && s.charAt(indent) == ' ')
			indent++;
		return indent;
	}

	private String expandLeadingTabs(String s) {
		// -- Must be changed in part 1:
		String fixedString = "";
		int n = 0;
		int start = 0;
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c == ' '){
				fixedString += " ";
				n++; 
			}
			else if(c == '\t'){
				for (int x = 0; x < (4 - (n % 4)); x++){
					fixedString += " ";
				}
				n += 4 - (n % 4);
			}
			else{
				start = i;
				break;
			}
		}
		fixedString += s.substring(start);
		return fixedString;
	}

	private boolean isLetterAZ(char c) {
		return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || (c == '_');
	}

	private boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}

	public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		if(k == lessToken || k == greaterToken || k == doubleEqualToken || k == greaterEqualToken
		|| k == lessEqualToken || k == notEqualToken) return true;
		return false;
	}

	public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		if(k == plusToken || k == minusToken) return true;
		return false;
	}

	public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		if(k == astToken || k == slashToken || k == percentToken|| k == doubleSlashToken) return true;
		return false;
	}

	public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		// -- Must be changed in part 2:
		if(k == plusToken || k == minusToken) return true;
		return false;
	}

	public boolean anyEqualToken() {
		for (Token t : curLineTokens) {
			if (t.kind == equalToken)
				return true;
			if (t.kind == semicolonToken)
				return false;
		}
		return false;
	}
}
