package project.catalin.mybets.datos;


import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.BuildConfig;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.JuegoInexistenteException;
import project.catalin.mybets.datos.excepciones.NoEstaEnContactosException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.excepciones.UsuarioSinContactosException;
import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.datos.shadows.ShadowSharedPreferences;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Catalin on 01/04/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows={ShadowSharedPreferences.class})
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "org.json.*" })
@PrepareForTest(JsonWebServiceUtils.class)
public class HU01_CrearPartida {

    private GestorDataWebServices gestorData;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void reInicializarGestor() throws IOException, JSONException {
        gestorData = new GestorDataWebServices();

        mockStatic(JsonWebServiceUtils.class);

        new MyBetsMock()
                .setUpWebServiceLogic();
    }

    @Test
    public void sistemaConUnUsuario_crearPartidaJuegoInexistente_JuegoInexistenteException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        int idAmigo = gestorData.registrarUsuario(new Persona("amigo@validos.com", "test2", "test321", "+123456799"), "123456");

        gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));
        gestorData.añadirAmigo(idAmigo);

        List<Integer> amigos = new LinkedList<>();
        amigos.add(idAmigo);

        //Acción
        try {
            gestorData.crearPartida(-1, amigos);
            fail("Se ha creado una partida de un juego inexistente");

        //Estado esperado
        } catch (ErrorServerException e){}
    }

    @Test
    public void sisitemaConUnUsuarioSinContactos_crearPartidaJuegoExistente_UsuarioSinContactosException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));

        //Acción
        try {
            gestorData.crearPartida(0, new LinkedList<Integer>());
            fail("Se ha creado una partida en solitario.");

        //Estado esperado
        } catch (ErrorServerException e){}
    }

    @Test
    public void sistemaConUnUsuario_crearPartidaJuegoSinIdentificación_UsuarioNoIdentificadoException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        int idAmigo = gestorData.registrarUsuario(new Persona("amigo@validos.com", "test2", "test321", "+123456799"), "123456");

        List<Integer> amigos = new LinkedList<>();
        amigos.add(idAmigo);

        //Acción
        try {
            gestorData.crearPartida(0, amigos);
            fail("Un usuario no identificado ha creado una partida.");

        //Estado esperado
        } catch (UsuarioNoIdentificadoException e){}
    }

    @Test
    public void sistemaConDosAmigosYUnJuego_crearPartida_partidaCreada() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        int idAmigo = gestorData.registrarUsuario(new Persona("amigo@validos.com", "test2", "test321", "+123456799"), "123456");

        gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));
        gestorData.añadirAmigo(idAmigo);

        List<Integer> amigos = new LinkedList<>();
        amigos.add(idAmigo);

        //Acción
        gestorData.crearPartida(0,amigos);

        //Estado esperado
        assertTrue(gestorData.getPartidasPendientes().size() == 1);
    }


    @Test
    public void sistemaConDosUsuariosNoAmigos_crearPartida_NoEstaEnContactosException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException {
        //Estado incial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        int idAmigo = gestorData.registrarUsuario(new Persona("amigo@validos.com", "test2", "test321", "+123456799"), "123456");

        gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));

        List<Integer> amigos = new LinkedList<>();
        amigos.add(idAmigo);

        //Acción
        try {
            gestorData.crearPartida(0, amigos);
            fail("Se ha retado a usuarios que no estan en contactos.");

        }catch (ErrorServerException e){}
    }

    @After
    public void logOut() {
        SharedPreferencesUtils.logOut();
    }
}
