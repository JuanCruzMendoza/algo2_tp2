interface Iterador<T> {

    public boolean haySiguiente();
    
    /**
     * Devuelve true si hay un elemento anterior en la colección.
     * 
     */
    public T siguiente();

    /**
     * Devuelve el elemento anterior en la colección y retrocede el iterador.
     * 
     */
}
