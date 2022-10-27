/* *******************************************************************
 * Copyright (c) 2021 Universidade Federal de Pernambuco (UFPE).
 * 
 * This file is part of the Compilers course at UFPE.
 * 
 * During the 1970s and 1980s, Hewlett-Packard used RPN in all 
 * of their desktop and hand-held calculators, and continued to 
 * use it in some models into the 2020s. In computer science, 
 * reverse Polish notation is used in stack-oriented programming languages 
 * such as Forth, STOIC, PostScript, RPL and Joy.
 *  
 * Contributors: 
 *     Henrique Rebelo      initial design and implementation 
 *     http://www.cin.ufpe.br/~hemr/
 * ******************************************************************/

package postfix.lexer;

/**
 * @author Henrique Rebelo
 */
public class Regex {
	// regex for literals recognition
	private static final String NUMERO_REGEX = "(\\d)+";
	// regex for single-character operation recognition.
	private static final String OPERACAO_REGEX = "(\\+|-|\\*|/)";
	private static final String SOMA_REGEX = "(\\+)";
	private static final String SUBTRACAO_REGEX = "(\\-)";
	private static final String DIVISAO_REGEX = "(/)";
	private static final String MULTIPLICACAO_REGEX = "(\\*)";
	private static final String ID_REGEX = "(x|y|z)";
	
	public static boolean ehNumero(String token) {
		return token.matches(NUMERO_REGEX);
	}
	public static boolean ehOperador(String token) {
		return token.matches(OPERACAO_REGEX);
	}
	public static boolean ehMultiplicacao(String token) {
		return token.matches(MULTIPLICACAO_REGEX);
	}

	public static boolean ehSubtracao(String token) {
		return token.matches(SUBTRACAO_REGEX);
	}
	public static boolean ehSoma(String token) {
		return token.matches(SOMA_REGEX);
	}
	public static boolean ehDivisao(String token) {
		return token.matches(DIVISAO_REGEX);
	}
	public static boolean ehID(String token) {
		return token.matches(ID_REGEX);
	}
	
	/**
	 * returns the proper token type for an operation token
	 * 
	 * @param token
	 * @return the operation token type
	 */
	public static TokenType pegarTokenOperador(String token) {
		TokenType tipoDoToken = null;
		if(ehSoma(token)) {
			tipoDoToken = TokenType.PLUS;
		}
		else if(ehSubtracao(token)) {
			tipoDoToken =  TokenType.MINUS;
		}
		else if(ehDivisao(token)) {
			tipoDoToken =  TokenType.SLASH;
		}
		else if(ehMultiplicacao(token)) {
			tipoDoToken = TokenType.STAR;
		}
		
		return tipoDoToken;
	}
}
