package project.catalin.mybets.datos.dataObjects;

import java.util.Date;

/**
 * Created by CDD on 19/05/2016.
 */
public class EntradaMuro {
    private Date mFecha;
    private int mPuntos;
    private String mNombrePartida;
    private Persona mPersona;

    public Date getFecha() {
        return mFecha;
    }

    public int getPuntos() {
        return mPuntos;
    }

    public String getNombrePartida() {
        return mNombrePartida;
    }

    public Persona getPersona() {
        return mPersona;
    }

    public void setPuntosGanados(int puntosGanados) {
        mPuntos = puntosGanados;
    }

    public void setNombreJuego(String nombreJuego) {
        mNombrePartida = nombreJuego;
    }

    public void setFecha(Date fecha) {
        mFecha = fecha;
    }

    public void setPersona(Persona persona) {
        mPersona = persona;
    }
}
