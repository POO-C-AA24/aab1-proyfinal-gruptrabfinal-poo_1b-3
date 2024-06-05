package View;

import Controller.PPLController;
import Controller.Delito;
import Controller.PPL;
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
        LocalDateTime fechaActual = LocalDateTime.now();
        PPLController controlador = new PPLController();

        Scanner sc = new Scanner(System.in);
        ArrayList<PPL> listaPPL = controlador.leerPPL();

        System.out.println("---Bienvenido al sistema de control de personas privadas de libertad (PPL)---");
        int opcionPrincipal;

        while (true) {

            System.out.println("-----------------------");
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
            } catch (Exception e) {
                System.out.println("Error de sintaxis. Ingrese opciones correctas");
                sc.nextLine(); //Limpiar el input inválido

            }

          

        }
    }

    private static void mostrarListaPPL(ArrayList<PPL> listaPPL) {

        if (listaPPL.isEmpty()) {
            System.out.println("NO EXISTEN PPL REGISTRADOS");
            return;
        }

        for (PPL ppl : listaPPL) {
            System.out.println(ppl);
        }

    }

    private static void agregarNuevoPPL(PPLController controlador, ArrayList<PPL> listaPPL, LocalDateTime fechaActual, Scanner sc) {
        try {
            System.out.print("Ingrese nombre del PPL: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese el numero de delitos cometidos por el PPL " + nombre + ": ");
            int numeroDelitos = sc.nextInt();
            sc.nextLine();

            ArrayList<Delito> delitos = new ArrayList<>();

            int contadorDelitos = 1; //Variable para imprimir la iteración actual. Solo para estética del programa
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
            controlador.escribirPPL(listaPPL);
            System.out.println("PPL AGREGADO");
        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido
        }
    }

    private static void eliminarPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {

        try {
            System.out.println("Escriba el nombre del PPL a eliminar: ");
            String nombrePPL = sc.nextLine();

            PPL preso = controlador.buscador(listaPPL).getPresoPorNombre(nombrePPL);

            if (preso != null) {
                listaPPL.remove(preso);
                System.out.println("Preso eliminado de la base de datos");
                controlador.escribirPPL(listaPPL);
            } else {
                System.out.println("EL PRESO NO EXISTE");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error de lectura de datos. Ingrese datos correctos");
            sc.nextLine(); //Limpiar el input inválido
        }
    }

    private static void buscarPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
        int opcion = 0;

        try {
            do {
                System.out.println("-------------------------------------------");
                System.out.println("1. Buscar lista de PPL por numero de delitos");
                System.out.println("2. Buscar lista de PPL por tipo de gravedad");
                System.out.println("3. Buscar lista de PPL por delito");
                System.out.println("4. Buscar lista de PPL por dias de visita permitidos");
                System.out.println("5. Buscar lista de PPL que superen x días de condena");
                System.out.println("6. Buscar PPL por nombre");
                System.out.println("7. Imprimir numero de delitos totales cometidos por los PPL");
                System.out.println("0. Salir ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese el numero de delitos: ");
                        int delitos = sc.nextInt();
                        sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorNumeroDelito(delitos));
                    }
                    case 2 -> {
                        System.out.print("Ingrese la gravedad del delito a buscar: (Alta | Media | Baja): ");
                        String gravedad = sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorGravedad(gravedad));
                    }
                    case 3 -> {
                        System.out.print("Ingrese el delito a buscar: ");
                        String delito = sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorDelito(delito));
                    }

                    case 4 -> {
                        System.out.print("Ingrese el numero de dias de visita permitido: ");
                        int dias = sc.nextInt();
                        sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorNumeroVisitas(dias));
                    }

                    case 5 -> {
                        System.out.print("Ingrese numero de dias de condena mínima: ");
                        int diasCodena = sc.nextInt();
                        sc.nextLine();
                        mostrarListaPPL(controlador.buscador(listaPPL).getPresosPorDiasCondena(diasCodena));
                    }

                    case 6 -> {
                        System.out.print("Ingrese el nombre del PPL: ");
                        String nombre = sc.nextLine();
                        PPL pplEncontrado = (controlador.buscador(listaPPL).getPresoPorNombre(nombre));
                        if (pplEncontrado != null) {
                            System.out.println(pplEncontrado);
                        } else {
                            System.out.println("PPL con nombre de: " + nombre + " no existe en la base de datos.");
                        }
                    }
                    case 7 -> {
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
            PPL pplEncontrado = controlador.buscador(listaPPL).getPresoPorNombre(sc.nextLine());

            if (pplEncontrado != null) {
                System.out.println(pplEncontrado);
                System.out.println("1. Agregar delito");
                System.out.print("2. Eliminar delito: ");
                int opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 1) {
                    System.out.print("Ingrese nombre del delito: ");
                    String delito = sc.nextLine();
                    System.out.print("Ingrese gravedad del delito (Alta | Media | Baja): ");
                    String gravedad = validarGravedad(sc);

                    pplEncontrado.agregarDelito(new Delito(delito, gravedad));
                    controlador.escribirPPL(listaPPL); //Actualizar la database ya que se modificaron datos
                    System.out.println("Delito agregado al PPL: " + pplEncontrado.getNombre());
                    return;
                }

                if (opcion == 2) {
                    System.out.print("Ingrese nombre del delito a eliminar: ");
                    String delito = sc.nextLine().trim();

                    System.out.println(pplEncontrado.eliminarDelito(delito));;
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
            PPL pplEncontrado = controlador.buscador(listaPPL).getPresoPorNombre(sc.nextLine());
            if (pplEncontrado != null) {
                System.out.println(pplEncontrado);
                System.out.println("1. Aplicar castigo BAJO");
                System.out.println("2. Aplicar castigo MEDIO");
                System.out.println("3. Aplicar castigo ALTO");
                int opcion = sc.nextInt();
                sc.nextLine();
                System.out.println(pplEncontrado.aplicarCastigo(opcion));
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

}
