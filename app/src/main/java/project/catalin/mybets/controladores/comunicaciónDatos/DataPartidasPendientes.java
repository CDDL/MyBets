package project.catalin.mybets.controladores.comunicaciónDatos;

import java.util.List;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by Trabajo on 10/05/2016.
 */
public interface DataPartidasPendientes {
    List<Partida> getPartidasPendientes() throws ErrorInternoException, ErrorServerException;
}
