package Model;

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
    int diasVisitaPermitidos;
    int diasCondena = 0;
    
    
    public PPL(String nombre, ArrayList<Delito> delitos, LocalDateTime fechaIngreso) {
        this.nombre = nombre;
        this.delitos = delitos;
        this.fechaIngreso = fechaIngreso;
        diasVisitaPermitidos = 6; //Por defecto, tiene derecho a 6 días de visita por semana
        inicializarCaracteristicas();
    }
    
    //Metodo que inicializa los dias de visita y el tiempo de condena basado en los delitos cometidos.
    private void inicializarCaracteristicas(){
        for(Delito delito : delitos){
            
            if(delito.getGravedad().equalsIgnoreCase("Alta")) {
                diasVisitaPermitidos -= 2;
                diasCondena += 1000;
            }else if(delito.getGravedad().equalsIgnoreCase("Media")){
                diasVisitaPermitidos--;
                diasCondena += 365;
            }else{
                diasCondena += 100;
            }
            
        }
    }

    @Override
    public String toString() {
        return "PPL: " + nombre + " ingresó en: " + fechaIngreso + " tiene los delitos de: " + delitos.toString();
    }
    
    public void agregarDelito(Delito delito){
        delitos.add(delito);
        inicializarCaracteristicas();
    }

    /*
    Los castigos se aplican de acuerdo a su nivel de gravedad:
    Gravedad 1: Nivel bajo, se resta 1 día de visita por semana y se agregan +30 dias de condena.
    Gravedad 2: Nivel moderado, se restan 3 días de visita por semana y se agregan + 365 dias de condena
    Gravedad 3: Nivel alto, se le prohiben los días de visita totales y se agregan +1000 días de condena
     */
    public void aplicarCastigo(int gravedad) {
        switch (gravedad) {
            case 1 -> {
                diasVisitaPermitidos--;
                diasCondena += 30;
            }
            case 2 -> {
                diasVisitaPermitidos -= 3;
                diasCondena += 365;
            }
            case 3 -> {
                diasVisitaPermitidos -= diasVisitaPermitidos;
                diasCondena += 1000;
            }
            default -> {
            }
             
        }
    }

    public ArrayList<Delito> getDelitos() {
        return delitos;
    }
    

}
