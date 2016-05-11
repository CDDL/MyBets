package project.catalin.mybets.controladores.comunicaciónDatos;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 03/05/2016.
 */
public interface DataUsuario {

    Persona getDatosUsuario(Integer integer) throws ErrorInternoException, ErrorServerException;
}
