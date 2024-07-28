
package Controller;

/**
 *
 * @author Carlos & Cody
 */


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Carlos & Cody
 */
//Clase que representa individualmente a cada PPL
public class PPL implements Serializable {

    private String nombre;
    private ArrayList<Delito> delitos;
    private LocalDateTime fechaIngreso;
    private int diasVisitaPermitidos = 6; 
    private int diasCondena = 0;

    int a;

    public PPL(String nombre, ArrayList<Delito> delitos, LocalDateTime fechaIngreso) {
        this.nombre = nombre;
        this.delitos = delitos;
        this.fechaIngreso = fechaIngreso;
       
        
        
        for(Delito delito : delitos){
            aplicarDelito(delito);
        }
    }

    //Metodo que actualiza los dias de visita y el tiempo de condena basado en el delito cometido
    private void aplicarDelito(Delito delito) {
      

            if (delito.getGravedad().equalsIgnoreCase("alta")) {
                restarDiasVisita(2);
                diasCondena += 1000;
                 
            } else {
                diasCondena += 200;
            }

        
    }

    @Override
    public String toString() {
        return "PPL: " + nombre + " ingresó en: " + fechaIngreso + " tiene condena de: " + diasCondena + " dias y derecho a " + diasVisitaPermitidos + " dias de visita permitidos. Tiene los delitos de: " + delitos.toString();
    }

    public void agregarDelito(Delito delito) {
        delitos.add(delito);
        aplicarDelito(delito);
    }

    public String eliminarDelito(String nombreDelito) {

        for (Delito delito : delitos) {
            System.out.println(delito.getDelito());
            if (delito.getDelito().equalsIgnoreCase(nombreDelito)) {
                delitos.remove(delito);
                return "Delito eliminado";

            }
        }

        return "Delito no encontrado "; //

    }

    /*
    Los castigos se aplican de acuerdo a su nivel de gravedad:
    Gravedad 1: Nivel bajo, se resta 1 día de visita por semana y se agregan +30 dias de condena.
    Gravedad 2: Nivel moderado, se restan 3 días de visita por semana y se agregan + 365 dias de condena
    Gravedad 3: Nivel alto, se le prohiben los días de visita totales y se agregan +1000 días de condena
     */
    public String aplicarCastigo(int gravedad) {
        switch (gravedad) {
            case 1 -> {
                restarDiasVisita(1);
                diasCondena += 30;
                return "Se agregaron 30 días de condena y se resta 1 día de visita permitido por semana. Dias permitidos actuales: " + diasVisitaPermitidos;
            }
            case 2 -> {
                restarDiasVisita(3);
                diasCondena += 365;
                return "Se agregaron 365 días de condena y se restan 3 días de visita permitido por semana. Dias permitidos actuales: " + diasVisitaPermitidos;
            }
            case 3 -> {
                restarDiasVisita(diasVisitaPermitidos);
                diasCondena += 1000;
                return "Se agregaron 1000 días de condena y se le prohibe el derecho a recibir visitas. Dias permitidos actuales: " + diasVisitaPermitidos;

            }
            default -> {
                return "Castigo no válido. Castigos permitidos: (1. BAJO || 2. MEDIO || 3. ALTO)";
            }

        }
    }

    public void restarDiasVisita(int n) {
        diasVisitaPermitidos = (diasVisitaPermitidos - n < 0) ? 0 : diasVisitaPermitidos - n;
    }

    public ArrayList<Delito> getDelitos() {
        return delitos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDiasVisitaPermitidos() {
        return diasVisitaPermitidos;
    }

    public int getDiasCondena() {
        return diasCondena;
    }

}
