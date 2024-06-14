package aed;

import java.util.ArrayList;

public class DiccionarioTrie<K,V> implements Diccionario<K,V>{

    ArrayList<Nodo<K>> raiz;

    class Nodo<T> {
        T valor;
        Nodo<T> izquierda;
        Nodo<T> derecha;
        Nodo<T> padre;

        public Nodo(T v) {
            valor = v;
            izquierda = null;
            derecha = null;
            padre = null;
        }
    }
    

    public DiccionarioTrie<K,V> crearDiccionario(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void definir(K clave, V valor){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public Boolean pertenece(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void borrar(K clave){
        throw new UnsupportedOperationException("No implementada aun");
    }

    public V obtener(K clave){
        throw new UnsupportedOperationException("No implementada aun");
    }
}
