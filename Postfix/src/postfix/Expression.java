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

/**
 * @author Henrique Rebelo
 */
public abstract class Expression {

    // visitors for expressions
    public interface Visitante<T> {
        T visitaNumberExpr(Numero expressao);
        T visitaBinopExpr(Binop expressao);
        T visitIdExpr(Id expressao);
    }

    // Nested Expr classes here

    // Number expression
    public static class Numero extends Expression {
        public Numero(String valor){
            this.valor = valor;
        }

        @Override
        public <T> T aceitar(Visitante<T> visitante) {
            return visitante.visitaNumberExpr(this);
        }

        public final String valor;
    }

    // Binop expression
    public static class Binop extends Expression {
        public Binop(Expression esquerda, Expression direita, Token operador) {
            this.esquerda = esquerda;
            this.direita = direita;
            this.operador = operador;
        }

        @Override
        public <T> T aceitar(Visitante<T> visitante) {
            return visitante.visitaBinopExpr(this);
        }

        public final Expression esquerda;
        public final Expression direita;
        public final Token operador;
    }

    // ID expression
    public static class Id extends Expression {
        public Id(String valor){
            this.value = valor;
        }

        @Override
        public <T> T aceitar(Visitante<T> visitor) {
            return visitor.visitIdExpr(this);
        }

        public final String value;
    }

    public abstract <T> T aceitar(Visitante<T> visitor);
}