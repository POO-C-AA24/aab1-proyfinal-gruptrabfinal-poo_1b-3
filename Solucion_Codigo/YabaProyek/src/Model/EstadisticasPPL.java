
package Model;

import java.util.ArrayList;

/**
 *
 * @author Carlos & Cody
 */

/*
Clase que devuelve las estadísticas de los PPL en base a cualquier metodo y los datos disponibles en el constructor dado.
*/
public class EstadisticasPPL {
    
    private ArrayList<PPL> arrayPPL;

    public EstadisticasPPL(ArrayList<PPL> ppl) {
        this.arrayPPL = ppl;
    }
    
    /*
    Devuelve la cantidad de presos que contengan n numero de delitos.
    */
    public ArrayList<PPL> getPresosPorNumeroDelito(int n){
        ArrayList<PPL> resultadosPPL = new ArrayList<>();
        
        for(PPL ppl : arrayPPL){
           if(ppl.getDelitos().size() == n){ resultadosPPL.add(ppl);  }   
        }
        return resultadosPPL;
    }
    
}
