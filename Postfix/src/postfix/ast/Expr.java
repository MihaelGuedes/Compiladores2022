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

package postfix.ast;

import postfix.lexer.Token;

/**
 * @author Henrique Rebelo
 */
public abstract class Expr {
	public interface Visitor<T> {
		T expressaoDoNumeroDeVisita(Number expr);
		T expressaoDaVisitaBinop(Binop expr);
		T expressaoIdDaVisita(Id expr);
	}

	public static class Number extends Expr {
		public Number(String valor){
			this.valor = valor;
		}

		@Override
		public <T> T aceitar(Visitor<T> visitor) {
			return visitor.expressaoDoNumeroDeVisita(this);
		}

		public final String valor;
	}

	public static class Binop extends Expr {
		public Binop(Expr esquerda, Expr direita, Token operator) {
			this.esquerda = esquerda;
			this.direita = direita;
			this.operator = operator;
		}

		@Override
		public <T> T aceitar(Visitor<T> visitor) {
			return visitor.expressaoDaVisitaBinop(this);
		}

		public final Expr esquerda;
		public final Expr direita;
		public final Token operator;
	}

	public abstract <T> T aceitar(Visitor<T> visitor);
    public static class Id extends Expr {
		public Id (String valor) {
			this.valor = valor;
		}

		@Override
		public <T> T aceitar(Visitor<T> visitante) {
			return visitante.expressaoIdDaVisita(this);
		}

		public final String valor;
    }
}
