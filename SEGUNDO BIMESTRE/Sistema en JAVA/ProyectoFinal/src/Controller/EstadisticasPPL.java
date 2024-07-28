package Controller;


import java.util.ArrayList;

/**
 *
 * @author Carlos & Cody
 */

/*
Clase que devuelve las estadísticas de los PPL en base a cualquier metodo y los datos disponibles en el metodo dado.
 */
public class EstadisticasPPL {

    private ArrayList<PPL> arrayPPL;

    public EstadisticasPPL(ArrayList<PPL> ppl) {
        this.arrayPPL = ppl;
    }

    /*
    Devuelve la cantidad de presos que contengan n numero de delitos.
     */
    public ArrayList<PPL> getPresosPorNumeroDelito(int n) {
        ArrayList<PPL> resultadosPPL = new ArrayList<>();

        for (PPL ppl : arrayPPL) {
            if (ppl.getDelitos().size() == n) {
                resultadosPPL.add(ppl);
            }
        }
        return resultadosPPL;
    }

    /*
    Devuelve la cantidad de presos que contengan x tipo de gravedad
     */
    public ArrayList<PPL> getPresosPorGravedad(String gravedad) {
        ArrayList<PPL> resultadosPPL = new ArrayList<>();

        for (PPL ppl : arrayPPL) {

            for (Delito delito : ppl.getDelitos()) {

                if (delito.getGravedad().equalsIgnoreCase(gravedad)) {
                    resultadosPPL.add(ppl);
                    break;
                }

            }
        }
        return resultadosPPL;
    }

    /*
    Devuelve la cantidad de presos que contengan un delito especifico
     */
    public ArrayList<PPL> getPresosPorDelito(String nombreDelito) {
        ArrayList<PPL> resultadosPPL = new ArrayList<>();

        for (PPL ppl : arrayPPL) {

            for (Delito delito : ppl.getDelitos()) {

                if (delito.getDelito().equalsIgnoreCase(nombreDelito)) {
                    resultadosPPL.add(ppl);
                    break;
                }

            }
        }
        return resultadosPPL;
    }

    /*
     Devuelve la cantidad de presos que tengan n numero de visitas permitidas (0 - 7)
     */
    public ArrayList<PPL> getPresosPorNumeroVisitas(int visitasSemanales) {
        ArrayList<PPL> resultadosPPL = new ArrayList<>();

        for (PPL ppl : arrayPPL) {
            if (ppl.getDiasVisitaPermitidos() == visitasSemanales) {
                resultadosPPL.add(ppl);
            }

        }
        return resultadosPPL;
    }

    /*
     Devuelve la cantidad de presos que superen n numero de dias de condena
     */
    public ArrayList<PPL> getPresosPorDiasCondena(int diasCondena) {
        ArrayList<PPL> resultadosPPL = new ArrayList<>();

        for (PPL ppl : arrayPPL) {
            if (ppl.getDiasCondena() >= diasCondena) {
                resultadosPPL.add(ppl);
            }

        }
        return resultadosPPL;
    }

    /*
    Devuelve PPL específico por nombre. Si existen 2 o mas con el mismo nombre, se devolverán igualmente.
     */
    public ArrayList<PPL> getPresosPorNombre(String nombrePPL) {
        ArrayList<PPL> resultadosPPL = new ArrayList<>();
        for (PPL ppl : arrayPPL) {

            if (ppl.getNombre().equalsIgnoreCase(nombrePPL)) {
                   resultadosPPL.add(ppl);
            }
        }
        return resultadosPPL;
    }

    /*
    Devuelve el numero de delitos cometidos por los PPL que existen en el momento dado.
    
     */
    public int getDelitosTotales() {
        int delitos = 0;
        for (PPL ppl : arrayPPL) {

            delitos += ppl.getDelitos().size();

        }
        return delitos;
    }

}
