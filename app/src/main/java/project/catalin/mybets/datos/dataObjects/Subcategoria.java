package project.catalin.mybets.datos.dataObjects;

import java.util.List;

/**
 * Created by CDD on 17/05/2016.
 */
public class Subcategoria {
    private List<Partida> mListaPartidas;
    private int mId;
    private String mNombreSubcategoria;

    public List<Partida> getListPartidas() {
        return mListaPartidas;
    }

    public int getId() {
        return mId;
    }

    public void setListPartidas(List<Partida> listaPartidas) {
        mListaPartidas = listaPartidas;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setNombreSubcategoria(String nombreSubcategoria) {
        mNombreSubcategoria = nombreSubcategoria;
    }

    public String getNombreSubcategoria() {
        return mNombreSubcategoria;
    }
}
