package Model;

import Controller.PPL;
import Controller.PPLGravedadAlta;
import Controller.PPLGravedadBaja;
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

    private static final String RUTA_PPL_GRAVE = "files/privados_libertad_grave.dat";
    private static final String RUTA_PPL_LEVE = "files/privados_libertad_leve.dat";
    
    
    private static final String RUTA_PPL_TXT = "files/privados_libertad_texto.txt";

    


    public boolean escribirPPLGrave(ArrayList<PPLGravedadAlta> arregloPPL) {
        try {
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(RUTA_PPL_GRAVE));
            escritor.writeObject(arregloPPL);
            escritor.close();
            return true;
        } catch (IOException e) {
            System.out.println("ERROR AL GUARDAR " + e);
            return false;
        }
    }
    
    public boolean escribirPPLLeve(ArrayList<PPLGravedadBaja> arregloPPL) {
        try {
            ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(RUTA_PPL_LEVE));
            escritor.writeObject(arregloPPL);
            escritor.close();
            return true;
        } catch (IOException e) {
            System.out.println("ERROR AL GUARDAR " + e);
            return false;
        }
    }

    public ArrayList<PPLGravedadAlta> leerPPLGrave() {
        try {

            ObjectInputStream lector = new ObjectInputStream(new FileInputStream(RUTA_PPL_GRAVE));
            ArrayList<PPLGravedadAlta> ppl = (ArrayList<PPLGravedadAlta>) lector.readObject();
            return ppl;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR AL LEER " + e);
            System.out.println("CREANDO UN ARCHIVO .dat vacío");
            escribirPPLGrave(new ArrayList<PPLGravedadAlta>());
            return new ArrayList<PPLGravedadAlta>();
        }
    }
    
     public ArrayList<PPLGravedadBaja> leerPPLLeve() {
        try {

            ObjectInputStream lector = new ObjectInputStream(new FileInputStream(RUTA_PPL_LEVE));
            ArrayList<PPLGravedadBaja> ppl = (ArrayList<PPLGravedadBaja>) lector.readObject();
            return ppl;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR AL LEER " + e);
            System.out.println("CREANDO UN ARCHIVO .dat vacío");
            escribirPPLLeve(new ArrayList<PPLGravedadBaja>());
            return new ArrayList<PPLGravedadBaja>();
        }
    }
    
    

    public boolean escribirTXT(ArrayList<PPL> arregloPPL) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_PPL_TXT));
            for (PPL ppl : arregloPPL) {
                writer.write(ppl.toString());
                writer.newLine();
                writer.newLine();
            }
            writer.close();
            return true;

        } catch (Exception e) {
            System.out.println("Error al escribir el archivo TXT " + e);
            return false;
        }
    }

}
