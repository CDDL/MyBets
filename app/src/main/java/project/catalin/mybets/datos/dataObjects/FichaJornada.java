package project.catalin.mybets.datos.dataObjects;

import java.util.List;

/**
 * Created by CDD on 27/05/2016.
 */
public class FichaJornada {
    private List<Partido> mListPartidos;
    private int mIdJornada;

    public List<Partido> getListaPartidos() {
        return mListPartidos;
    }

    public int getIdJornada() {
        return mIdJornada;
    }

    public void setIdJornada(int idJornada) {
        mIdJornada = idJornada;
    }

    public void setListPartidos(List<Partido> listPartidos) {
        mListPartidos = listPartidos;
    }
}
