
package Controller;

import Model.PPL;
import Model.PPLDatabase;
import java.util.ArrayList;

/**
 *
 * @author Carlos & Cody.
 */

//Esta clase sirve de puente intermedio entre la clase main de ejecucion y la clase model. 
public class PPLController {
    private static PPLDatabase database = new PPLDatabase();
    public String mostrarMenu(){
        String menu =
                """
                1. Mostrar PPL activos
                2. Agregar nuevo PPL 
                3. Eliminar PPL 
                4. Busqueda de PPLs
                   
                """;
                
                
        
        
        return menu;
    }
    
    public ArrayList<PPL> leerPPL(){
        return database.leerPPL();
    }
    
     public void escribirPPL(ArrayList<PPL> ppl){
        database.escribirPPL(ppl);
    }
    
    
}
