package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 03/05/2016.
 */
public interface DataGestionPartidas {
    void crearPartida(int idJuego, List<Persona> listaOponentes) throws ErrorServerException, ErrorInternoException;
}
