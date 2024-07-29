package View;

import Controller.PPLController;
import Controller.Delito;
import Controller.PPL;
import Controller.PPLGravedadAlta;
import Controller.PPLGravedadBaja;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Carlos & Cody
 */
public class Ejecucion {

    public static void main(String[] args) {

        LocalDateTime fechaActual = LocalDateTime.now(); //Permite obtener la fecha y hora actual para diferentes propósitos
        PPLController controlador = new PPLController(); //Puente para acceder a la base de datos del programa.

        Scanner sc = new Scanner(System.in);
        ArrayList<PPLGravedadAlta> listaPPLAlta = controlador.leerPPLGravedadAlta();
        ArrayList<PPLGravedadBaja> listaPPLBaja = controlador.leerPPLGravedadBaja();

        ArrayList<PPL> listaPPL = new ArrayList<>();
        listaPPL.addAll(listaPPLAlta);
        listaPPL.addAll(listaPPLBaja);

        System.out.println("---Bienvenido al sistema de control de personas privadas de libertad (PPL)---");
        int opcionPrincipal;

        while (true) {

            System.out.print(controlador.mostrarMenu());

            try {
                opcionPrincipal = sc.nextInt();
                sc.nextLine();

                switch (opcionPrincipal) {
                    case 1:
                        mostrarListaPPL(listaPPL);
                        break;
                    case 2:
                        agregarNuevoPPL(controlador, listaPPL, fechaActual, sc);
                        break;
                    case 3:
                        eliminarPPL(controlador, listaPPL, sc);
                        break;
                    case 4:
                        buscarPPL(controlador, listaPPL, sc);
                        break;
                    case 5:
                        modificarDelitoPPL(controlador, listaPPL, sc);
                        break;
                    case 6:
                        aplicarCastigoPPL(controlador, listaPPL, sc);
                        break;
                    case 7:
                        generarTXT(controlador, listaPPL);
                        break;
                    case 8:
                        System.out.println("Saliendo del sistema...");
                        System.exit(0);
                    default:
                        System.out.println("Ingrese una opcion válida");
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Error de sintaxis. Ingrese opciones correctas");
                sc.nextLine(); //Limpiar el input inválido

            }

        }
    }

    /*
    ESTE METODO MOSTRARÁ SI UN PPL PERTENECE A LA CATEGORÍA GRAVE O LEVE. 
    FUNCIONA TAMBIÉN PARA PPL'S GENERALES QUE NO TIENEN ASIGNADA UNA CATEGORÍA ESPECÍFICA
     */
    private static void mostrarListaPPL(ArrayList<PPL> listaPPL) {

        if (listaPPL.isEmpty()) {
            System.out.println("NO EXISTEN PPL REGISTRADOS");
            return;
        }

        int contador = 1;

        for (PPL ppl : listaPPL) {

            if (ppl instanceof PPLGravedadAlta) {
                System.out.println("[" + contador + "] -> " + (PPLGravedadAlta) ppl + "\n");
            } else if (ppl instanceof PPLGravedadBaja) {
                System.out.println("[" + contador + "] -> " + (PPLGravedadBaja) ppl + "\n");
            } else {
                System.out.println("[" + contador + "] -> " + ppl + "\n");
            }

            contador++;
        }

    }

    private static void agregarNuevoPPL(PPLController controlador, ArrayList<PPL> listaPPL, LocalDateTime fechaActual, Scanner sc) {
        try {
            System.out.print("Ingrese nombre del PPL: ");
            String nombre = sc.nextLine();

            System.out.println("Ingrese el tipo de PPL: ");
            System.out.println("1. PRESO LIBERTAD CATEGORÍA ALTA\t2. PRESO LIBERTAD CATEGORÍA LEVE");
            int tipoPPL = validarTipoPPL(sc);

            
            System.out.print("Ingrese el numero de delitos cometidos por el PPL " + nombre + ": ");
            int numeroDelitos = sc.nextInt();
            sc.nextLine();

            ArrayList<Delito> delitos = new ArrayList<>(); //Aquí se almacenarán los delitos del PPL que se está escribiendo

            int contadorDelitos = 1; //Variable para imprimir la iteración actual. Solo para estética del programa

            //El bucle se ejecuta las veces que se ingresaron como el numero de delitos. 
            while (numeroDelitos > 0) {
                System.out.print("Ingrese el delito " + contadorDelitos + " cometido: ");
                String delito = sc.nextLine();

                System.out.print("Ingrese la gravedad del delito (Alta | Media | Baja): ");

                String gravedad = validarGravedad(sc);

                delitos.add(new Delito(delito, gravedad));

                numeroDelitos--;

                contadorDelitos++;

            }

            PPL nuevoPPL = new PPL(nombre, delitos, fechaActual);
            listaPPL.add(nuevoPPL);
            controlador.escribirPPL(listaPPL); //Actualizar base de datos
            System.out.println("PPL AGREGADO");

        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido
        }
    }

    private static void eliminarPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {

        try {
            System.out.print("Escriba el nombre del PPL a eliminar: ");
            String nombrePPL = sc.nextLine();

            ArrayList<PPL> presos = controlador.buscador(listaPPL).getPresosPorNombre(nombrePPL);

            if (!presos.isEmpty()) {

                PPL pplModificar = presos.get(0);

                if (presos.size() != 1) { //En caso de existir 2 con el mismo nombre, seleccionar el necesario
                    pplModificar = seleccionarPPL(presos, sc);
                }

                listaPPL.remove(pplModificar);
                System.out.println("Preso eliminado de la base de datos");
                controlador.escribirPPL(listaPPL); //Actualizar base de datos.
            } else {
                System.out.println("EL PRESO NO EXISTE");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido
        }
    }

    private static void buscarPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
        int opcion;

        try {
            do {
                System.out.println("-------------------------------------------");
                System.out.println("[1]. Buscar lista de PPL por numero de delitos");
                System.out.println("[2]. Buscar lista de PPL por tipo de gravedad");
                System.out.println("[3]. Buscar lista de PPL por delito");
                System.out.println("[4]. Buscar lista de PPL por dias de visita permitidos");
                System.out.println("[5]. Buscar lista de PPL que superen x días de condena");
                System.out.println("[6]. Buscar PPL por nombre");
                System.out.println("[7]. Imprimir numero de delitos totales cometidos por los PPL");
                System.out.println("[0]. Salir ");
                System.out.println("--------------------------------------------------");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> { //PPLS por numero de delitos
                        System.out.print("Ingrese el numero de delitos: ");
                        int delitos = sc.nextInt();
                        sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorNumeroDelito(delitos));
                    }
                    case 2 -> { //PPLS por tipo de gravedad
                        System.out.print("Ingrese la gravedad del delito a buscar: (Alta | Media | Baja): ");
                        String gravedad = validarGravedad(sc);
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorGravedad(gravedad));
                    }
                    case 3 -> { //PPLS por x delito
                        System.out.print("Ingrese el delito a buscar: ");
                        String delito = sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorDelito(delito));
                    }

                    case 4 -> { //PPLS por día de visita semanales permitidos
                        System.out.print("Ingrese el numero de dias de visita permitido: ");
                        int dias = sc.nextInt();
                        sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorNumeroVisitas(dias));
                    }

