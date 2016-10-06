package project.catalin.mybets.controladores.comunicacionDatos;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 05/10/2016.
 */
public interface DataPuntos {
    int obtenerMisPuntos() throws ErrorInternoException, ErrorServerException;
}
