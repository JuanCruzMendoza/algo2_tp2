package aed;

class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    String definition;

    public TrieNode() {
        children = new TrieNode[26]; // 26 letras del alfabeto inglés
        isEndOfWord = false;
        definition = null;
    }
}

public class DiccionarioTrie {
    private final TrieNode root;

    public DiccionarioTrie() {
        root = new TrieNode();
    }

    // Método para insertar una palabra con su definición en el Trie
    public void insert(String word, String definition) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
        current.definition = definition;
    }

    // Método para buscar una palabra en el Trie
    public String search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current.isEndOfWord ? current.definition : null;
    }



    public static void main(String[] args) {
        DiccionarioTrie diccionario = new DiccionarioTrie();

        diccionario.insert("hello", "a greeting");
        diccionario.insert("world", "the earth, together with all of its countries and peoples");

        System.out.println(diccionario.search("hello")); // "a greeting"
        System.out.println(diccionario.search("hell"));  // null
        System.out.println(diccionario.search("world")); // "the earth, together with all of its countries and peoples"
    }
}
