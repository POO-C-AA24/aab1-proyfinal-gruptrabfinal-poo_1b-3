package View;

import Controller.PPLController;
import Model.Delito;
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

        OUTER:
        while (true) {
            System.out.print(controlador.mostrarMenu());
            opcionPrincipal = sc.nextInt(); sc.nextLine();
            
            
            switch (opcionPrincipal) {
                case 1:
                    
                    break;
                case 2: //Agregar PPL
                    System.out.print("Ingrese nombre del PPL: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el numero de delitos cometidos por el PPL: " + nombre + ": ");
                    int numeroDelitos = sc.nextInt(); sc.nextLine();
                    
                    ArrayList<Delito> delitos = new ArrayList<>();
                    
                    while(numeroDelitos > 0){
                        System.out.print("Ingrese el delito cometido: ");
                        String delito = sc.nextLine();
                        System.out.print("Ingrese la gravedad del delito (Alta | Media | Baja): ");
                        String gravedad = sc.nextLine();
                        
                        delitos.add(new Delito(delito, gravedad));
                        
                        numeroDelitos--;
                    }
                    
                    //Crear al PPL y guardarlo en la base de datos: 
                    PPL nuevoPPL = new PPL(nombre, delitos, fechaActual);
                    listaPPL.add(nuevoPPL);
                    controlador.escribirPPL(listaPPL);
                    System.out.println("PPL AGREGADO");
                    
                    break;
                case 3:

                    break;
                case 4:

                    break;
                default:
                    System.out.println("Opcion no existe");
                    break OUTER;
            }

        }
        System.out.println("Apagando el programa: ");
        Thread.sleep(2000);
    }
}
