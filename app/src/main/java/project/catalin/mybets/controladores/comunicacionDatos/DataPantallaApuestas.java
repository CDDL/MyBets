package project.catalin.mybets.controladores.comunicacionDatos;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 26/05/2016.
 */
public interface DataPantallaApuestas {
    int getIdFutbol() throws ErrorInternoException, ErrorServerException;
}
