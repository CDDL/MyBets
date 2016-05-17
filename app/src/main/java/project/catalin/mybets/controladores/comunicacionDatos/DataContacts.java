package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 18/04/2016.
 */
public interface DataContacts {
    List<Persona> getContactos() throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException;
}
