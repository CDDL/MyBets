package project.catalin.mybets.datos.dataObjects;

import java.util.List;

/**
 * Created by Trabajo on 04/05/2016.
 */
public class Apuesta {
    public static final int APUESTA_LOCAL = 1;
    public static final int APUESTA_VISITANTE = 2;
    public static final int APUESTA_EMPATE = 0;
    private String mNombreLocal;
    private String mUrlImagenLocal;
    private String mNombreVisitante;
    private String mUrlImagenVisitante;
    private int mApuesta;
    private long mId;

    public void setNombreLocal(String nombreLocal) {
        mNombreLocal = nombreLocal;
    }

    public void setUrlImagenLocal(String urlImagenLocal) {
        mUrlImagenLocal = urlImagenLocal;
    }

    public void setNombreVisitante(String nombreVisitante) {
        mNombreVisitante = nombreVisitante;
    }

    public void setUrlImagenVisitante(String urlImagenVisitante) {
        mUrlImagenVisitante = urlImagenVisitante;
    }

    public void setApuesta(int apuesta) {
        mApuesta = apuesta;
    }

    public long getId() {
        return mId;
    }

    public String getIconLocal() {
        return mUrlImagenLocal;
    }

    public String getIconVisitantes() {
        return mUrlImagenVisitante;
    }

    public String getNombreEquipoLocal() {
        return mNombreLocal;
    }

    public String getNombreEquipoVisitante() {
        return mNombreVisitante;
    }

    public int getApuesta() {
        return mApuesta;
    }

}
