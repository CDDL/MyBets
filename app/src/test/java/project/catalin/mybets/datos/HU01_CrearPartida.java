package project.catalin.mybets.datos;


import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.jsons.JsonCreatorUtils;
import project.catalin.mybets.datos.jsons.JsonObjectComparator;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by Catalin on 01/04/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonWebServiceUtils.class, SharedPreferencesUtils.class})
public class HU01_CrearPartida {

    private GestorDataWebServices mGestorData;
    private MyBetsMock mMyBetsMock;

    @Before
    public void reInicializarGestor() throws IOException, JSONException {
        mGestorData = new GestorDataWebServices();
        mMyBetsMock = new MyBetsMock();

        mockStatic(JsonWebServiceUtils.class);
        mockStatic(SharedPreferencesUtils.class);

    }

    @Test
    public void sistemaConUnUsuario_crearPartidaJuegoInexistente_JuegoInexistenteException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, IOException, JSONException {
        //Estado inicial
        mMyBetsMock.sistemaUnUsuarioSinAmigos();
        mMyBetsMock.partidaInxistente();

        //Acción
        try {
            mGestorData.crearPartida(-1, Collections.<Integer>emptyList());
            fail("Se ha creado una partida de un juego inexistente");

        //Estado esperado
        } catch (ErrorServerException e){}
    }

    @Test
    public void sisitemaConUnUsuarioSinContactos_crearPartidaJuegoExistente_UsuarioSinContactosException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, IOException, JSONException {
        //Estado inicial
        mMyBetsMock.sistemaUnUsuarioSinAmigos();
        mMyBetsMock.partidaExistente();

        //Acción
        try {
            mGestorData.crearPartida(0, new LinkedList<Integer>());
            fail("Se ha creado una partida en solitario.");

        //Estado esperado
        } catch (ErrorServerException e){}
    }

    @Test
    public void sistemaConUnUsuario_crearPartidaJuegoSinIdentificación_UsuarioNoIdentificadoException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException {
        //Estado inicial
        mMyBetsMock.sistemaUsuarioNoIdentificado();

        //Acción
        try {
            mGestorData.crearPartida(0, Collections.<Integer>emptyList());
            fail("Un usuario no identificado ha creado una partida.");

        //Estado esperado
        } catch (UsuarioNoIdentificadoException e){}
    }

    @Test
    public void sistemaConDosAmigosYUnJuego_crearPartida_partidaCreada() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, IOException, JSONException {
        //Estado inicial
        mMyBetsMock.partidaExistente();
        mMyBetsMock.sistemUnUsuarioConAmigo();

        //Acción
        mGestorData.crearPartida(0,Collections.singletonList(1));

        //Estado esperado
        verifyStatic();
        JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_NUEVA_PARTIDA), argThat(new JsonObjectComparator(JsonCreatorUtils.createRequest_NuevaPartida_ConAmigos())));
    }


    @Test
    public void sistemaConDosUsuariosNoAmigos_crearPartida_NoEstaEnContactosException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, IOException, JSONException {
        //Estado incial
        mMyBetsMock.partidaExistentePeticiónNoAmigos();
        mMyBetsMock.usuarioIdentificado();

        //Acción
        try {
            mGestorData.crearPartida(0, Collections.singletonList(1));
            fail("Se ha retado a usuarios que no estan en contactos.");

        }catch (ErrorServerException e){}
    }
}
