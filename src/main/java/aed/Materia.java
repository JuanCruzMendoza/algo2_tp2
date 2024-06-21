package aed;

public class Materia {

    // Atributos de Materia
    public int[] docentes;
    public ListaEnlazada< String > nombres;
    public ListaEnlazada< DiccionarioTrie<Materia> > carreras_comunes;
    public ListaEnlazada<String> inscriptos;

    // Invariante de representación:

    // - Existe una única instancia de InfoMateria asociada a cada instancia de Materia con la cual es construída.

    // - Lista de Strings nombres: son los diferentes nombres que pueda tener la materia para cada carrera.
    // Su longitud será la misma que InfoMateria y además nombres[i] = InfoMateria.ParCarreraMateria[i].nombreMateria para todo i en rango.
    // Dado que dos carreras pueden tener el mismo nombre para la misma materia, la lista nombres puede tener repetidos.
    // Si dos instancias de Materia tienen los mismos elementos en nombres, son iguales.

    // - Lista de Diccionarios carreras_comunes: cada diccionario de la lista referencia a un diccionario de SistemaSIU.carreras 
    // (que es a su vez el valor de una clave carrera), pero sólo aquellos cuya clave asociada se encuentra en InfoMateria, de manera que: 
    // para todo i en rango, InfoMateria.ParCarreraMateria[i].carrera = clave de carreras_comunes[i] en diccionario SistemaSIU.carreras

    // - Relación carreras_comunes - nombres: cada nombre de la lista nombres en la posición i corresponde al nombre de la materia
    // en el diccionario de materias carreras_comunes[i], y nos lleva a la misma instancia de Materia, es decir:
    // nombres.longitud() = carreras_comunes.longitud(), 
    // para todo i en rango: nombres[i] es clave de carreras_comunes[i] && carreras_comunes[i].obtener(nombres[i]) = this.Materia

    // - Array de enteros docentes: cuenta con 4 posiciones donde cada una representa la cantidad de docentes (entero mayor o igual a cero) 
    // en la materia según su cargo, respetando la siguiente estructura: [PROF, JTP, AY1, AY2]

    // Lista de Strings inscriptos: cuenta con las libretas univeritarias de cada estudiante inscripto a la instancia Materia correspondiente,
    // y como no hay dos LU iguales, cada lista de inscriptos no tiene repetidos.

    // Constructor de Materia
    public Materia(){
        
        // Los distintos nombres que tiene la materia para diferentes carreras
        this.nombres = new ListaEnlazada<>();

        // Creamos la lista donde vamos a almacenar los diccionarios de las carreras donde se encuentra la materia
        this.carreras_comunes = new ListaEnlazada<>();

        // Al principio, no hay inscriptos en la materia
        inscriptos = new ListaEnlazada<>();

        // Inicializamos docentes con un Array de 4 posiciones, y al principio hay 0 en cada cargo
        docentes = new int[4];
        for (int i= 0; i< 4; i++){
          docentes[i] = 0;
        }

    }
}
