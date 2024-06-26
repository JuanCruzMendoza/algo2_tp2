package aed;

public class SistemaSIU {

    // Atributos del Sistema
    DiccionarioTrie<Integer> estudiantes;
    DiccionarioTrie<DiccionarioTrie<Materia>> carreras;

    // Invariante de representacion:

    // - SistemaSIU se construye con un único Array de InfoMateria, junto con un Array de Strings libretasUniversitarias

    // - Diccionario carreras: cada clave es un nombre de una carrera (String) y su valor es otro diccionario, cuyas claves a su vez 
    // son los nombres de las diferentes materias (String) que tiene la carrera y están asociadas a instancias de la clase Materia. 
    // Cada instancia de Materia está asociada a una única materia sin importar los distintos nombres que pueda tener para cada carrera.
    // De esta manera, si dos carreras cuentan con la misma materia, pero con diferentes nombres, con este diccionario podemos llegar 
    // a la misma instancia Materia, la cual almacena la información de sus inscriptos y sus docentes (ver Materia.java para más detalles).
    
    // - Relación SistemaSIU.carreras - InfoMateria[]: 
    // El conjunto de nombres distintos de carreras pertenecientes a los pares carrera - materia de cada instancia de InfoMateria 
    // son las claves de SistemaSIU.carreras. 
    // Además, dado un ParCarreraMateria p de cualquier instancia de InfoMateria, se cumple que p.nombreMateria es clave del diccionario 
    // carreras.obtener(p.carrera).
    // A cada instancia distinta de Materia dentro de los diccionarios de carreras le corresponde una instancia de InfoMateria 
    // con la cual es construída.

    // - Diccionario estudiantes: cada clave es una libreta universitaria (String) de un estudiante, cuyo valor asociado 
    // es la cantidad de materias (entero mayor o igual a cero) a las que está inscripto dicho estudiante.
    // Por otro lado, dada la lista libretasUniversitarias, cada elemento de la misma es clave del diccionario estudiantes.

    // - Relación SistemaSIU.estudiantes - Materia.inscriptos (lista de LU): dado n = estudiantes[LU] cantidad de materias, 
    // habrá n instancias de Materia (dentro de SistemaSIU.carreras) donde aparecerá LU como elemento de Materia.inscriptos 
    // (sólo una vez en cada lista).

    // - Relación SistemaSIU.carreras - Materia.carreras_comunes - Materia.nombres: 
    // Dada una instancia Materia, cada diccionario de Materia.carreras_comunes referencia a algún diccionario de SistemaSIU.carreras.
    // Para un i en rango y una carrera c, si SistemaSIU.carreras.obtener(c) = Materia.carreras_comunes[i] (referencian al mismo diccionario)
    // entonces SistemaSIU.carreras.obtener(c).obtener(Materia.nombres[i]) = Materia (podemos llegar a la misma instancia Materia).
    // La cantidad de elementos de Materia.carreras_comunes (y de Materia.nombres) de cada instancia de Materia es igual a la cantidad 
    // de claves de cada diccionario de SistemaSIU.carreras (es decir, la cantidad de nombres de cada materia por el número total de 
    // materias será igual a la cantidad de materias de cada carrera por el número total de carreras).


    // - Referencia para complejidades:

    // |M|: cantidad de materias
    // |m|: longitud del nombre de una materia m
    // |C|: cantidad de carreras
    // |c|: longitud del nombre de una carrera
    // |M_c|: cantidad de materias de una carrera c
    // |m_c|: longitud del nombre de una materia m de una carrera c
    // |N_m|: cantidad de nombres de una materia m
    // |n|: longitud de uno de los nombres n de una materia
    // E: cantidad total de estudiantes
    // E_m: cantidad de estudiantes de una materia m
    // |estudiante|: longitud de libreta universitaria (acotada)
    
    // - Complejidades de DiccionarioTrie:
    // DiccionarioTrie(): O(1)
    // estaVacio(): O(1)
    // insertar(): O(|clave|)
    // buscar(): O(|clave|)
    // pertenece(): O(|clave|)
    // eliminar(): O(|clave|)
    // obtenerClaves(): O(n*|clave|)

