package project.catalin.mybets.controladores.comunicacionDatos;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 09/05/2016.
 */
public interface DataDatosPropios {
    Persona getDatosPropios() throws ErrorServerException, ErrorInternoException;

    void guardarEdicionDatos(Persona persona) throws ErrorServerException, ErrorInternoException;
}
