
package Model;

import Controller.PPL;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
    private static final String RUTA_PPL_TXT = "files/privados_libertad_texto.txt";
    
    public boolean escribirPPL(ArrayList<PPL> arregloPPL){
        try{
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(RUTA_PPL));
            escritor.writeObject(arregloPPL);
            escritor.close();
            return true;
        }catch(IOException e){
            System.out.println("ERROR AL GUARDAR " + e);
            return false;
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
    
    public boolean escribirTXT(ArrayList<PPL> arregloPPL){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_PPL_TXT));
            for(PPL ppl : arregloPPL){
                writer.write(ppl.toString());
                writer.newLine();
                writer.newLine();
            }
            writer.close();
            return true;
            
        }catch(Exception e){
            System.out.println("Error al escribir el archivo TXT " + e);
            return false;
        }
    }
    
    
  
}
