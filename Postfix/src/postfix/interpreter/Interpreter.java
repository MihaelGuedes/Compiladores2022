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

package postfix.interpreter;

import postfix.ast.Expr;
import postfix.ast.Expr.Id;

import java.util.HashMap;

/**
 * @author Henrique Rebelo
 */
public class Interpreter implements Expr.Visitor<Integer> {

	public final HashMap<String, String> ambiente;

	public Interpreter (HashMap<String, String> ambiente) {
		this.ambiente = ambiente;
	}
	
	public int interp(Expr expressao) {
		int valor = avaliador(expressao);
		
		return valor;
	}

	@Override
	public Integer expressaoDoNumeroDeVisita(Expr.Number expressao) {
		return Integer.parseInt(expressao.valor);
	}

	@Override
	public Integer expressaoDaVisitaBinop(Expr.Binop expr) {
		int esquerda = avaliador(expr.esquerda);
		int direita = avaliador(expr.direita);
		int resposta = 0;

		switch (expr.operator.tipoDoToken) {
		case PLUS:
			resposta = esquerda + direita;
			break;
		case MINUS:
			resposta = esquerda - direita;
			break;
		case SLASH:
			resposta = esquerda / direita;
			break;
		case STAR:
			resposta = esquerda * direita;
			break;
		default:
			break;
		}

		return resposta;
	}

	private int avaliador(Expr expressao) {
		return expressao.aceitar(this);
	}

	@Override
	public Integer expressaoIdDaVisita(Id expressao) {
		return null;
	}
}
