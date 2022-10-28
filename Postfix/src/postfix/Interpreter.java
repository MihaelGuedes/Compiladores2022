package postfix;/* *******************************************************************
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

import java.util.HashMap;

/**
 * @author Henrique Rebelo
 */
public class Interpreter implements Expression.Visitante<Integer> {
    public final HashMap<String, String> env;
    public Interpreter(HashMap<String, String> env) {
        // TODO Auto-generated constructor stub
        this.env = env;
    }

    public int interp(Expression expression) {
        int value = evaluate(expression);

        return value;
    }

    @Override
    public Integer visitaNumberExpr(Expression.Numero expressao) {
        return Integer.parseInt(expressao.valor);
    }

    @Override
    public Integer visitaBinopExpr(Expression.Binop expressao) {
        int left = evaluate(expressao.esquerda);
        int right = evaluate(expressao.direita);
        int result = 0;

        switch (expressao.operador.type) {
            case PLUS:
                result = left + right;
                break;
            case MINUS:
                result = left - right;
                break;
            case SLASH:
                result = left / right;
                break;
            case STAR:
                result = left * right;
                break;
            default:
                break;
        }

        return result;
    }

    @Override
    public Integer visitIdExpr(Expression.Id expressao) {
        if(!env.containsKey(expressao.value)) {
            throw new InterpreterError(expressao.value + " cannot be resolved");
        }
        return Integer.parseInt(env.get(expressao.value));
    }

    private int evaluate(Expression expression) {
        return expression.aceitar(this);
    }
}