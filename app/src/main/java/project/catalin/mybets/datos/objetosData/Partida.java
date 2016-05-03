package project.catalin.mybets.datos.objetosData;

import java.util.Date;

/**
 * Created by Trabajo on 19/04/2016.
 */
public class Partida {
    public static int TIPO_PARTIDA_1x2 = 0;
    public static int TIPO_PARTIDA_RESULT_EXACT = 1;


    private int mIdPartida;
    private int mBote;
    private int mNumPersonas;
    private int mTipoPartida;
    private String mNombrePartida;
    private String mUrlIcono;
    private String mColorPartida;
    private Date mFecha;

    public Partida(int idPartida, String nombrePartida, Date fecha, int bote, int numPersonas, String urlIcono) {
        mIdPartida = idPartida;
        mNombrePartida = nombrePartida;
        mFecha = fecha;
        mBote = bote;
        mNumPersonas = numPersonas;
        mUrlIcono = urlIcono;
    }

    public int getIdPartida() {
        return mIdPartida;
    }

    public String getColorIcono() {
        return mColorPartida;
    }

    public void setColorIcono(String colorIcono) {
        mColorPartida = colorIcono;
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

    public int getTipoPartida() {
        return mTipoPartida;
    }

    public void setTipoPartida(int tipoPartida) {
        mTipoPartida = tipoPartida;
    }

}
