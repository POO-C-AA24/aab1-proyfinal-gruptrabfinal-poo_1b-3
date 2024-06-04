package View;

import Controller.PPLController;
import Model.Delito;
import Model.PPL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Carlos & Cody
 */
public class Ejecucion {

    public static void main(String[] args) throws InterruptedException {
        LocalDateTime fechaActual = LocalDateTime.now();
        PPLController controlador = new PPLController();
        Scanner sc = new Scanner(System.in);
        ArrayList<PPL> listaPPL = controlador.leerPPL();

        System.out.println("---Bienvenido al sistema de control de personas privadas de libertad (PPL)---");
        int opcionPrincipal;

        while (true) {
            System.out.println("-----------------------");
            System.out.print(controlador.mostrarMenu());
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
                    buscarPPL(controlador, listaPPL, sc);
                    break;
                default:
                    System.out.println("Opción no existe");

            }

            Thread.sleep(3000);
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
        System.out.print("Ingrese nombre del PPL: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese el numero de delitos cometidos por el PPL " + nombre + ": ");
        int numeroDelitos = sc.nextInt();
        sc.nextLine();

        ArrayList<Delito> delitos = new ArrayList<>();

        while (numeroDelitos > 0) {
            System.out.print("Ingrese el delito cometido: ");
            String delito = sc.nextLine();
            System.out.print("Ingrese la gravedad del delito (Alta | Media | Baja): ");
            String gravedad = sc.nextLine();
            delitos.add(new Delito(delito, gravedad));
            numeroDelitos--;
        }

        PPL nuevoPPL = new PPL(nombre, delitos, fechaActual);
        listaPPL.add(nuevoPPL);
        controlador.escribirPPL(listaPPL);
        System.out.println("PPL AGREGADO");
    }

    private static void eliminarPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
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
    }

    private static void buscarPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
        int opcion = 0;

        do {
            System.out.println("-------------------------------------------");
            System.out.println("1. Buscar lista de PPL por numero de delitos");
            System.out.println("2. Buscar lista de PPL por tipo de gravedad");
            System.out.println("3. Buscar lista de PPL por delito");
            System.out.println("4. Buscar PPL por nombre");
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
                    System.out.print("Ingrese el nombre del PPL: ");
                    String nombre = sc.nextLine();
                    PPL pplEncontrado = (controlador.buscador(listaPPL).getPresoPorNombre(nombre));
                    if (pplEncontrado != null) {
                        System.out.println(pplEncontrado);
                    } else {
                        System.out.println("PPL con nombre de: " + nombre + " no existe en la base de datos.");
                    }
                }

            }

        } while (opcion != 0);
    }

    private static void modificarDelitoPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
        System.out.print("Ingrese nombre del PPL a modificar: ");
        PPL pplEncontrado = controlador.buscador(listaPPL).getPresoPorNombre(sc.nextLine());

        if (pplEncontrado != null) {
            System.out.println(pplEncontrado);
            System.out.println("1. Agregar delito");
            System.out.print("2. Eliminar delito");
            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                System.out.print("Ingrese nombre del delito: ");
                String delito = sc.nextLine();
                System.out.print("Ingrese gravedad del delito (Alta | Media | Baja): ");
                String gravedad = sc.nextLine();

                pplEncontrado.agregarDelito(new Delito(delito, gravedad));
                controlador.escribirPPL(listaPPL); //Actualizar la database ya que se modificaron datos
                System.out.println("Delito agregado al PPL: " + pplEncontrado.getNombre());
                return;
            }

            if (opcion == 2) {
                System.out.print("Ingrese nombre del delito a eliminar: ");
                String delito = sc.nextLine();
                
                 //Buscar los delitos e eliminarlo. TODO
                
                controlador.escribirPPL(listaPPL); //Actualizar la database ya que se modificaron datos
                System.out.println("Delito eliminado para el PPL: " + pplEncontrado.getNombre());
                return;
            }

        }
    }

    private static void aplicarCastigoPPL(PPLController controlador, ArrayList<PPL> listaPPL, Scanner sc) {
    }

}
