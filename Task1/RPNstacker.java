import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

public class RPNstacker {
    public static void main(String[] args) throws IOException {
        Stack<Integer> pilha = new Stack<>();
        Path caminhoDoArquivo = Path.of("./Calc1.stk");
        List<String> linhas = Files.readAllLines(caminhoDoArquivo);

        int primeiroValor, segundoValor, resultado = 0;

        for (String token : linhas) {
            if (token.equals("+") || token.equals("-") ||
                    token.equals("*") || token.equals("/") || token.equals("%")) {

                primeiroValor = pilha.pop();
                segundoValor = pilha.pop();
                resultado = calcularOperacao(token, primeiroValor, segundoValor);
                pilha.push(resultado);
            } else {

                pilha.push(Integer.valueOf(token));
            }
        }

        System.out.println(resultado);
    }

    private static int calcularOperacao(String operador, int op1, int op2) {
        int resultado = 0;
        switch (operador) {
            case "+":
                resultado = op1 + op2;
                break;
            case "-":
                resultado = op1 - op2;
                break;
            case "*":
                resultado = op1 * op2;
                break;
            case "/":
                resultado = op1 / op2;
                break;
            case "%":
                resultado = op1 % op2;
                break;
        }
        return resultado;
    }
}
