package project.catalin.mybets.controladores.comunicacionDatos;

import project.catalin.mybets.datos.dataObjects.FichaClasificacion;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 16/05/2016.
 */
public interface DataClasificacionPartida {
    FichaClasificacion getClasificacion(int idPartida) throws ErrorInternoException, ErrorServerException;
}
