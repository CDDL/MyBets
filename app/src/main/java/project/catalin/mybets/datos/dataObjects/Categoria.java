package project.catalin.mybets.datos.dataObjects;

/**
 * Created by CDD on 17/05/2016.
 */
public class Categoria {
    private int mId;
    private String mUrlIcono;
    private String mNombre;

    public int getId() {
        return mId;
    }

    public String getUrlIcono() {
        return mUrlIcono;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setUrlIcono(String urlIcono) {
        mUrlIcono = urlIcono;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }
}
