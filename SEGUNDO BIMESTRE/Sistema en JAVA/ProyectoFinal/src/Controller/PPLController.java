
package Controller;

import Model.PPLDatabase;
import java.util.ArrayList;

/**
 *
 * @author Carlos & Cody.
 */

//Esta clase sirve de puente intermedio entre la clase main de ejecucion y la base de datos. 
public class PPLController {
    private static PPLDatabase database = new PPLDatabase();
    public String mostrarMenu(){
        String menu =
                """
                -----------------------------------
                [1]. Mostrar PPL activos
                [2]. Agregar nuevo PPL 
                [3]. Eliminar PPL 
                [4]. Busqueda de PPLs
                [5]. Agregar/Eliminar delito a PPL
                [6]. Aplicar agravante a PPL
                [7]. Generar PPLs activos en .txt    
                [8]. Salir.
                -----------------------------------
                """;
                
                
        
        
        return menu;
    }
    
    public ArrayList<PPLGravedadAlta> leerPPLGravedadAlta(){
        return database.leerPPLGrave();
    }
    
    public ArrayList<PPLGravedadBaja> leerPPLGravedadBaja(){
        return database.leerPPLLeve();
    }
    
     public boolean escribirPPLGravedadAlta(ArrayList<PPLGravedadAlta> ppl){
        return database.escribirPPLGrave(ppl);
    }
     
       public boolean escribirPPLGravedadBaja(ArrayList<PPLGravedadBaja> ppl){
        return database.escribirPPLLeve(ppl);
    }
     
       
       
     public boolean escribirTXT(ArrayList<PPL> ppl){
         return database.escribirTXT(ppl);
     }
     
     public EstadisticasPPL buscador(ArrayList<PPLGravedadAlta> pplAlta, ArrayList<PPLGravedadBaja> pplBaja){
         return new EstadisticasPPL(pplAlta, pplBaja);
     }
    
    
}
