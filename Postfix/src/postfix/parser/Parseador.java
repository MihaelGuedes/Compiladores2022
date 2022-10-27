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

package postfix.parser;

import java.util.List;
import java.util.Stack;

import postfix.ast.Expr;
import postfix.lexer.Token;
import postfix.lexer.TokenType;

/**
 * @author Henrique Rebelo
 */
public class Parseador {

	private final List<Token> tokens;
	// The internal stack used for shift-reduce parsing
	private Stack<Expr> pilha = new Stack<>();
	private int atual = 0;

	public Parseador(List<Token> tokens) {
		this.tokens = tokens;
	}

	//Parsing Expressions 
	public Expr parsear() {
		try {
			return expressao();
		} catch (java.util.EmptyStackException error) {
			throw new ParasearErro("expressão binop incompleta");
		}
	}

	private Expr expressao() {
		while (!ehFinal()) {
			if(this.match(TokenType.NUM)) {
				this.pilha.push(this.number());
			}
			else if(this.match(TokenType.PLUS, TokenType.MINUS, 
					TokenType.SLASH, TokenType.STAR)) {
				this.pilha.push(this.binop());
			} else if (this.match(TokenType.ID)) {
				this.pilha.push(this.id());
			}
			this.avancar();
		}
		return this.pilha.pop();
	}

	private Expr number() {
		return new Expr.Number(atual().lexema);
	}

	private Expr binop() {
		return new Expr.Binop(this.pilha.pop(), this.pilha.pop(), this.atual());
	}

	private Expr id() {
		return new Expr.Id(atual().lexema);
	}

	private boolean match(TokenType... types) {
		for (TokenType type : types) {
			if (checagem(type)) {
				return true;
			}
		}

		return false;
	}

	private boolean ehFinal() {
		return atual().tipoDoToken == TokenType.EOF;
	}

	private Token avancar() {
		if (!ehFinal()) atual++;
		return anterior();
	}

	private Token atual() {
		return tokens.get(atual);
	}

	private Token anterior() {
		return tokens.get(atual - 1);
	}

	private boolean checagem(TokenType type) {
		if (ehFinal()) return false;
		return atual().tipoDoToken == type;
	}
}
