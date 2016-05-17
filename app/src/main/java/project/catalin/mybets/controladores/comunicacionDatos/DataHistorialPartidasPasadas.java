package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.FichaHistorial;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 12/05/2016.
 */
public interface DataHistorialPartidasPasadas {
    List<FichaHistorial> getHistorialDePartidaId(int idPartida) throws ErrorInternoException, ErrorServerException;
}
