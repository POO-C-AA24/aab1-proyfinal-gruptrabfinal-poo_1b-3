package Controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Carlos Sánchez & Cody
 */
public class PPLGravedadAlta extends PPL implements Serializable {

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
                return "Se agregaron 300 días de condena y " + aplicarProbabilidadCondenaMuerte(1.5);
            }
            case 2 -> {
                diasCondena += 1000;
                
                return "Se agregaron 1000 días de condena y " + aplicarProbabilidadCondenaMuerte(10);  
            }
            case 3 -> {
                diasCondena += 2000;
                return "Se agregaron 2000 días de condena y " + aplicarProbabilidadCondenaMuerte(30);

            }
            default -> {
                return "Castigo no válido. Castigos permitidos: (1. BAJO || 2. MEDIO || 3. ALTO)";
            }

        }

    }

    public String aplicarProbabilidadCondenaMuerte(double probabilidad) {
        probabilidadCondenaMuerte = (probabilidadCondenaMuerte + probabilidad > 100) ? 100 : probabilidadCondenaMuerte + probabilidad;

        if (probabilidadCondenaMuerte >= 100) {
            probabilidadCondenaMuerte = 100;
            ejecutado = true;
            return nombre + " fue ejecutado debido a las decisiones tomadas durante el transcurso de los hechos";
        }
     
        return nombre + " tiene ahora un " + probabilidadCondenaMuerte + "% probabilidad de ser ejecutado.";
    }

    @Override
    public String toString() {
        String estado = (ejecutado) ? "ESTE PRESO FUE EJECUTADO" : "ESTE PRESO SE ENCUENTRA CUMPLIENDO SU CONDENA";
        return "(GRAVEDAD ALTA) -> PPL: " + nombre + " ingresó en: " + fechaIngreso + " tiene condena de: " + diasCondena + " dias. " + estado + ". NO TIENE DERECHO A VISITAS. DELITOS:" + delitos.toString();
    }

}
