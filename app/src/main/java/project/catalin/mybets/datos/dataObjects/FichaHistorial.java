package project.catalin.mybets.datos.dataObjects;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by CDD on 12/05/2016.
 */
public class FichaHistorial {
    private Persona mPersona;
    private List<Apuesta> mListaApuestas;
    private Date mFecha;
    private int mBote;
    private String mUrlIcono;
    private String mColorFondo;

    public FichaHistorial() {
        mListaApuestas = new LinkedList<>();
    }

    public void setPersona(Persona persona) {
        mPersona = persona;
    }


    public void addApuesta(Apuesta apuesta) {
        mListaApuestas.add(apuesta);
    }

    public List<Apuesta> getApuestas() {
        return mListaApuestas;
    }

    public Date getData() {
        return mFecha;
    }

    public int getBote() {
        return mBote;
    }

    public String getUrlImagen() {
        return mPersona.getImagen();
    }

    public String getUrlIcono() {
        return mUrlIcono;
    }

    public void setBote(int bote) {
        mBote = bote;
    }

    public void setColorFondo(String colorfondo) {
        mColorFondo = colorfondo;
    }

    public void setUrlIcono(String urlIcono) {
        mUrlIcono = urlIcono;
    }

    public void setFecha(Date fecha) {
        mFecha = fecha;
    }

    public String getColorCabecera() {
        return mColorFondo;
    }

    public int getIdUsuario() {
        return mPersona.getId();
    }
}
