package project.catalin.mybets.controladores.comunicacionVista;

import project.catalin.mybets.datos.dataObjects.FichaClasificacion;

/**
 * Created by CDD on 16/05/2016.
 */
public interface ViewFichaClasificacion {
    void showLoadingClasificacion();

    void dismissLoadingClasificacion();

    void alert(String message);

    void setDatosClasificacion(FichaClasificacion entradaClasificaciones);
}
