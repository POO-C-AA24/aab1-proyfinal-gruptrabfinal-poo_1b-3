
package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Carlos Sánchez
 */
public class PPLDatabase {
    private static final String RUTA_PPL = "files/privados_libertad.dat";
    
    public void escribirPPL(ArrayList<PPL> arregloPPL){
        try{
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(RUTA_PPL));
            escritor.writeObject(arregloPPL);
            escritor.close();
        }catch(IOException e){
            System.out.println("ERROR AL GUARDAR " + e);
        }
    }
    
    public ArrayList<PPL> leerPPL(){
        try{
            
            ObjectInputStream lector = new ObjectInputStream(new FileInputStream(RUTA_PPL));
            ArrayList<PPL> ppl  = (ArrayList<PPL>) lector.readObject();
            return ppl; 
            
        }catch(IOException | ClassNotFoundException e){
            System.out.println("ERROR AL LEER " + e);
            System.out.println("CREANDO UN ARCHIVO .dat vacío");
            escribirPPL(new ArrayList<PPL>());
            return new ArrayList<PPL>();
        }
    }
}
