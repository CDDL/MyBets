package project.catalin.mybets.datos.dataObjects;

/**
 * Created by CDD on 16/05/2016.
 */
public class EntradaClasificacion {
    private Persona mPersona;
    private int mPuntos;
    private int mPosicion;

    public int getPosicion() {
        return mPosicion;
    }

    public Persona getPersona() {
        return mPersona;
    }

    public int getPuntos() {
        return mPuntos;
    }

    public void setPersona(Persona persona) {
        mPersona = persona;
    }

    public void setPuntos(int puntos) {
        mPuntos = puntos;
    }

    public void setPosicion(int posicion) {
        mPosicion = posicion;
    }
}
