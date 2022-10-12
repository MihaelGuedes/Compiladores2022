// package stacker.rpn.lexer;
import java.util.regex.*;

public class Regex {

	public static boolean isNum(String token) {
		if (token == null)
			return false;
		
		return token.matches("(\\d)+");
	}
	
	public static boolean isOP(String token) {

		if (token == null)
            return false;

		if (token.equals("+") || token.equals("-") || token.equals("/") || token.equals("*")) {
			return true;
		}

		return false;
	}

	public static TokenType verificarRegex(String token) throws Exception {
		if (isNum(token)) {
                return TokenType.NUM;
		} else if (Regex.isOP(token)) {
			if (token.equals("+")) {
				return TokenType.PLUS;
			} else if (token.equals("-")) {
				return TokenType.MINUS;
			} else if (token.equals("/")) {
				return TokenType.SLASH;
			} else if (token.equals("*")) {
				return TokenType.STAR;
			} else {
				throw new Exception("Error: Unexpected character: " + token);
			}
		} else {
			throw new Exception("Error: Unexpected character: " + token);
		}
	}
}
