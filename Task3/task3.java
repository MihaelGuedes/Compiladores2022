import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class task3 {
    public static void main(String[] args) throws Exception {
        Stack<String> pilha = new Stack<>();
        Path caminhoDoArquivo = Path.of("./Calc3.stk");
        List<String> linhas = Files.readAllLines(caminhoDoArquivo);
        List<Token> listaDeTokens = new ArrayList<>();

        int primeiroValor, segundoValor, resultado = 0;

        for (String linha : linhas) {
            TokenType tokenType = Regex.verificarRegex(linha);
            Token token = new Token(tokenType, linha);
            listaDeTokens.add(token);
        }

        System.out.println(listaDeTokens);

        resultadoOperacao(listaDeTokens, pilha);
    }

    private static void resultadoOperacao(List<Token> listaDeTokens, Stack<String>pilha) {
        while(!listaDeTokens.isEmpty()) {
            String operador = listaDeTokens.remove(0).lexeme;

            switch(operador){
                case "+":
                case "-":
                case "/":
                case "*":
                    int op1, op2;
                    op1 = Integer.parseInt(pilha.pop());
                    op2 = Integer.parseInt(pilha.pop());
                    switch(operador){
                        case "+":
                            pilha.push(Integer.toString(op1 + op2));
                            break;
                        case "-":
                            pilha.push(Integer.toString(op1 - op2));
                            break;
                        case "/":
                            pilha.push(Integer.toString(op1 / op2));
                            break;
                        case "*":
                            pilha.push(Integer.toString(op1 * op2));
                            break;
                    }
                    break;
                default:
                    pilha.push(operador);
                    break;
            }
        }
        
        System.out.println("Saída: " + pilha.pop());
    }
}
