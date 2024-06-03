package View;

import Controller.PPLController;
import Model.Delito;
import Model.EstadisticasPPL;
import Model.PPL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
                    // Lógica adicional aquí
                    break;
                default:
                    System.out.println("Opción no existe");
                 
            }
            
            Thread.sleep(3000);
        }
    }

    private static void mostrarListaPPL(ArrayList<PPL> listaPPL) {
        
        if(listaPPL.isEmpty()){
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
}