    // - Complejidades de ListaEnlazada:
    // ListaEnlazada(): O(1)
    // longitud(): O(1)
    // agregarAtras(): O(1)
    // obtener(): O(n)
    // eliminar(): O(n)
    // modificarPosicion(): O(n)
    // iterador(), haySiguiente(), siguiente(): O(1)

    
    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    
    // Método para crear sistema, con la informacion de las materias de cada carrera y los estudiantes
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){

        estudiantes = new DiccionarioTrie<>();
        carreras = new DiccionarioTrie<>();

        // Se ejecuta E veces (= longitud de libretasUniversitarias)
        for (String estudiante: libretasUniversitarias){

            // O(|estudiante|), pero al estar acotada |estudiante|, es O(1)
            estudiantes.insertar(estudiante, 0); // en principio, ningun alumno esta inscripto a ninguna materia

        } // E * O(1) = O(E)

        // Se ejecuta |M| veces
        for(InfoMateria info: infoMaterias){

            // O(1)
            Materia nueva_materia = new Materia(); 

            // O(1)
            ParCarreraMateria[] pares_carrera_materia = info.getParesCarreraMateria();

            // Se ejecuta |N_m| veces
            for (ParCarreraMateria carrera_materia: pares_carrera_materia){

                // O(1)
                String nombre_materia = carrera_materia.getNombreMateria();
                nueva_materia.nombres.agregarAtras(nombre_materia);

                // O(1)
                String nombre_carrera = carrera_materia.getCarrera();

                // O(|c|)
                if (this.carreras.pertenece(nombre_carrera)){

                    // O(|c|) + O(|n|)
                    this.carreras.buscar(nombre_carrera).insertar(nombre_materia, nueva_materia);

                } else {

                    DiccionarioTrie<Materia> materias_de_carrera = new DiccionarioTrie<>();

                    // O(|c|) + O(|n|)
                    materias_de_carrera.insertar(nombre_materia, nueva_materia);
                    this.carreras.insertar(nombre_carrera, materias_de_carrera);
                }

                // O(1) + O(|c|) = O(|c|)
                nueva_materia.carreras_comunes.agregarAtras(carreras.buscar(nombre_carrera));

            }

        } // |M| * |N_m| * ( O(1) + O(1) + O(|c|) + O(|c|) + O(|c|) + O(|n|) ) =
        // O( |M| * |N_m| * ( |c| + |n| ) ) =
        // O( |M| * |N_m| * |c| + |M| * |N_m| * |n|)
        // Notemos que |C| * |M_c| = |M| * |N_m|, es decir, la sumatoria de la cantidad de materias de cada carrera 
        // es igual a la sumatoria de la cantidad de nombres de cada materia (dado que cada nombre corresponde a una carrera diferente)
        // Entonces O( |M| * |N_m| * |c| + |M| * |N_m| * |n|) = O( |C| * |M_c| * |c| + |M| * |N_m| * |n| )

    } // Complejidad SistemaSIU():  O( |C| * |M_c| * |c| + |M| * |N_m| * |n| + E)


    // Método para inscribir a un estudiante en una materia, dada también la carrera
    public void inscribir(String estudiante, String carrera, String materia){

        // O(|estudiante|) = O(1)
        int valor_anterior = estudiantes.buscar(estudiante);

        // O(|estudiante|) = O(1)
        estudiantes.insertar(estudiante, valor_anterior+1);

        // O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);

        // O(1)
        materia_obj.inscriptos.agregarAtras(estudiante);

    } // Complejidad inscribir(): O(1) + O(1) + O(|c|) + O(|m|) + O(1) = O(|c| + |m|)


    // Método para agergar un docente a una materia, dada también la carrera
    public void agregarDocente(CargoDocente cargo, String carrera, String materia){

        // O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);

        // El array de docentes es: [PROF, JTP, AY1, AY2]
        // O(1)
        materia_obj.docentes[3-cargo.ordinal()]++;

    } // Complejidad agregarDocente(): O(1) + O(|c|) + O(|m|) = O(|c| + |m|)


    // Método para devolver un Array con 4 posiciones, donde cada una corresponde a la cantidad de docentes: [PROF, JTP, AY1, AY2]
    public int[] plantelDocente(String materia, String carrera){

        // O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);
        
        return materia_obj.docentes;

    } // Complejidad plantelDocente(): O(|c| + |m|)


    public void cerrarMateria(String materia, String carrera){

        // Encontramos la materia a cerrar: O(|c|) + O(|m|)	    
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);

        // A cada estudiante inscripto, restamos 1 a su cantidad de materias inscriptas
        // Se ejecuta E_m veces
        ListaEnlazada<String>.IteradorLista iterador_estudiantes = materia_obj.inscriptos.iterador();
        for (int i = 0; i < materia_obj.inscriptos.longitud(); i++){

            // Recorremos el iterador, tomando los estudiantes: O(1)
            String estudiante = iterador_estudiantes.siguiente();
            
            // Buscamos la cantidad de materias que tiene y restamos 1: O(|estudiante) + O(|estudiante|) = O(1)
            int cant_materias = this.estudiantes.buscar(estudiante);
            this.estudiantes.insertar(estudiante, cant_materias-1);

        } // E_m * O(1) = O(E_m)

        // Para cada carrera, eliminamos la materia (teniendo en cuenta que tiene diferentes nombres en cada una)
        // Se ejecuta |N_m| veces
        ListaEnlazada<DiccionarioTrie<Materia>>.IteradorLista iterador_carreras = materia_obj.carreras_comunes.iterador();
        ListaEnlazada<String>.IteradorLista iterador_nombres_materias = materia_obj.nombres.iterador();
        for (int i = 0; i < materia_obj.carreras_comunes.longitud(); i++){

            // Recorremos las carreras donde se encuentra la materia, al mismo tiempo que recorremos los distintos nombres que tienen
            // (notar que por como fue construido, el nombre de la materia en la posicion i de materia_obj.nombres se encuentra en la carrera 
            // en la posicion i de materia_obj.carreras_comunes)
            // O(1) + O(1) = O(1)
            DiccionarioTrie<Materia> carrera_dicc = iterador_carreras.siguiente();
            String nombre_materia = iterador_nombres_materias.siguiente();

            // Borramos uno de los nombres de la materia en una carrera: O(|n|)
            carrera_dicc.eliminar(nombre_materia);

        } // |N_m| * O(1) * O(|n|)  = O(|N_m| * |n|)

    } // Complejidad cerrarMateria(): O(|c|) + O(|m|) + O(E_m) + O(|N_m| * |n|) = O(|c| + |m| + |N_m| * |n| + E_m)


    // Método que devuelve la cantidad de alumnos inscriptos en una materia, dada también una carrera
    public int inscriptos(String materia, String carrera){

        // Encontramos la materia: O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);
        
        // Devolvemos la longitud de la lista de inscriptos: O(1)
        return materia_obj.inscriptos.longitud();

    } // Complejidad inscriptos(): O(|c| + |m|)


    // Método para determinar si la cantidad de estudiantes excede la capacidad de la materia, dada las siguientes condiciones:
    // Debe haber a lo sumo 250 estudiantes por cada profesor, 100 por cada JTP, 20 por cada AY1 y 30 por cada AY2
    public boolean excedeCupo(String materia, String carrera){

        // Encontramos la materia: O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);

        // Tomamos el array de docentes y la longitud de la lista de inscriptos: O(1)
        int[] docentes = materia_obj.docentes;
        int cant_estudiantes = materia_obj.inscriptos.longitud();

        // Si excede el cupo, significa que se cumple algunas de las siguientes condiciones:
        // O(1): comparaciones
        boolean excede_cupo = 
            docentes[3] * 30 < cant_estudiantes // condicion para AY2
            || docentes[2] * 20 < cant_estudiantes // condicion para AY1
            || docentes[1] * 100 < cant_estudiantes // condicion para JTP
            || docentes[0] * 250 < cant_estudiantes; // condicion para PROF

        return excede_cupo;

    } // Complejidad excedeCupo(): O(|c| + |m|)


    // Método para devolver un Array con todas las carreras del sistema
    public String[] carreras(){

        // Obtenemos todas las claves del diccionario carreras en una lista: O(|C| * |c|)
        // Por como está implementado obtenerClaves(), las Strings estan ordenadas lexicográficamente
        ListaEnlazada<String> lista_carreras = this.carreras.obtenerClaves();

        // Para pasar de una ListaEnlazada a un Array, armamos un iterador para recorrer lista_carreras
        // y guardar los valores en un nuevo Array carreras_arr: O(1)
        ListaEnlazada<String>.IteradorLista iterador_carreras = lista_carreras.iterador();
        String[] carreras_arr = new String[lista_carreras.longitud()];

        // Recorremos lista_carreras: se ejecuta |C| veces
        for (int i = 0; i < lista_carreras.longitud(); i++){

            // O(1)
            carreras_arr[i] = iterador_carreras.siguiente();

        } // |C| * O(1) = O(|C|)

        return carreras_arr;

    } // Complejidad carreras(): O(|C|*|c|) + O(1) + O(|C|) = (con |c|>0) O(|C|*|c|)


    // Método para devolver un Array con todas las materias de una carrera
    public String[] materias(String carrera){

        // Buscamos el diccionario de las materias de la carrera y obtenemos sus claves en una lista: O(|c| + |M_c| * |m_c|)
        // Por como esta implementado obtenerClaves(), las Strings están ordenadas lexicográficamente
        ListaEnlazada<String> lista_materias = this.carreras.buscar(carrera).obtenerClaves();	  

        // Para pasar de una ListaEnlazada a un Array, armamos un iterador para recorrer lista_carreras
        // y guardar los valores en un nuevo Array materias_arr: O(1)
        ListaEnlazada<String>.IteradorLista iterador_lista = lista_materias.iterador();
        String[] materias_arr = new String[lista_materias.longitud()]; 

        // Recorremos lista_materias: se ejecuta |M_c| veces
        for (int i = 0; i < lista_materias.longitud(); i++){

            // O(1)
            materias_arr[i] = iterador_lista.siguiente(); 

        } // |M_c| * O(1) = O(|M_c)

        return materias_arr;

    } // Complejidad materias(): O(|c| + |M_c| * |m_c|) + O(1) + O(|M_c|) = (con |m_c|>0) O(|c| + |M_c| * |m_c|)


    // Método para devolver la cantidad de materias inscriptas de un estudiante
    public int materiasInscriptas(String estudiante){

        // Encuentra la cantidad de materias inscriptas en diccionario: O(|estudiante|) = O(1)
        return estudiantes.buscar(estudiante);	   

    } // Complejidad materiasInscriptas(): O(1)
}
