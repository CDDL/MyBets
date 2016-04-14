package project.catalin.mybets.datos;

import org.json.JSONException;
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

import project.catalin.mybets.BuildConfig;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.datos.shadows.ShadowSharedPreferences;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows={ShadowSharedPreferences.class})
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "org.json.*" })
@PrepareForTest(JsonWebServiceUtils.class)
public class HU20_RegistrarUsuario {

    private IGestorData gestorData;

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
    public void sistemaSinUsuarios_registrarUsuarioDatosValidos_usuarioRegistradoEnElSistema() throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException, ContraseñaVaciaException, JSONException, IOException {
        //Estado inicial

        //Acción
        int idUsuario = gestorData.registrarUsuario(new Persona("datos@validos.com", "test","test123","+123456789"), "123456");


        //Estado esperado
        assertTrue(gestorData.validarIdentificación(new LoginData("datos@validos.com", "123456")).getNombre().equals("test"));
    }

    @Test
    public void sistemaSinUsuarios_registrarUsuarioEmailIncorrecto_EmailMalFormadoException() throws UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException {
        //Estado inicial

        //Acción
        try {
            gestorData.registrarUsuario(new Persona("emailnovalido.com", "test","test123","+123456789"), "123456");
            fail("Se ha registrado un usuario con un email mal formateado.");

        //Estado esperado
        }catch (EmailMalFormadoException emailMalFormadoException){}
    }

    @Test
    public void sistemaConUnUsuario_registrarUsuarioEmailYaExistente_UsuarioRepetidoException() throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException {
        //Estado inicial
        gestorData.registrarUsuario(new Persona("datos@validos.com", "test","test123","+123456789"), "123456");

        //Acción
        try {
            gestorData.registrarUsuario(new Persona("datos@validos.com", "test","test123","+123456789"), "123456");
            fail("Se ha registrado un usuario repetido.");

        //Estado esperado
        } catch (ErrorServerException e){}
    }

    @Test
    public void sistemaSinUsuarios_registrarUsuarioEmailVacío_EmailVacioException() throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException {
        //Estado inicial

        //Acción
        try {
            gestorData.registrarUsuario(new Persona("", "test","test123","+123456789"), "123456");
            fail("Se ha registrado un usuario con el email vacío.");

        //Estado esperado
        } catch (EmailVacioException e){}
    }
}
