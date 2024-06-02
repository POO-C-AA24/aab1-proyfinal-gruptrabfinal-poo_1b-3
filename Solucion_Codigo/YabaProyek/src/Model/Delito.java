
package Model;

/**
 *
 * @author Carlos &  Cody
 */

//Clase pequeña para moldear los delitos de los PPL
public class Delito {
    private String delito, gravedad;

    public Delito(String delito, String gravedad) {
        this.delito = delito;
        this.gravedad = gravedad;
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
    
}
