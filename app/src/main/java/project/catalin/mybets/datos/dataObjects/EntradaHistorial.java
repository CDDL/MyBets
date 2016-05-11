package project.catalin.mybets.datos.dataObjects;

import java.util.Date;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class EntradaHistorial {
    private int mId;
    private String mTitulo;
    private int mPuntos;
    private Date mFecha;
    private String mNombre;

    public int getId() {
        return mId;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public void setTitulo(String titulo) {
        mTitulo = titulo;
    }

    public int getPuntos() {
        return mPuntos;
    }

    public void setPuntos(int puntos) {
        mPuntos = puntos;
    }

    public Date getFecha() {
        return mFecha;
    }

    public void setFecha(Date fecha) {
        mFecha = fecha;
    }
}
