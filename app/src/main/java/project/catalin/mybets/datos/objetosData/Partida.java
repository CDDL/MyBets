package project.catalin.mybets.datos.objetosData;

import java.util.Date;

/**
 * Created by Trabajo on 19/04/2016.
 */
public class Partida {
    private final int mIdPartida;
    private final String mNombrePartida;
    private final Date mFecha;
    private final int mBote;
    private final int mNumPersonas;
    private final String mUrlIcono;
    private long mId;
    private int mTipoPartida;
    private int mColorPartida;

    public Partida(int idPartida, String nombrePartida, Date fecha, int bote, int numPersonas, String urlIcono) {
        mIdPartida = idPartida;
        mNombrePartida = nombrePartida;
        mFecha = fecha;
        mBote = bote;
        mNumPersonas = numPersonas;
        mUrlIcono = urlIcono;
    }

    public long getId() {
        return mId;
    }

    public int getColorIcono() {
        return mColorPartida;
    }

    public String getUrlIcono() {
        return mUrlIcono;
    }

    public String getTitulo() {
        return mNombrePartida;
    }

    public int getBote() {
        return mBote;
    }

    public int getNumPersonas() {
        return mNumPersonas;
    }

    public Date getFecha() {
        return mFecha;
    }

    public void setTipoPartida(int tipoPartida) {
        mTipoPartida = tipoPartida;
    }

    public void setColorIcono(int colorIcono) {
        mColorPartida = colorIcono;
    }
}
