package postfix.ast;

import postfix.ast.Expr.Binop;
import postfix.ast.Expr.Id;
import postfix.ast.Expr.Number;

public class AstImpressor implements Expr.Visitor<String>{

	public String impressao(Expr expressao) {
		return expressao.aceitar(this);
	}

	@Override
	public String expressaoDoNumeroDeVisita(Number expressao) {
		return expressao.valor.toString();
	}

	@Override
	public String expressaoDaVisitaBinop(Binop expressao) {
		return PreOrdemEntreParenteses(expressao.operator.lexema,
				expressao.esquerda, expressao.direita);
	}

	private String PreOrdemEntreParenteses(String name, Expr... expressoes) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("(").append(name);
		for (Expr expresssao : expressoes) {
			buffer.append(" ");
			buffer.append(expresssao.aceitar(this));
		}
		buffer.append(")");

		return buffer.toString();
	}

	@Override
	public String expressaoIdDaVisita(Id expr) {
		return null;
	}
}
