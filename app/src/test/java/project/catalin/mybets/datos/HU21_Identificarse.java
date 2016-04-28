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
import project.catalin.mybets.datos.excepciones.UsuarioVacioException;
import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Demils on 22/03/2016.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonWebServiceUtils.class, SharedPreferencesUtils.class})
public class HU21_Identificarse {

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
    public void sistemaConUnUsuario_identificarUsuarioContraseñaNoCoincide_usuarioNoIdentificado() throws EmailMalFormadoException, UsuarioRepetidoException, IOException, JSONException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ContraseñaVaciaException, UsuarioVacioException, ErrorServerException {
        //Estado inicial
        mMyBetsMock.sistemaConUnUsuarioRegistrado();

        //Acción
        try {
            mGestorData.validarIdentificación(new LoginData("datos@validos.com", "123"));
            fail("La persona se ha identificado con datos no válidos.");

        //Estado esperado
        }catch (ErrorServerException e){}

    }

    @Test
    public void sistemaSinUsuarios_identificarUsuarioCamposVacios_EmailVacíoException() throws IOException, JSONException, EmailVacioException, ContraseñaVaciaException, EmailMalFormadoException, ErrorInternoException, ErrorServerException {
        //Estado inicial

        //Acción
        try {
            mGestorData.validarIdentificación(new LoginData("",""));
            fail("Se ha intentado validar la identificación de un campo vacío.");

        //Estado esperado
        } catch (EmailVacioException e){}

    }

    @Test

    public void sistemaConUnUsuario_identificarUsuarioCoincideContraseña_usuarioIdentificado() throws EmailMalFormadoException, UsuarioRepetidoException, IOException, JSONException, TelefonoMalFormadoException, NombreVacioException, EmailVacioException, ErrorInternoException, ErrorServerException, ContraseñaVaciaException {
        //Estado inicial
        mMyBetsMock.sistemaConUnUsuarioRegistrado();

        //Acción
        Persona usuarioIdentificado = mGestorData.validarIdentificación(new LoginData("datos@validos.com", "123456"));

        //Estado esperado
        assertTrue(usuarioIdentificado.getEmail().equals("datos@validos.com"));
    }
}
