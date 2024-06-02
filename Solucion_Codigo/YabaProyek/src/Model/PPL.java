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
    int diasCondena;
    
    
    public PPL(String nombre, ArrayList<Delito> delitos, LocalDateTime fechaIngreso, int diasVisita) {
        this.nombre = nombre;
        this.delitos = delitos;
        this.fechaIngreso = fechaIngreso;
        diasVisitaPermitidos = 6; //Por defecto, tiene derecho a 6 días de visita por semana

    }
    
    //Metodo que inicializa los dias de visita y el tiempo de condena basado en los delitos cometidos.
    private void inicializarCaracteristicas(){
        for(Delito delito : delitos){
            
            if(delito.getGravedad().equalsIgnoreCase("Alta")){
                
            }else if(delito.getGravedad().equalsIgnoreCase("Media")){
                
            }else{
                
            }
            
        }
    }

    @Override
    public String toString() {
        return "PPL: " + nombre + " ingresó en: " + fechaIngreso + " tiene los delitos de: " + delitos.toString();
    }

    /*
    Los castigos se aplican de acuerdo a su nivel de gravedad:
    Gravedad 1: Nivel bajo, se resta 1 día de visita por semana
    Gravedad 2: Nivel moderado, se restan 3 días de visita por semana
    Gravedad 3: Nivel alto, se le prohiben los días de visita totales. 
     */
    public void aplicarCastigo(int gravedad) {
        switch (gravedad) {
            case 1:
                diasVisitaPermitidos--;
                break;
            case 2:
                diasVisitaPermitidos -= 3;
                break;
            case 3:
                diasVisitaPermitidos -= diasVisitaPermitidos;
                break;
            default:
             
        }
    }

}
