
package Controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Carlos Sánchez & Cody
 */
public class PPLGravedadBaja extends PPL implements Serializable{
    private int diasVisitaPermitidos = 6; 
    
    public PPLGravedadBaja(String nombre, ArrayList<Delito> delitos, LocalDateTime fechaIngreso) {
        super(nombre, delitos, fechaIngreso);
    }
    
    
      public void restarDiasVisita(int n) {
        diasVisitaPermitidos = (diasVisitaPermitidos - n < 0) ? 0 : diasVisitaPermitidos - n;
    }
      
      

    @Override
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
    
     @Override
    public String toString() {
        return "PPL: " + nombre + " ingresó en: " + fechaIngreso + " tiene condena de: " + diasCondena + " dias y derecho a " + diasVisitaPermitidos + " dias de visita permitidos. Tiene los delitos de: " + delitos.toString();
    }

    public int getDiasVisitaPermitidos() {
        return diasVisitaPermitidos;
    }
}
