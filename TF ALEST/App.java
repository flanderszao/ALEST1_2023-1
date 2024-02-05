
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        boolean fim = false;
        Scanner teclado = new Scanner(System.in);

        WordTree arvore = new WordTree();
        String aux[];
                
        Path path1 = Paths.get("dicionario.csv");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {// Charset.defaultCharset())
            String line = reader.readLine();
            while (line != null) {
                aux = line.split(";");
                if(arvore.getTotalNodes()==0) {
                    aux[0] = aux[0].substring(1);
                    //System.out.println(aux[0]); testar se está funcionando
                }
                Palavra p = new Palavra(aux[0],aux[1]); //só por via das dúvidas
                arvore.addWord(p.getPalavra().toLowerCase(), p.getSignificado()); //usa o palavra acima para ter certeza que significado e palavra funcionarão
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        } 
        
        while (!fim){ //loop para interface do usuário
            System.out.println("Dicionario básico Ver 0.5 (escreva 0 para parar)");
            System.out.println("Escreva uma palavra (ou parte de uma) em letra minúscula: ");
            String palavra = teclado.nextLine(); //controle de usuário

            if(palavra != null) System.out.println("\n"+arvore.searchAll(palavra)); //busca todas as palavras similares

            if (arvore.getSignificado(palavra) != null){ //busca se é a palavra completa
                System.out.println("\n"+palavra+": "+arvore.getSignificado(palavra)); //imprime palavra+significado se for true
            }

            System.out.println("------------------------");
            
            if (palavra == "0") fim = true; //fim de laço
        }

    }
 
}
