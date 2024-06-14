package aed;

public interface Diccionario<K,V> {

    public Diccionario<K,V> crearDiccionario();

    public void definir(K clave, V valor);

    public Boolean pertenece();

    public void borrar(K clave);

    public V obtener(K clave);

}
