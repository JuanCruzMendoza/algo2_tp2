package aed;

// Clase para representar el Diccionario Trie
public class DiccionarioTrie<V> {

    // Esta implementación de Diccionario al estar hecha con un Trie, asume que la clave siempre es una String

    private NodoTrie<V> raiz; // Nodo raíz del Trie

    // Invariante de Representación:
    // - El Trie es un árbol, por lo que no tiene ciclos, y tiene un nodo raíz .
    // - Si la raíz es null, el diccionario está vacío.
    // - Cada nodo tiene 256 hijos (que representan cada caracter de ASCII extendido), aunque pueden ser null.
    // - Si los hijos de un nodo valen null, entonces ese nodo es una hoja.
    // - Los nodos hojas tienen esFinDePalabra = True, y el resto tienen esFinDePalabra = False.
    // - Todas las hojas son alcanzables partiendo del nodo raíz.
    // - El camino desde la raíz hasta cualquier nodo corresponde a una clave String del diccionario.
    // - El diccionario no tiene claves repetidas.
    // - El atributo valor de un nodo es siempre null, a no ser que sea un nodo hoja, en cuyo caso tiene el valor (de tipo V) 
    // asociado a la palabra clave utilizada para llegar hasta ese nodo.
    // - Todos los nodos o tienen valor distinto de null o tiene algún hijo distinto de null.

    // Clase de nodos
    private class NodoTrie<T> {
        NodoTrie<V>[] hijos; // Array de nodos hijos
        T valor; // Valor asociado al nodo
        boolean esFinDePalabra; // Indicador de si es el final de una clave
    
        // Constructor de nodo
        @SuppressWarnings("unchecked")
        public NodoTrie() {
            hijos = new NodoTrie[255]; // Asumiendo caracteres ASCII extendido (con 256 caracteres)
            valor = null;
            esFinDePalabra = false;
        }
    }

    // Constructor de diccionario
    // O(1)
    public DiccionarioTrie() {
        raiz = new NodoTrie<>();
    }

    // Método para verificar si un nodo está vacío (sin hijos)
    private boolean estaVacioNodo(NodoTrie<V> nodo) {

        // Se ejecuta 256 veces (hijos tiene siempre la misma longitud)
        for (NodoTrie<V> hijo : nodo.hijos) {

            // O(1): comparacion
            if (hijo != null) {
                return false;
            }
        }
        return true;
        
    } // Complejidad estaVacioNodo(): 256 * O(1) = O(1)


    // Método para verificar si un diccionario esta vacio
    public boolean estaVacio(){

        // O(1): solo utiliza estaVacioNodo()
        return estaVacioNodo(this.raiz);
        
    } // Complejidad estaVacio(): O(1)


    // Método para insertar una clave con su valor asociado
    public void insertar(String clave, V valor) {
        NodoTrie<V> nodo_actual = raiz;

        // Se ejecuta |clave| veces: recorre los caracteres de clave 
        for (char c : clave.toCharArray()) {

            // O(1): comparaciones y asignaciones
            int indice = (int) c;
            if (nodo_actual.hijos[indice] == null) {
                nodo_actual.hijos[indice] = new NodoTrie<>();
            }
            nodo_actual = nodo_actual.hijos[indice];
        }

        // O(1): asignaciones
        nodo_actual.esFinDePalabra = true;
        nodo_actual.valor = valor;

    } // Complejidad insertar(): |clave| * O(1) + O(1) = O(|clave|)


    // Método para buscar una clave y obtener su valor
    public V buscar(String clave) {
        
        NodoTrie<V> nodo = raiz;

        // Se ejecuta |clave| veces: recorre los caracteres de clave 
        for (char c : clave.toCharArray()) {

            // O(1): asignaciones y comparaciones
            int indice = (int) c;
            if (nodo.hijos[indice] == null) {
                return null;
            }
            nodo = nodo.hijos[indice];
        }

        // O(1): comparacion y return
        if (nodo.esFinDePalabra){
            return nodo.valor;
        } else {
            return null;
        }

    } // Complejidad buscar(): |clave|*O(1) + O(1) = O(|clave|)


