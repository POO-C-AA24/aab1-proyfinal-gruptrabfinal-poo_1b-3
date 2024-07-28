
package Controller;

import java.io.Serializable;

/**
 *
 * @author Carlos &  Cody
 */

//Clase pequeña para moldear los delitos de los PPL
public class Delito implements Serializable{
    private String delito, gravedad;

    public Delito(String delito, String gravedad) {
        this.delito = delito.trim();
        this.gravedad = gravedad.trim();
    }

    public String getDelito() {
        return delito;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getGravedad() {
        return gravedad;
    }
    
    
    @Override
    public String toString(){
        return "--[" + delito + "] con gravedad: [" + gravedad + "]--";
    }
}
