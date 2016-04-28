package project.catalin.mybets.datos;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.jsons.JsonCreatorUtils;
import project.catalin.mybets.datos.jsons.JsonObjectComparator;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JsonWebServiceUtils.class)
public class HU20_RegistrarUsuario {

    private GestorDataWebServices mGestorData;
    private MyBetsMock mMyBetsMock;


    @Before
    public void reInicializarGestor() throws IOException, JSONException {
        mGestorData = new GestorDataWebServices();
        mMyBetsMock = new MyBetsMock();

        mockStatic(JsonWebServiceUtils.class);

        mMyBetsMock.habilitarRegistro();
    }


    @Test
    public void sistemaSinUsuarios_registrarUsuarioDatosValidos_usuarioRegistradoEnElSistema() throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException, ContraseñaVaciaException, JSONException, IOException {
        //Estado inicial

        //Acción
        int idUsuario = mGestorData.registrarUsuario(new Persona("datos@validos.com", "test","test123","+123456789"), "123456");


        //Estado esperado
        verifyStatic();
        JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_REGISTER), argThat(new JsonObjectComparator(JsonCreatorUtils.createRequest_Registro_DatosValidos())));
    }

    @Test
    public void sistemaSinUsuarios_registrarUsuarioEmailIncorrecto_EmailMalFormadoException() throws UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException {
        //Estado inicial

        //Acción
        try {
            mGestorData.registrarUsuario(new Persona("emailnovalido.com", "test","test123","+123456789"), "123456");
            fail("Se ha registrado un usuario con un email mal formateado.");

        //Estado esperado
        }catch (EmailMalFormadoException emailMalFormadoException){}
    }

    @Test
    public void sistemaConUnUsuario_registrarUsuarioEmailYaExistente_UsuarioRepetidoException() throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException {
        //Estado inicial
        mGestorData.registrarUsuario(new Persona("datos@validos.com", "test","test123","+123456789"), "123456");

        //Acción
        try {
            mGestorData.registrarUsuario(new Persona("datos@validos.com", "test","test123","+123456789"), "123456");
            fail("Se ha registrado un usuario repetido.");

        //Estado esperado
        } catch (ErrorServerException e){}
    }

    @Test
    public void sistemaSinUsuarios_registrarUsuarioEmailVacío_EmailVacioException() throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException {
        //Estado inicial

        //Acción
        try {
            mGestorData.registrarUsuario(new Persona("", "test","test123","+123456789"), "123456");
            fail("Se ha registrado un usuario con el email vacío.");

        //Estado esperado
        } catch (EmailVacioException e){}
    }
}
