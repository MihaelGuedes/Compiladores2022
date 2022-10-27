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
public class Token {

	public final TokenType tipoDoToken;
	public final String lexema;

	public static final String DELIMITADOR_DE_TOKEN = "\t\n\r\f ";

	public Token (TokenType tipo, String valor) {
		this.tipoDoToken = tipo;
		this.lexema = valor;
	}

	@Override
	public String toString() {
		return "Token [type=" + this.tipoDoToken + ", lexeme=" + this.lexema + "]";
	}
}
