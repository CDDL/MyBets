package project.catalin.mybets.datos.dataObjects;

import java.util.Date;

/**
 * Created by Trabajo on 19/04/2016.
 */
public class Partida {
    public static final int TIPO_PARTIDA_1x2 = 0;
    public static final int TIPO_PARTIDA_RESULT_EXACT = 1;
    public static final int ESTADO_JUEGA_YA = 2;
    public static final int ESTADO_PARTIDA_RECHAZADA = 0;
    public static final int ESTADO_ESPERANDO_RESULTADOS = 1;
    public static final int ESTADO_PARTIDA_GANADA = 3;


    private int mIdPartida;
    private int mBote;
    private int mNumPersonas;
    private int mTipoPartida;
    private String mNombrePartida;
    private String mUrlIcono;
    private String mColorPartida;
    private int mMaxNumPersonas;
    private int mEstadoPartida;
    private int mPuntosGanados;
    private Date mFecha;


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

    public void setFecha(Date fecha) {
        mFecha = fecha;
    }

    public int getTipoPartida() {
        return mTipoPartida;
    }

    public void setTipoPartida(int tipoPartida) {
        mTipoPartida = tipoPartida;
    }

    public int getMaxNumPersonas() {
        return mMaxNumPersonas;
    }

    public int getEstadoPartida() {
        return mEstadoPartida;
    }

    public int getPuntosGanados() {
        return mPuntosGanados;
    }

    public void setId(int id) {
        mIdPartida = id;
    }

    public void setNombrePartida(String nombrePartida) {
        mNombrePartida = nombrePartida;
    }

    public void setBote(int bote) {
        mBote = bote;
    }

    public void setNumPersonas(int numPersonas) {
        mNumPersonas = numPersonas;
    }

    public void setUrlIcono(String urlIcono) {
        mUrlIcono = urlIcono;
    }

    public void setEstadoPartida(int estadoPartida) {
        mEstadoPartida = estadoPartida;
    }
}
