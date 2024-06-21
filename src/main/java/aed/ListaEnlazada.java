
package aed;

public class ListaEnlazada<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimNodo;
    private int longitud;

    // Invariante de representacion:
    // - Si la lista está vacía, longitud = 0 y los punteros primero y último valen null.
    // - Si la lista no está vacía, longitud > 0 y el puntero primero es distinto de null y apunta al primer nodo de la lista.
    // - Si longitud > 1, el puntero ultimNodo también es distinto de null y apunta al último nodo de la lista.
    // - La longitud es la cantidad de nodos de la lista, desde primero hasta ultimNodo.
    // - Todos los nodos deben estar correctamente encadenados entre primero y ultimNodo, sin nodos intermedios apuntando a null o ciclos.
    // - Cada nodo apunta a otro, excepto el último.
    // - El último nodo apunta a null (ultimNodo.sig = null)

    // Clase nodo
    private class Nodo<K> { 
        T valor;
        Nodo<K> sig;
        
        // Constructor del nodo
        public Nodo(T v){ 
            valor = v;
            sig = null;
        }
    }
    
    // Constructor de la lista nueva (tiene todo null)
    public ListaEnlazada() {   
       primero = null;
       ultimNodo = null;
       longitud = 0; 
    } // Complejidad: O(1)
    

    // Devuelve la longitud de la lista
    public int longitud() {
        return longitud;

    } // Complejidad longitud(): O(1)


    // Agrega un elemento al principio de la lista
    public void agregarAdelante(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem); // O(1)

        // Cambia el puntero primero
        if (primero != null) { // O(1)
            nuevo.sig = primero;
        } else { // O(1)
            ultimNodo = nuevo; // si sólo hay un elemento, el primer y último nodo es el mismo
        }
        primero = nuevo; // O(1)
        longitud++; // O (1)

    } // Complejidad agregarAdelante(): O(1)


    // Agregar un elemento al final de la lista
    public void agregarAtras(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem); // O(1)

        // No es necesario recorrer la lista para llegar al nodo final, 
        // sino que podemos modificar la referencia al último nodo
        if (primero == null) { // O(1)
            primero = nuevo;
            ultimNodo = nuevo;
        } else { // O(1)
            ultimNodo.sig = nuevo;
            ultimNodo = nuevo;
        }
        longitud++; // O(1)

    } // Complejidad agregarAtras(): O(1)


    // Devuelve un elemento de la lista, dada su posicion
    public T obtener(int i) {
        Nodo<T> actual = primero; // O(1)

        // En el peor caso, el elemento está al final de la lista y debe recorrerla toda: O(n),
        // donde n es la longitud de la lista
        for (int j = 0; j < i; j++) { 
            actual = actual.sig;
        }
        return actual.valor;

     } // Complejidad obtener(): O(n)


    // Elimina un elemento de la lista, dada su posición
    public void eliminar(int i) {

        // Caso en el que se quiera eliminar la primera posición: O(1)
        if (i == 0) { // O(1)
            primero = primero.sig;
            if (primero == null) {
                ultimNodo = null;
            }
        } else {
            Nodo<T> actual = primero;

            // En el peor caso, se debe recorrer toda la lista para eliminar el elemento: O(n)
            for (int j = 0; j < i - 1; j++) { 
                actual = actual.sig;
            }
            actual.sig = actual.sig.sig;
            if (actual.sig == null) {
                ultimNodo = actual;
            }
        }
        longitud--; // O(1)

    } // Complejidad eliminar(): O(n)

    
    // Modificamos el valor de una posición de la lista
    public void modificarPosicion(int indice, T elem) {
        Nodo<T> actual = primero; // O(1)

        // En el peor caso, recorremos toda la lista para modificar la última posición: O(n)
        for (int j = 0; j < indice; j++) {  
            actual = actual.sig;
        }
        actual.valor = elem; // O(1)

    } // Complejidad modificarPosicion(): O(n)


    // Copiamos una lista
    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<>(); // O(1)
        Nodo<T> actual = this.primero; // O(1)

        // Copiamos cada elemento recorriendo toda su longitud: O(n)
        while (actual != null) {  // O(n)
            nuevaLista.agregarAtras(actual.valor);
            actual = actual.sig;
        }
        return nuevaLista;

    } // Complejidad copiar(): O(n)


    // Copiamos una lista pasada como parámetro
    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo<T> actual = lista.primero;

        // Copiamos cada elemento recorriendo toda su longitud: O(n)
        while (actual != null) { 
            this.agregarAtras(actual.valor);
            actual = actual.sig;
        }
    } // Complejidad ListaEnlazada(lista): O(n)


    // Convierte una lista en una String
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("["); // O(1)
        Nodo<T> actual = primero; // O(1)

        // Recorre cada elemento para convertirlo en String: O(n)
        while (actual != null) {  
            res.append(actual.valor); // O(1)
            if (actual.sig != null) {
                res.append(", ");  // O(1)
            }
            actual = actual.sig; 
        }
        res.append("]"); // O(1)
        return res.toString();

    } // Complejidad toString(): O(n)
    

    // Clase para armar un iterador de la lista
    public class IteradorLista {
        ListaEnlazada<T>.Nodo<T> indice;

        // Constructor de iterador
        public IteradorLista() {
            this.indice = null;  // O(1)
        }

        // Se fija si hay un elemento más en la lista
        public boolean haySiguiente() {

            // Uuna o dos comparaciones de punteros y una asignación condicional: O(1)
            return (indice == null) ? primero != null : indice.sig != null; 

        } // Complejidad haySiguiente(): O(1)


        // O(1). 
        // Devuelve el siguiente elemento de la lista
        public T siguiente() {

            // El método sólo involucra una evaluación condicional,  una asignación de referencia y el acceso a un valor del nodo: O(1)
            if (indice == null) { 
                indice = primero;
            } else {
                indice = indice.sig;
            }
            return indice.valor;
        } // Complejidad siguiente(): O(1)
    } 
    
    // Construye un iterador
    public IteradorLista iterador() {
        return new IteradorLista();
    } // Complejidad iterador(): O(1)
}

    
    