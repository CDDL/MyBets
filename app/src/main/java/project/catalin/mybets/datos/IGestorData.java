package project.catalin.mybets.datos;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataIdentificación;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Demils on 22/03/2016.
 */
public interface IGestorData extends DataIdentificación {


    int registrarUsuario(Persona dataUsuario, String password) throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, EmailVacioException, NombreVacioException, ErrorInternoException, ErrorServerException;

    @Override
    Persona validarIdentificación(LoginData dataLogin) throws ErrorInternoException, ErrorServerException;

    void añadirAmigo(int idAmigo) throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException;

    List<Persona> getContactos() throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException;

}
