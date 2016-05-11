package project.catalin.mybets.datos.dataObjects;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Trabajo on 04/05/2016.
 */
public class Partido {

    private int mId;

    private List<Equipo> mListaEquipos;

    public Partido() {
        mListaEquipos = new LinkedList<>();
    }

    public void setId(int id) {
        mId = id;
    }

    public void addEquipo(Equipo equipo) {
        mListaEquipos.add(equipo);
    }

    public List<Equipo> getEquipos() {
        return mListaEquipos;
    }
}
