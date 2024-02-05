// 4645G-04 - Algoritmos e Estruturas de Dados I
// 2023-1

import java.util.ArrayList;
import java.util.List;

public class WordTree {
    
    // Classe interna
    private class CharNode {
        private char character;
	    private String significado;
        private boolean isFinal;
        private CharNode father;
        private List<CharNode> children;

        public CharNode(char character) {
            this.character = character;
            father = null;
            children = new ArrayList<CharNode>();
        }
        
        public CharNode(char character, boolean isFinal) {
            this.character = character;
            this.isFinal = isFinal;
            father = null;
            children = new ArrayList<CharNode>();
        }

        /**
        * Adiciona um filho (caracter) no nodo. Não pode aceitar caracteres repetidos.
        * @param character - caracter a ser adicionado
        * @param isfinal - se é final da palavra ou não
        */
        public CharNode addChild (char character, boolean isfinal) { //cria um char novo
            CharNode n = new CharNode(character, isfinal);
            n.father = this;
            children.add(n);
            return n;
        }
        
        public int getNumberOfChildren() { //busca tamanho da subtree, similar ao método as árvores genéricas
            return children.size();
        }
        
        public CharNode getChild(int index) { //busca um nodo filho especifico
            return children.get(index);
        }

        /**
         * Obtém a palavra correspondente a este nodo, subindo até a raiz da árvore
         * @return a palavra
         */
        private String getWord() { //busca a palavra
            CharNode aux = this;
            String palaux = "", palauxs = "";

            while (aux.father != null){ //adiciona chars em um string
                palaux += String.valueOf(aux.character);
                aux = aux.father;
            }

            for (int i = palaux.length()-1; i > -1; i--){ //reverte a string
                palauxs += String.valueOf(palaux.charAt(i));
            }

            return palauxs;
        }
        
        /**
        * Encontra e retorna o nodo que tem determinado caracter.
        * @param character - caracter a ser encontrado.
        */
        public CharNode findChildChar (char character) { 
            for (CharNode c : children) { //usa um foreach buscando a char criança do nodo
                if (c.character == character){
                    return c;
                }
            }
            return null;
        }
        
    }


    
    // Atributos
    private CharNode root;
    private int totalNodes;
    private int totalWords;
    


    // Construtor
    public WordTree() {
        root = new CharNode(' '); //necessidade, pois não estava funcionando sem isso
        totalNodes = 0;
        totalWords = 0;
    }


    
    // Metodos
    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalNodes() {
        return totalNodes;
    }
    
    /**
    *Adiciona palavra na estrutura em árvore
    *@param word
    */
    public void addWord(String word, String significado) {
        CharNode aux = root;
        CharNode auxChild;
        for (int i = 0; i < word.length(); i++) {
            char charaux = word.charAt(i);
            auxChild = aux.findChildChar(charaux);
            if (auxChild == null) {
                boolean isFinal = (i == word.length() - 1); //praticidade
                auxChild = aux.addChild(charaux, isFinal); //tentei fazer 2 ifs, mas dava erros 
            if (isFinal) {
                auxChild.significado = significado;
                }
            totalNodes++;
            }
        aux = auxChild;
        }
        totalWords++;
    }
    
    /**
     * Vai descendo na árvore até onde conseguir encontrar a palavra
     * @param word
     * @return o nodo final encontrado
     */
    private CharNode findCharNodeForWord(String word) { //busca último char da palavra fornecida (especialmente util para significado)
        CharNode aux = root;
        for (int i=0; i<word.length(); i++){
            char charaux = word.charAt(i);
            CharNode auxChild = aux.findChildChar(charaux);
            
            if (auxChild == null) return null;

            aux = auxChild;
        }
        return aux;
    }

    /**
    * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo dado.
    * Tipicamente, um método recursivo.
    * @param prefix
    */
    public List<String> searchAll(String prefix) { //busca todas as palavras
        List<String> lista = new ArrayList<String>();
        CharNode n = findCharNodeForWord(prefix);

        if (n != null) searchAll(n, lista);

        return lista;
    }   

    private void searchAll(CharNode n, List<String> lista){ //método recursivo do acima
        if (n.isFinal) {
            lista.add(n.getWord());
        }

        for (CharNode nchild : n.children){ //aqui usado um foreach (peguei da aula de POO)
            searchAll(nchild, lista);
        }
    }

    public String getSignificado(String palavra){ //facilidade
        CharNode n = findCharNodeForWord(palavra);
        return n.significado;
    }

}
