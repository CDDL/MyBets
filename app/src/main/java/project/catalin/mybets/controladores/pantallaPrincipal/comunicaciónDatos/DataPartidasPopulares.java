package project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónDatos;

import java.util.List;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.objetosData.Partida;

/**
 * Created by Trabajo on 28/04/2016.
 */
public interface DataPartidasPopulares {
    List<Partida> getPartidasPopulares() throws ErrorInternoException, ErrorServerException;
}
