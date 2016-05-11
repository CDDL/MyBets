package project.catalin.mybets.datos;


import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.List;

import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Catalin on 01/04/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JsonWebServiceUtils.class, SharedPreferencesUtils.class})
public class HU17_ConsultarContactos {

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
    public void sistemaUnUsuarioConUnAmigo_getContactosUsuario_ListaContactos() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, IOException, JSONException {
        //Estado inicial
        mMyBetsMock.sistemUnUsuarioConAmigo();

        //Acción
        List<Persona> listaAmigos = mGestorData.getContactos();

        //Estado esperado
        assertTrue(listaAmigos.get(0).getEmail().equals("amigo@validos.com"));
    }

    @Test
    public void sistemaConUnUsuarioSinAmigos_getContactosUsuario_ListaVacia() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException, ContraseñaVaciaException, UsuarioNoIdentificadoException, JSONException, IOException {
        //Estado inicial
        mMyBetsMock.sistemaUnUsuarioSinAmigos();

        //Acción
        List<Persona> listaAmigos = mGestorData.getContactos();

        //Estado esperado
        assertTrue(listaAmigos.size() == 0);
    }

    @Test
    public void sisteamConUnUsuarioNoIdentificado_getContactosUsuario_UsuarioNoIdentificadoException() throws NombreVacioException, ErrorServerException, UsuarioRepetidoException, ErrorInternoException, EmailMalFormadoException, TelefonoMalFormadoException, EmailVacioException {
        //Estado inicial
        mMyBetsMock.sistemaUsuarioNoIdentificado();

        //Acción
        try {
            mGestorData.getContactos();
            fail("Un usuario ha conseguido sus contactos sin estar identificado.");

        //Esperado
        } catch (UsuarioNoIdentificadoException e) {}
    }
}
