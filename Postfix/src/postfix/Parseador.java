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

import java.util.List;
import java.util.Stack;
/**
 * @author Henrique Rebelo
 */
public class Parseador {

    private final List<Token> tokens;
    // The internal stack used for shift-reduce parsing
    private Stack<Expression> stack = new Stack<>();
    private int current = 0;

    public Parseador(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Parsing Expressions
    public Expression parsear() {
        try {
            return expressao();
        } catch (java.util.EmptyStackException error) {
            throw new ErroParseador("incomplete binop expression");
        }
    }

    // -------------------------------------------------------------
    // HELPERS METHODS
    // -------------------------------------------------------------
    private Expression expressao() {
        while (!chegandoNoFinal()) {
            if (this.match(TokenType.NUM)) {
                this.stack.push(this.numero());
            }
            // matching any of the operation tokens
            else if (this.match(TokenType.PLUS, TokenType.MINUS, TokenType.SLASH, TokenType.STAR)) {
                this.stack.push(this.binop());
            } else if (this.match(TokenType.ID)) {
                this.stack.push(this.id());
            }
            this.avancar();
        }
        return this.stack.pop();
    }

    private Expression numero() {
        return new Expression.Numero(pegar().lexeme);
    }

    private Expression id() {
        return new Expression.Id(pegar().lexeme);
    }

    private Expression binop() {
        Expression right = this.stack.pop();
        Expression left = this.stack.pop();
        return new Expression.Binop(left, right, this.pegar());
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (checar(type)) {
                return true;
            }
        }

        return false;
    }

    private boolean checar(TokenType type) {
        if (chegandoNoFinal())
            return false;
        return pegar().type == type;
    }

    private Token avancar() {
        if (!chegandoNoFinal())
            current++;
        return anterior();
    }

    private boolean chegandoNoFinal() {
        return pegar().type == TokenType.EOF;
    }

    private Token pegar() {
        return tokens.get(current);
    }

    private Token anterior() {
        return tokens.get(current - 1);
    }
}