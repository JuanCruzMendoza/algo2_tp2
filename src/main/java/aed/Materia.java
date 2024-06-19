package aed;


// Invariante de representacion:
// Todos los elemntos del conjunto implementado en la lista enlazada "inscriptos" son claves del
// Diccionariotrie "Estudiantes" del modulo Siu.

// Todos las claves de los DiccionariosTries que estàn dentro del conjunto listaEnlazada "carrerras_comunes"  son claves del
// DiccionarioTrie que es el valor de diccionariosTrie carreras.

// Todos los elemntos del conjunto implementado en la lista enlazada "nombres" son claves del
// Diccionariotrie que a su vez es valor del DiccionarioTrie "carrera" del modulo Siu.


public class Materia {

    public int[] docentes;
    public ListaEnlazada< String > nombres;
    public ListaEnlazada< DiccionarioTrie<Materia> > carreras_comunes;
    public ListaEnlazada<String> inscriptos;

    public Materia(){
        
        // Los distintos nombres que tiene la materia para diferentes carreras
        this.nombres = new ListaEnlazada<>();

        // Creamos la lista donde vamos a almacenar los diccionarios de las carreras donde se encuentra la materia
        this.carreras_comunes = new ListaEnlazada<>();

        // Al principio, no hay inscriptos en la materia
        inscriptos = new ListaEnlazada<>();

        // Inicializamos docentes con un Array de 4 posiciones, y al principio hay 0 en cada cargo
        // Sigue la siguiente estructura: [PROF, JTP, AY1, AY2]
        docentes = new int[4];
        for (int i= 0; i< 4; i++){
          docentes[i] = 0;
        }

    }
}
