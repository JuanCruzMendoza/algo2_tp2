package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DiccionarioTrieTests {

    @Test
    void nuevoDiccionarioVacio(){
        DiccionarioTrie<String> dicc = new DiccionarioTrie<String>();
        assertEquals(true, dicc.estaVacio());

    }

    @Test 
    void insertar_pertenece_eliminar(){
        DiccionarioTrie<String> dicc = new DiccionarioTrie<>();
        dicc.insertar("manzana", "Una fruta");
        dicc.insertar("man", "Abreviatura de hombre");
        dicc.insertar("mango", "Otra fruta");

        assertEquals(true, dicc.pertenece("manzana"));
        assertEquals(true, dicc.pertenece("man"));
        assertEquals(true, dicc.pertenece("mango"));
        assertEquals(false, dicc.pertenece("maniobra"));

        assertEquals("Una fruta", dicc.buscar("manzana"));
        assertEquals("Abreviatura de hombre", dicc.buscar("man"));
        assertEquals("Otra fruta", dicc.buscar("mango"));

        dicc.eliminar("manzana");
        dicc.eliminar("mango");

        assertEquals(false, dicc.pertenece("manzana"));
        assertEquals(false, dicc.pertenece("mango"));
        assertEquals(true, dicc.pertenece("man"));

        dicc.eliminar("man");
        dicc.estaVacio();
    }

}
