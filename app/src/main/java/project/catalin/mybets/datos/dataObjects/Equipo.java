package project.catalin.mybets.datos.dataObjects;

/**
 * Created by Trabajo on 05/05/2016.
 */
public class Equipo {
    private int mId;
    private String mNombre;
    private String mIcono;

    public void setId(int id) {
        mId = id;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public void setIcono(String urlIcono) {
        mIcono = urlIcono;
    }

    public String getIcon() {
        return mIcono;
    }

    public String getNombre() {
        return mNombre;
    }
}