                    case 5 -> { //PPLS por x días de condena en adelante
                        System.out.print("Ingrese numero de dias de condena mínima: ");
                        int diasCodena = sc.nextInt();
                        sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorDiasCondena(diasCodena));
                    }

                    case 6 -> { //PPL específico por nombre. 
                        System.out.print("Ingrese el nombre del PPL: ");
                        String nombre = sc.nextLine();
                        ArrayList<PPL> pplEncontrados = (controlador.buscador(listaPPL).getPresosPorNombre(nombre));
                        mostrarListaPPL(pplEncontrados);

                    }
                    case 7 -> { //Contador de delitos totales.
                        int delitosTotales = controlador.buscador(listaPPL).getDelitosTotales();
                        System.out.println("Delitos totales para los PPL actuales: " + delitosTotales);
                    }

                }

            } while (opcion != 0);
        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido

        }
    }

    private static void modificarDelitoPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
        try {
            System.out.print("Ingrese nombre del PPL a modificar: ");
            ArrayList<PPL> pplEncontrados = controlador.buscador(listaPPL).getPresosPorNombre(sc.nextLine());

            if (!pplEncontrados.isEmpty()) {

                PPL pplModificar = pplEncontrados.get(0);

                if (pplEncontrados.size() != 1) { //En caso de existir 2 con el mismo nombre, seleccionar el necesario
                    pplModificar = seleccionarPPL(pplEncontrados, sc);
                }

                System.out.println(pplModificar);
                System.out.println("1. Agregar delito");
                System.out.print("2. Eliminar delito: ");
                int opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 1) {
                    System.out.print("Ingrese nombre del delito: ");
                    String delito = sc.nextLine();
                    System.out.print("Ingrese gravedad del delito (Alta | Media | Baja): ");
                    String gravedad = validarGravedad(sc);

                    pplModificar.agregarDelito(new Delito(delito, gravedad));
                    controlador.escribirPPL(listaPPL); //Actualizar la database ya que se modificaron datos
                    System.out.println("Delito agregado al PPL: " + pplModificar.getNombre());
                    return;
                }

                if (opcion == 2) {
                    System.out.print("Ingrese nombre del delito a eliminar: ");
                    String delito = sc.nextLine().trim();

                    System.out.println(pplModificar.eliminarDelito(delito));
                    controlador.escribirPPL(listaPPL); //Actualizar la database ya que se modificaron datos

                    return;
                }

            }
            System.out.println("El PPL no existe en la base de datos");
        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido
        }

    }

    private static void aplicarCastigoPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {

        try {
            System.out.print("Ingrese nombre del PPL a aplicar castigo: ");
            ArrayList<PPL> pplEncontrados = controlador.buscador(listaPPL).getPresosPorNombre(sc.nextLine());

            if (!pplEncontrados.isEmpty()) {

                PPL pplModificar = pplEncontrados.get(0);

                if (pplEncontrados.size() != 1) { //En caso de existir 2 con el mismo nombre, seleccionar el necesario
                    pplModificar = seleccionarPPL(pplEncontrados, sc);
                }

                System.out.println(pplModificar);
                System.out.println("1. Aplicar castigo BAJO");
                System.out.println("2. Aplicar castigo MEDIO");
                System.out.println("3. Aplicar castigo ALTO");
                int opcion = sc.nextInt();
                sc.nextLine();

                while (opcion != 1 && opcion != 2 && opcion != 3) { //Forzar al usuario a elegir un castigo válido
                    System.out.print("Ingrese un castigo válido: ");
                    opcion = sc.nextInt();
                    sc.nextLine();
                }
                System.out.println(pplModificar.aplicarCastigo(opcion));
                controlador.escribirPPL(listaPPL); //Actualizar la lista PPL
                return;
            }

            System.out.println("El PPL no existe en la base de datos");
        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido
        }

    }

    private static void generarTXT(PPLController controlador, ArrayList<PPL> listaPPL) {

        if (controlador.escribirTXT(listaPPL)) {
            System.out.println("Archivo escrito correctamente ");
            return;
        }
        System.out.println("Error al escribir archivo txt ");
    }

    private static String validarGravedad(Scanner sc) {

        String gravedad = sc.nextLine().trim();

        while (!gravedad.equalsIgnoreCase("ALTA")
                && !gravedad.equalsIgnoreCase("MEDIA")
                && !gravedad.equalsIgnoreCase("BAJA")) {
            System.out.print("Ingrese una gravedad correcta: (Alta | Media | Baja): ");
            gravedad = sc.nextLine().trim();

        }

        return gravedad;
    }

    private static int validarTipoPPL(Scanner sc) {
        int tipoPPL = sc.nextInt();
        sc.nextLine();

        while (tipoPPL != 1 && tipoPPL != 2) {
            System.out.println("Ingrese un tipo de PPL correcto");
            tipoPPL = sc.nextInt();
            sc.nextLine();
        }
        return tipoPPL;
    }

    //Permite seleccionar un PPL especifico dada una lista de PPL.
    private static PPL seleccionarPPL(ArrayList<PPL> ppl, Scanner sc) {

        mostrarListaPPL(ppl);
        System.out.print("Escriba el numero de PPL a seleccionar: ");
        int opcion = sc.nextInt();

        //Si la opcion está fuera del rango del arreglo seguir pidiendo hasta que el input sea válido
        //Se trabaja con valores de 1 hasta arreglo.length() para comodidad del usuario
        //Al momento de retornar, ya se traduce el valor introducido como un valor de índice para el arreglo (opcion - 1)
        while (opcion <= 0 || opcion > ppl.size()) {
            System.out.print("Eliga una opción disponible dentro del rango de los PPL: ");
            opcion = sc.nextInt();

        }

        return ppl.get(opcion - 1);
    }

}
