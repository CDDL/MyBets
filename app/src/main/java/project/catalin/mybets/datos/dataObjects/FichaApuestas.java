package project.catalin.mybets.datos.dataObjects;

import java.util.List;

/**
 * Created by CDD on 26/05/2016.
 */
public class FichaApuestas {
    private int mIdPartida;
    private int mIdJornada;
    private List<Integer> mListApuestas;

    public void setIdPartida(int idPartida) {
        mIdPartida = idPartida;
    }

    public void setIdJornada(int idJornada) {
        mIdJornada = idJornada;
    }

    public void setListaApuestas(List<Integer> listaApuestas) {
        mListApuestas = listaApuestas;
    }

    public List<Integer> getListaApuestas() {
        return mListApuestas;
    }

    public int getIdPartida() {
        return mIdPartida;
    }

    public int getIdJornada() {
        return mIdJornada;
    }
}
