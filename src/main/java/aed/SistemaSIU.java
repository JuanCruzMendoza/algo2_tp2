package aed;

public class SistemaSIU {

    DiccionarioTrie<Integer> estudiantes;
    DiccionarioTrie<DiccionarioTrie<Materia>> carreras;

    // Referencia para complejidades:
    // |M|: cantidad de materias
    // |m|: longitud del nombre de una materia
    // |C|: cantidad de carreras
    // |c|: longitud del nombre de una carrera
    // |M_c|: cantidad de materias de una carrera c
    // |N_m|: cantidad de nombres de una materia m
    // |n|: longitud de uno de los nombres de una materia
    // E: cantidad total de estudiantes
    // E_m: cantidad de estudiantes de una materia m
    // |estudiante|: longitud de libreta universitaria (acotada)
    
    // DiccionarioTrie, al estar implementado con un Trie, tiene las siguientes complejidades:
    // DiccionarioTrie(): O(1)
    // estaVacio(): O(1)
    // insertar(): O(|clave|)
    // buscar(): O(|clave|)
    // pertenece(): O(|clave|)
    // eliminar(): O(|clave|)

    // ListaEnlazada:
    // ListaEnlazada(): O(1)
    // longitud(): O(1)
    // agregarAtras(): O(1)
    // obtener(): O(n)
    // eliminar(): O(n)
    // modificarPosicion(): O(n)
    // ListaIterador(), haySiguiente(), siguiente(): O(1)

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

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

            Materia nueva_materia = new Materia(); 

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


    public void agregarDocente(CargoDocente cargo, String carrera, String materia){

        // O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);

        // El array de docentes es: [PROF, JTP, AY1, AY2]
        // O(1)
        materia_obj.docentes[3-cargo.ordinal()]++;

    } // Complejidad agregarDocente(): O(1) + O(|c|) + O(|m|) = O(|c| + |m|)


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
        IteradorLista<String> iterador_estudiantes = materia_obj.inscriptos.iterador();
        for (int i = 0; i < materia_obj.inscriptos.longitud(); i++){

            // Recorremos el iterador, tomando los estudiantes: O(1)
            String estudiante = iterador_estudiantes.siguiente();
            
            // Buscamos la cantidad de materias que tiene y restamos 1: O(|estudiante) + O(|estudiante|) = O(1)
            int cant_materias = this.estudiantes.buscar(estudiante);
            this.estudiantes.insertar(estudiante, cant_materias-1);
        }

        // Para cada carrera, eliminamos la materia (teniendo en cuenta que tiene diferentes nombres en cada una)
        // Se ejecuta |N_m| veces
        IteradorLista<DiccionarioTrie<Materia>> iterador_carreras = materia_obj.carreras_comunes.iterador();
        IteradorLista<String> iterador_nombres_materias = materia_obj.nombres.iterador();
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

    } // Complejidad cerrarMateria(): O(|c|) + O(|m|) + O(|N_m| * |n|) = O(|c| + |m| + |N_m| * |n|)

    
    public int inscriptos(String materia, String carrera){

        // Encontramos la materia: O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);
        
        // Devolvemos la longitud de la lista de inscriptos: O(1)
        return materia_obj.inscriptos.longitud();

    } // Complejidad inscriptos(): O(|c| + |m|)


    public boolean excedeCupo(String materia, String carrera){

        // Encontramos la materia: O(|c|) + O(|m|)
        Materia materia_obj = this.carreras.buscar(carrera).buscar(materia);

        // Tomamos el array de docentes y la longitud de la lista de inscriptos: O(1)
        int[] docentes = materia_obj.docentes;
        int cant_estudiantes = materia_obj.inscriptos.longitud();

        // Para que no exceda el cupo, se deben cumplir las siguientes 4 condiciones:
        boolean excede_cupo = 
            docentes[3] * 30 < cant_estudiantes // condicion para AY2
            || docentes[2] * 20 < cant_estudiantes // condicion para AY1
            || docentes[1] * 100 < cant_estudiantes // condicion para JTP
            || docentes[0] * 250 < cant_estudiantes; // condicion para PROF

        return excede_cupo;

    } // Complejidad excedeCupo(): O(|c| + |m|)


    public String[] carreras(){
        ListaEnlazada<String> lista_carreras = this.carreras.obtenerClaves();
        IteradorLista<String> iterador_carreras = lista_carreras.iterador();
        
        String[] carreras_arr = new String[lista_carreras.longitud()];

        for (int i = 0; i < lista_carreras.longitud(); i++){
            carreras_arr[i] = iterador_carreras.siguiente();
        }

        return carreras_arr;
    }

    public String[] materias(String carrera){

        ListaEnlazada<String> lista_materias = this.carreras.buscar(carrera).obtenerClaves();	  
        IteradorLista<String> iterador_lista = lista_materias.iterador();
        
        // Creamos el array de materias con la longitud de la lista de materias
        String[] materias_arr = new String[lista_materias.longitud()];

        for (int i = 0; i < lista_materias.longitud(); i++){
            materias_arr[i] = iterador_lista.siguiente();
        }

        return materias_arr;
    }


    public int materiasInscriptas(String estudiante){

        // Encuentra la cantidad de materias inscriptas en diccionario: O(|estudiante|) = O(1)
        return estudiantes.buscar(estudiante);	   

    } // Complejidad materiasInscriptas(): O(1)
}
