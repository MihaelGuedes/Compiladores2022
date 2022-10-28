package postfix;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * @author Henrique Rebelo
 */
public class Postfix {

    private static final Interpreter interpretador = new Interpreter(new HashMap<String, String>());
    private static boolean temErros = false;
    private static boolean debuger = false;

    public static void main(String[] args) throws IOException {
        debuger = false;
        rodar(args, debuger);
    }

    private static void rodarArquivo(String caminhoDoArquivo) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(caminhoDoArquivo));
        String programaFonte = new String(bytes, Charset.defaultCharset());
        rodar(programaFonte);

        if (temErros)
            System.exit(65);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader entrada = new InputStreamReader(System.in);
        BufferedReader leitor = new BufferedReader(entrada);

        for (;;) {
            System.out.print("> ");
            String line = leitor.readLine();
            if (line == null)
                break;
            rodar(line);
            temErros = false;
        }
    }

    private static <LexError extends Throwable> void rodar(String source) {
        try {
            interpretador.env.put("y", "10");

            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scan();

            // debugging for tokens
            if (debuger) {
                imprimirTokens(tokens);
            }

            Parseador parseador = new Parseador(tokens);
            Expression expression = parseador.parsear();

            // debugging for parsing/ast
            if (debuger) {
                printAST(expression);
            }
            System.out.println(interpretador.interp(expression));
        } catch (ErroLexama e) {
            error("Lex", e.getMessage());
            temErros = true;
        } catch (ErroParseador e) {
            error("Parser", e.getMessage());
            temErros = true;
        } catch (InterpreterError e) {
            // TODO: handle exception
            error("Interpreter", e.getMessage());
            temErros = true;
        }
    }

    private static void rodar(String[] args, boolean debugging) throws IOException {
        Postfix.debuger = debugging;
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                rodar(args[i]);
            }
        } else {
            runPrompt();
        }
    }

    private static void printAST(Expression expression) {
        System.out.println("ast: " + new ImprimirAst().print(expression));
        System.out.println();
    }

    private static void imprimirTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println();
    }

    private static void error(String kind, String mensagem) {
        report(kind, mensagem);
    }

    private static void report(String tipo, String mensagem) {
        System.err.println("[" + tipo + "] Error: " + mensagem);
        temErros = true;
    }
}