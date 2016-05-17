package project.catalin.mybets.controladores.comunicacionDatos;

import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 18/04/2016.
 */
public interface DataIdentificación {
    Persona validarIdentificación(LoginData loginData) throws ContraseñaVaciaException, EmailVacioException, EmailMalFormadoException, ErrorInternoException, ErrorServerException;
}
