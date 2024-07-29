
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
public abstract class PPL implements Serializable {

    protected String nombre;
    protected ArrayList<Delito> delitos;
    protected LocalDateTime fechaIngreso;
    protected int diasCondena = 0;


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
                diasCondena += 800;
                 
            } else if(delito.getGravedad().equalsIgnoreCase("media")){
                diasCondena += 400;
            }else{
                diasCondena += 150;
            }

        
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
    ESTOS SE APLICARÁN DE FORMA ESPECIALIZADA DEPENDIENDO SI SE TIENE QUE SOBREESCRIBIR EN UN PPLGravedadAlta o PPLGravedadLeve
     */
    public abstract String aplicarCastigo(int gravedad);
       
    
    public ArrayList<Delito> getDelitos() {
        return delitos;
    }

    public String getNombre() {
        return nombre;
    }


    public int getDiasCondena() {
        return diasCondena;
    }

}
