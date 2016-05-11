package project.catalin.mybets.controladores.comunicaciónDatos;

import java.util.List;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Partido;

/**
 * Created by Trabajo on 05/05/2016.
 */
public interface DataJornada {
    List<Partido> getDatosJornada(int idPartida) throws ErrorServerException, ErrorInternoException;

}
