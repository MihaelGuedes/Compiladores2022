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

package postfix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import postfix.ast.AstImpressor;
import postfix.ast.Expr;
import postfix.interpreter.Interpreter;
import postfix.lexer.lexamaError;
import postfix.lexer.Scanner;
import postfix.lexer.Token;
import postfix.parser.Parseador;
import postfix.parser.ParasearErro;

/**
 * @author Henrique Rebelo
 */

public class Main {
    private static final Interpreter interpreter = new Interpreter(new HashMap<String, String>());
    private static boolean error = false;
    private static boolean debugging = false;

    
    public static void main(String[] args) throws IOException {

        debugging = false;
        rodar(args, debugging);
    }
    
    private static void rodarArquivo(String caminhoDoArquivoPrincipal) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(caminhoDoArquivoPrincipal));
        String progFont =
                new String(bytes, Charset.defaultCharset());
        
        rodar(progFont);

        if (error) System.exit(65);
    }
    
    private static void rodarPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            rodar(line);
            error = false;
        }
    }

    private static void rodar(String source) {
        try {
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scan();

            if(debugging) {
                imprimirTokens(tokens);
            }

            Parseador parseador = new Parseador(tokens);
            Expr expr = parseador.parsear();
            if(debugging) {
                imprimirAST(expr);
            }

            interpreter.ambiente.put("x", "10");
            interpreter.ambiente.put("y", "7");
            interpreter.ambiente.put("z", "5");

            System.out.println(interpreter.interp(expr));
        } catch (lexamaError e) {
            error("Lexema", e.getMessage());
            error = true;
        }
        catch (ParasearErro e) {
            error("Parseador", e.getMessage());
            error = true;
        }
    }

    private static void rodar(String[] args, boolean debugging) throws IOException {
        Main.debugging = debugging;
        if(args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                rodarArquivo(args[i]);
            }
        }
        else {
            rodarPrompt();
        }
    }

    private static void imprimirAST(Expr expr) {
        System.out.println("postfix.ast: "+new AstImpressor().impressao(expr));
        System.out.println();
    }

    private static void imprimirTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println();
    }

    private static void error(String tipo, String mensagem) {
        reportar(tipo, mensagem);
    }

    private static void reportar(String tipo, String mensagem) {
        System.err.println(
                "[" + tipo + "] Error: " + mensagem);
        error = true;
    }
}