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
import java.util.List;

import project.catalin.mybets.BuildConfig;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
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
public class HU17_ConsultarContactos {

    private GestorDataWebServices gestorData;

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void reInicializarGestor() throws IOException, JSONException {
        gestorData = new GestorDataWebServices();

        mockStatic(JsonWebServiceUtils.class);

        new project.catalin.mybets.datos.MyBetsMock()
                .setUpWebServiceLogic();
    }

    @Test
    public void sistemaUnUsuarioConUnAmigo_getContactosUsuario_ListaContactos() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        int idAmigo = gestorData.registrarUsuario(new Persona("amigo@validos.com", "test2", "test321", "+123456799"), "123456");

        gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));
        gestorData.añadirAmigo(idAmigo);

        //Acción
        List<Persona> listaAmigos = gestorData.getContactos();

        //Estado esperado
        assertTrue(listaAmigos.get(0).getEmail().equals("amigo@validos.com"));
    }

    @Test
    public void sistemaConUnUsuarioSinAmigos_getContactosUsuario_ListaVacia() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, JSONException, IOException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");
        gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));

        //Acción
        List<Persona> listaAmigos = gestorData.getContactos();

        //Estado esperado
        assertTrue(listaAmigos.size() == 0);
    }

    @Test
    public void sisteamConUnUsuarioNoIdentificado_getContactosUsuario_UsuarioNoIdentificadoException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test", "test123", "+123456789"), "123456");

        //Acción
        try {
            gestorData.getContactos();
            fail("Un usuario ha conseguido sus contactos sin estar identificado.");

        //Esperado
        } catch (UsuarioNoIdentificadoException e) {
        }
    }

    @After
    public void logOut() {
        SharedPreferencesUtils.logOut();
    }
}
