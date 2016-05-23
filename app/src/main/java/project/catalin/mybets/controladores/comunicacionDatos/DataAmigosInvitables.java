package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Contacto;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 23/05/2016.
 */
public interface DataAmigosInvitables {
    List<Persona> getListaAmigosInvitables(List<Contacto> contactos) throws ErrorInternoException, ErrorServerException;
}
