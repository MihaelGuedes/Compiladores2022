import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class task2RPNSS {
    public static void main(String[] args) throws Exception {
        Stack<String> pilha = new Stack<>();
        Path caminhoDoArquivo = Path.of("./Calc2.stk");
        List<String> linhas = Files.readAllLines(caminhoDoArquivo);
        List<Token> listaDeTokens = new ArrayList<>();

        int primeiroValor, segundoValor, resultado = 0;

        for (String linha : linhas) {
            if (linha.matches("[0-9]*")) {
                Token token = new Token(TokenType.NUM, linha);
                listaDeTokens.add(token);
            } else if (linha.equals("+")) {
                Token token = new Token(TokenType.PLUS, linha);
                listaDeTokens.add(token);
            } else if (linha.equals("-")) {
                Token token = new Token(TokenType.MINUS, linha);
                listaDeTokens.add(token);
            } else if (linha.equals("/")) {
                Token token = new Token(TokenType.SLASH, linha);
                listaDeTokens.add(token);
            } else if (linha.equals("*")) {
                Token token = new Token(TokenType.STAR, linha);
                listaDeTokens.add(token);
            } else {
                throw new Exception("Error: Unexpected character: " + linha);
            }
        }

        System.out.println(listaDeTokens);

        while(!listaDeTokens.isEmpty()){
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
