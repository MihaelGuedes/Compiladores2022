package postfix;

import java.util.Stack;

public class ImprimirAst implements Expression.Visitante<String> {

    public String print(Expression expression) {
        return expression.aceitar(this);
    }

    @Override
    public String visitaBinopExpr(Expression.Binop expressao) {
        return PreOrdem(expressao.operador.lexeme,
                expressao.esquerda, expressao.direita);
    }

    @Override
    public String visitaNumberExpr(Expression.Numero expressao) {
        return expressao.valor.toString();
    }

    @Override
    public String visitIdExpr(Expression.Id expressao) {
        return expressao.value.toString();
    }

    private String PreOrdem(String nome, Expression... expressoes) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("(").append(nome);
        for (Expression expressao : expressoes) {
            buffer.append(" ");
            buffer.append(expressao.aceitar(this));
        }
        buffer.append(")");

        return buffer.toString();
    }

    public boolean ehBalancaDeEquilibrio(String expressao) {
        if (expressao.isEmpty()) {
            return true;
        }

        Stack<Character> pilha = new Stack<Character>();
        for (int i = 0; i < expressao.length(); i++) {
            char atual = expressao.charAt(i);
            if (atual == '(') {
                pilha.push(atual);
            }
            if (atual == ')') {
                if (pilha.isEmpty()) {
                    return false;
                }
                char ultimo = pilha.peek();
                if (atual == ')' && ultimo == '(') {
                    pilha.pop();
                }
                else {
                    return false;
                }
            }
        }

        return pilha.isEmpty() ? true : false;
    }


}