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

    public Partida(int idPartida, String nombrePartida, Date fecha, int bote, int numPersonas, String urlIcono) {
        mIdPartida = idPartida;
        mNombrePartida = nombrePartida;
        mFecha = fecha;
        mBote = bote;
        mNumPersonas = numPersonas;
        mUrlIcono = urlIcono;
    }
}