    // Método para verificar si una clave pertenece al Trie
    public boolean pertenece(String clave) {

        // O(|clave|): misma complejidad que buscar()
        return buscar(clave) != null;

    } // Complejidad pertenece(): O(|clave|)


    // Método para eliminar una clave del Trie
    public boolean eliminar(String clave) {

        // O(|clave|): misma complejidad que eliminarAux()
        return eliminarAux(raiz, clave, 0);

    } // Complejidad eliminar(): O(|clave|)


    // Método recursivo auxiliar para eliminar una clave del Trie
    private boolean eliminarAux(NodoTrie<V> actual, String clave, int indice) {

        // Caso base: se llega al nodo correspondiente al ultimo caracter de clave
        // O(1): comparaciones, asignaciones y estaVacioNodo()
        if (indice == clave.length()) {

            // Si el nodo actual no es el final de palabra, la clave no existe en el diccionario
            if (!actual.esFinDePalabra) {
                return false;
            }
            actual.esFinDePalabra = false;

            // Si no esta vacío, significa que la clave no pertenece al diccionario
            return estaVacioNodo(actual); // O(1)
        }

        // Obtenemos el índice del hijo y su nodo
        // O(1): asignaciones
        char c = clave.charAt(indice);
        int indiceHijo = (int) c;
        NodoTrie<V> nodo = actual.hijos[indiceHijo];

        // Si el nodo no existe, la clave no esta en el diccionario
        // O(1): comparacion
        if (nodo == null) {
            return false;
        }

        // Cambia el nodo hasta llegar recursivamente al nodo del ultimo caracter de clave
        // O(|clave|): recorre cada caracter de clave mediante un indice
        boolean debeEliminarNodoActual = eliminarAux(nodo, clave, indice + 1);

        // A la vuelta, elimina los nodos que ya no son necesarios
        // O(1): comparaciones y asignaciones
        if (debeEliminarNodoActual) {
            actual.hijos[indiceHijo] = null;

            // Verificamos si el nodo actual no tiene otros hijos y no es fin de palabra
            return !actual.esFinDePalabra && estaVacioNodo(actual);
        }

        return false;

    }  // Complejidad eliminarAux(): O(1) + O(1) + O(1) + O(|clave|) + O(1) = O(|clave|)


    // Método para obtener todas las claves almacenadas en el Trie
    public ListaEnlazada<String> obtenerClaves() {
        ListaEnlazada<String> resultado = new ListaEnlazada<>();

        // Busca las claves todas las claves y las agrega a la lista: O(n*|clave|)
        obtenerClavesAux(raiz, "", resultado);
        return resultado;

    } // Complejidad obtenerClaves(): O(n*|clave|)


    // Método recursivo auxiliar para obtener todas las claves almacenadas en el Trie
    private void obtenerClavesAux(NodoTrie<V> nodo, String prefijo, ListaEnlazada<String> resultado) {

        // Cuando llega a un nodo null, corta la funcion: O(1)
        if (nodo == null) {
            return;
        }

        // Si el nodo es el fin de la palabra clave, la agrega a la lista: O(1)
        if (nodo.esFinDePalabra) {
            resultado.agregarAtras(prefijo);
        }

        // Se ejecuta 256 veces (una por cada caracter de ASCII extendido)
        for (char c = 0; c < 255; c++) {

            // Para cada caracter, si el hijo del nodo no es null, agrega las claves recursivamente
            // Como recorre desde 0 hasta 255, las claves se van agregando en orden lexicografico
            if (nodo.hijos[c] != null) {
                obtenerClavesAux(nodo.hijos[c], prefijo + (char) c, resultado);
            }
        } 
        // En el peor caso, cada clave tiene todos los caracteres distintos y el Trie está lleno, pero cada recorrido de los nodos siempre 
        // lleva a una hoja con una clave asociada. Entonces, para cada clave sólo recorremos cada uno de sus caracteres: O(n * |clave|), 
        // siendo n la cantidad de claves y |clave| la longitud de la clave de mayor longitud.

    } // Complejidad obtenerClavesAux(): O(1) + O(1) + O(n*|clave|) = O(n*|clave|)

}
