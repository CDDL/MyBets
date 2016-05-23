package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 19/05/2016.
 */
public interface DataPartidasHoy {
    List<Partida> getPartidasHoy() throws ErrorInternoException, ErrorServerException;
}
