package project.catalin.mybets.datos.dataObjects;

import java.util.Date;
import java.util.List;

/**
 * Created by CDD on 16/05/2016.
 */
public class FichaClasificacion {
    private List<EntradaClasificacion> mEntradasList;
    private String mNombrePartida;
    private String mColorFondo;
    private Date mFechaPartida;
    private String mUrlIcono;
    private int mBotePartida;

    public void setNombrePartida(String nombrePartida) {
        mNombrePartida = nombrePartida;
    }

    public void setFechaPartida(Date fechaPartida) {
        mFechaPartida = fechaPartida;
    }

    public void setBotePartida(int botePartida) {
        mBotePartida = botePartida;
    }

    public void setUrlIcono(String urlIcono) {
        mUrlIcono = urlIcono;
    }

    public void setColorFondo(String colorFondo) {
        mColorFondo = colorFondo;
    }


    public void setEntradasList(List<EntradaClasificacion> entradasList) {
        mEntradasList = entradasList;
    }

    public String getNombrePartida() {
        return mNombrePartida;
    }

    public Date getFecha() {
        return mFechaPartida;
    }

    public int getBote() {
        return mBotePartida;
    }

    public List<EntradaClasificacion> getEntradas() {
        return mEntradasList;
    }

    public String getUrlIcono() {
        return mUrlIcono;
    }
}
