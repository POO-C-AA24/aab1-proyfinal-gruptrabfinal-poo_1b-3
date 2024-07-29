
package Controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Carlos Sánchez & Cody
 */
public class PPLGravedadAlta extends PPL implements Serializable{
    
    double probabilidadCondenaMuerte;
    boolean ejecutado;
    public PPLGravedadAlta(String nombre, ArrayList<Delito> delitos, LocalDateTime fechaIngreso) {
        super(nombre, delitos, fechaIngreso);
        probabilidadCondenaMuerte = 0;
        ejecutado = false;
    }

    @Override
    public String aplicarCastigo(int gravedad) {
         switch (gravedad) {
            case 1 -> {
                diasCondena += 300;
                aplicarProbabilidadCondenaMuerte(1.5);
                return "Se agregaron 300 días de condena";
            }
            case 2 -> {
                diasCondena += 1000;
                        aplicarProbabilidadCondenaMuerte(10);
                return "Se agregaron 1000 días de condena";
            }
            case 3 -> {
                diasCondena += 2000;
                        aplicarProbabilidadCondenaMuerte(30);
                return "Se agregaron 2000 días de condena";

            }
            default -> {
                return "Castigo no válido. Castigos permitidos: (1. BAJO || 2. MEDIO || 3. ALTO)";
            }

        }
         
         
    }
    
    public String aplicarProbabilidadCondenaMuerte(double probabilidad){
        probabilidadCondenaMuerte = (probabilidadCondenaMuerte+probabilidad > 100) ? 100 : probabilidadCondenaMuerte+probabilidad;
        
        if(probabilidadCondenaMuerte + probabilidad >= 100){
            probabilidadCondenaMuerte = 100;
            ejecutado = true;
            return nombre + " fue ejecutado debido a las decisiones tomadas durante el transcurso de los hechos";
        }
        probabilidadCondenaMuerte += probabilidad;
        return nombre + " tiene ahora un " + probabilidadCondenaMuerte + " de ser ejecutado.";
    }
    
     @Override
    public String toString() {
        String estado = (ejecutado) ? "ESTE PRESO FUE EJECUTADO" : "ESTE PRESO SE ENCUENTRA CON VIDA";
        return "PPL: " + nombre + " ingresó en: " + fechaIngreso + " tiene condena de: " + diasCondena + " dias. " + estado +  ". NO TIENE DERECHO A VISITAS. DELITOS:" + delitos.toString();
    }
    
}
