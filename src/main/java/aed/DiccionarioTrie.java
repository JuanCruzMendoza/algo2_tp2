package aed;

class TrieNode<V> {
    TrieNode<V>[] hijos; // Array de nodos hijos
    V valor; // Valor asociado al nodo
    boolean esFinDePalabra; // Indicador de si es el final de una palabra

    @SuppressWarnings("unchecked")
    public TrieNode() {
        hijos = new TrieNode[26]; // Asumiendo solo letras minúsculas 'a' a 'z'
        valor = null;
        esFinDePalabra = false;
    }
}

// Clase para representar el Diccionario Trie
public class DiccionarioTrie<V> {
    private TrieNode<V> raiz; // Nodo raíz del Trie

    // Constructor
    public DiccionarioTrie() {
        raiz = new TrieNode<>();
    }

    // Método para insertar una palabra con su valor asociado
    public void insertar(String palabra, V valor) {
        TrieNode<V> nodo = raiz;
        for (char c : palabra.toCharArray()) {
            int indice = c - 'a';
            if (nodo.hijos[indice] == null) {
                nodo.hijos[indice] = new TrieNode<>();
            }
            nodo = nodo.hijos[indice];
        }
        nodo.esFinDePalabra = true;
        nodo.valor = valor;
    }

    // Método para buscar una palabra y obtener su valor
    public V buscar(String palabra) {
        TrieNode<V> nodo = raiz;
        for (char c : palabra.toCharArray()) {
            int indice = c - 'a';
            if (nodo.hijos[indice] == null) {
                return null;
            }
            nodo = nodo.hijos[indice];
        }
        return nodo.esFinDePalabra ? nodo.valor : null;
    }

    // Método para verificar si una palabra completa pertenece al Trie
    public boolean pertenece(String palabra) {
        return buscar(palabra) != null;
    }

    // Método para eliminar una palabra del Trie
    public boolean eliminar(String palabra) {
        return eliminar(raiz, palabra, 0);
    }

    // Método recursivo auxiliar para eliminar una palabra del Trie
    private boolean eliminar(TrieNode<V> actual, String palabra, int indice) {
        if (indice == palabra.length()) {
            if (!actual.esFinDePalabra) {
                return false;
            }
            actual.esFinDePalabra = false;
            return actual.valor == null && estaVacio(actual);
        }

        char c = palabra.charAt(indice);
        int indiceHijo = c - 'a';
        TrieNode<V> nodo = actual.hijos[indiceHijo];

        if (nodo == null) {
            return false;
        }

        boolean debeEliminarNodoActual = eliminar(nodo, palabra, indice + 1);

        if (debeEliminarNodoActual) {
            actual.hijos[indiceHijo] = null;
            return actual.valor == null && !actual.esFinDePalabra && estaVacio(actual);
        }

        return false;
    }

    // Método para verificar si un nodo está vacío (sin hijos)
    private boolean estaVacio(TrieNode<V> nodo) {
        for (TrieNode<V> hijo : nodo.hijos) {
            if (hijo != null) {
                return false;
            }
        }
        return true;
    }

    // Método principal para pruebas
    public static void main(String[] args) {
        DiccionarioTrie<String> trie = new DiccionarioTrie<>();
        trie.insertar("manzana", "Una fruta");
        trie.insertar("man", "Abreviatura de hombre");
        trie.insertar("mango", "Otra fruta");

        System.out.println(trie.buscar("manzana"));   // devuelve "Una fruta"
        System.out.println(trie.buscar("man"));       // devuelve "Abreviatura de hombre"
        System.out.println(trie.buscar("mango"));     // devuelve "Otra fruta"

        trie.eliminar("manzana");
        System.out.println(trie.buscar("manzana"));   // devuelve null
        System.out.println(trie.buscar("man"));       // devuelve "Abreviatura de hombre"

        System.out.println(trie.pertenece("man"));     // devuelve true
        System.out.println(trie.pertenece("manz"));    // devuelve false
        System.out.println(trie.pertenece("banana"));  // devuelve false
    }
}
