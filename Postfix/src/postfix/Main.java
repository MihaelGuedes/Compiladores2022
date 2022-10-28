package postfix;

import java.nio.file.*;

public class Main {
    public static String lerArquivos(String nomeDoArquivo) throws Exception
    {
        String conteudo = "";
        conteudo = new String(Files.readAllBytes(Paths.get(nomeDoArquivo)));
        return conteudo;
    }
    public static void main(String[] args) throws Exception {
        Postfix postfix = new Postfix();
        String arquivoComDadosIncorretos = lerArquivos("src/postfix/Calc1.stk");
        String arquivoComDadosCorretos = lerArquivos("src/postfix/Calc2.stk");
        args = new String[2];
        args[0] = arquivoComDadosIncorretos;
        args[1] = arquivoComDadosCorretos;

        postfix.main(args);
    }
}
