package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.FichaJornada;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Partido;

/**
 * Created by Trabajo on 05/05/2016.
 */
public interface DataJornada {
    FichaJornada getDatosJornada(int idPartida) throws ErrorServerException, ErrorInternoException;

}
