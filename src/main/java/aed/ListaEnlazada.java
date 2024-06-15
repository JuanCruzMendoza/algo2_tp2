import java.util.*;

// Invariante Representativo.
// Si la lista está vacía (longitud = 0)entonces los punteros primero y ultimo valen null.
// Si la lista no está vacía (longitud > 0) entonces lo spunteros primero y ultimo no son null.
// el primero.sig tampoco es null para todas las posiciones intermedias.
// La longitud tiene que ser el numero de los nodos en la lista.
// Todos los nodos deben estar correctamente encadenados entre primero y ultimNodo,
// sin nodos colgantes o ciclos.
// El último nodo ("ultimNodo") debe tener su campo "sig" igual a null.

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimNodo;
    private int longitud;


    private class Nodo<T> {  // Constructor de la clase
        T valor;
        Nodo<T> sig;
    
        public Nodo(T v){  // Constructor del nodo
            valor = v;
            sig = null;
        }
    }
    public class ListaEnlazada<T> implements Secuencia<T> {
        private Nodo<T> primero;
        private Nodo<T> ultimNodo;
        private int longitud;
    
        public ListaEnlazada() {   // Constructor de la lista nueva tiene todo null/0
           primero = null;
           ultimNodo = null;
           longitud = 0; 
        }
    
        public int longitud() {
            return longitud;
        }
    // La complejidad es O(1), no depende del tamaño de la lista, 
    // ya que solo cambia la referencia al primero nodo y ajusta el puntero "primero".
        public void agregarAdelante(T elem) {
            Nodo<T> nuevo = new Nodo<>(elem); // O(1)
            if (primero != null) { // O(1)
                nuevo.sig = primero;
            } else { // O(1)
                ultimNodo = nuevo;
            }
            primero = nuevo; // O(1)
            longitud++; // O (1)
        }
    // La complejidad es O(1). Ya que tenemos referencia al ulitmoNodo
    // no es necesario recorrer la lista para llegar al nodo final.
    // Modifica solo las referencias del último nodo y ajusta el puntero "ultimoNodo".
        public void agregarAtras(T elem) {
            Nodo<T> nuevo = new Nodo<>(elem); // O(1)
            if (primero == null) { // O(1)
                primero = nuevo;
                ultimNodo = nuevo;
            } else { // O(1)
                ultimNodo.sig = nuevo;
                ultimNodo = nuevo;
            }
            longitud++; // O(1)
        }
    // La complejidad es O(n) donde n es es la longitud de la lista. 
    // En el peor caso i es la ulima posición y entonces se va a recorrer desde el principio hasta obtener el elemento
        public T obtener(int i) {
            Nodo<T> actual = primero; // O(1)
            for (int j = 0; j < i; j++) { //O(n)
                actual = actual.sig;
            }
            return actual.valor;
        }
    // La complejidad es O(n). Como en el caso anterior,
    // hasta encontrar el elemento para eliminar puede recorrer toda la longitud de la lista.
        public void eliminar(int i) {
            if (i == 0) { // O(1)
                primero = primero.sig;
                if (primero == null) {
                    ultimNodo = null;
                }
            } else { // O(1)
                Nodo<T> actual = primero;
                for (int j = 0; j < i - 1; j++) { // O(n)
                    actual = actual.sig;
                }
                actual.sig = actual.sig.sig;
                if (actual.sig == null) {
                    ultimNodo = actual;
                }
            }
            longitud--; // O(1)
        }
    // La complejidad es O(n). el indice puede estar en la ultima posición
    // en el peor caso, entonces reemplazamos el valor recien al econtrarnos con esa posición.
        public void modificarPosicion(int indice, T elem) {
            Nodo<T> actual = primero; // O(1)
            for (int j = 0; j < indice; j++) {  //O(n)
                actual = actual.sig;
            }
            actual.valor = elem; // O(1)
        }
    // O(n). Vamos a copiar cada elemento de la lista recorriendo toda su longitud.
        public ListaEnlazada<T> copiar() {
            ListaEnlazada<T> nuevaLista = new ListaEnlazada<>(); // O(1)
            Nodo<T> actual = this.primero; // O(1)
            while (actual != null) {  // O(n)
                nuevaLista.agregarAtras(actual.valor);
                actual = actual.sig;
            }
            return nuevaLista;
        }
    // O(n). 
        public ListaEnlazada(ListaEnlazada<T> lista) {
            Nodo<T> actual = lista.primero;
            while (actual != null) { // O(n)
                this.agregarAtras(actual.valor);
                actual = actual.sig;
            }
        }
    // O(n). recorre cada elemento para converirlo en string
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder("["); // O(1)
            Nodo<T> actual = primero; // O(1)
            while (actual != null) {  // O(n)
                res.append(actual.valor); 
                if (actual.sig != null) {
                    res.append(", ");  
                }
                actual = actual.sig; 
            }
            res.append("]"); // O(1)
            return res.toString();
        }
    
        private class ListaIterador implements Iterador<T> {
            Nodo<T> indice;
    // La complejidad es constante, creo una lista nueva
            public ListaIterador() {
                this.indice = null;  // O(1)
            }
    // La complejidad es constante.
    // La función solo involucra una o dos comparaciones de punteros y una asignación condicional.
            public boolean haySiguiente() {
                return (indice == null) ? primero != null : indice.sig != null;  // O(1)
            }
    // O(1). La función solo involucra una evaluación condicional, 
    // una asignación de referencia y el acceso a un valor del nodo.
            public T siguiente() {
                if (indice == null) {  // O(1)
                    indice = primero;
                } else {
                    indice = indice.sig;
                }
                return indice.valor;
            }
    
        }
    
        public Iterador<T> iterador() {
            return new ListaIterador();
        }
    }

    }
    