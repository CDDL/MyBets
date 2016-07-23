package project.catalin.mybets.controladores.comunicacionDatos;

import project.catalin.mybets.datos.dataObjects.Apuesta;
import project.catalin.mybets.datos.dataObjects.FichaApuestas;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 26/05/2016.
 */
public interface DataApuesta {
    void enviarApuesta(FichaApuestas fichaApuestas) throws ErrorInternoException, ErrorServerException;

    void aceptarPartida(int idPartida) throws ErrorInternoException, ErrorServerException;
}
