package project.catalin.mybets.datos;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import project.catalin.mybets.datos.jsons.JsonObjectComparator;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_Login_Invalido;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_Login_Valido;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_NuevaPartida_AmigosNoEnContactos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_NuevaPartida_ConAmigos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_NuevaPartida_PartidaInexistente;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_NuevaPartida_SinAmigos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_Registro_DatosValidos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_Registro_DatosValidosAmigo;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_UsuarioIdentificado;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_ConsultarContactos_SinContactos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_ConsultarContactos_UnContacto;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_Login_FailDatosIncorrectos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_Login_Ok;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_NoIdentificado;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_NuevaPartida_FailNoEnContactos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_NuevaPartida_FailPartidaInexistente;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_NuevaPartida_FailSinAmigos;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_NuevaPartida_Ok;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_NuevoAmigo_Ok;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_Registro_AmigoOk;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_Registro_FailDatosYaExistentes;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_Registro_Ok;

/**
 * Created by Trabajo on 12/04/2016.
 */
public class MyBetsMock {

    private Context mockedContext;
    private SharedPreferences mockedSharedPrefs;
    private SharedPreferences.Editor mockedEditor;


    public MyBetsMock setUpWebService() throws IOException, JSONException {
//        peticiónLoginValido_respuestaLoginOk();
//        peticiónLoginInvalido_respuestaLoginError();
//        peticiónRegistroUsuario_respuestaRegistrado_respuestaRepetido();
        return this;
    }

    public MyBetsMock setUpSharedPrefs() {
//        mockedContext = Mockito.mock(Context.class);
//        mockedSharedPrefs = Mockito.mock(SharedPreferences.class);
//        mockedEditor = Mockito.mock(SharedPreferences.Editor.class);
//
//        when(ContextCreator.getAppContext()).thenReturn(mockedContext);
//
//        when(mockedContext.getSharedPreferences(anyString(),anyInt())).thenReturn(mockedSharedPrefs);
//        when(mockedSharedPrefs.edit()).thenReturn(mockedEditor);
//
//        sharedPrefs_valoresBase();
//        sharedPrefs_pasarAIdentificado();

        return this;
    }

    public void sharedPrefs_valoresBase() {
        when(mockedSharedPrefs.getBoolean(eq("Logged"), anyBoolean())).thenReturn(false);
        when(mockedSharedPrefs.getString(eq("JsonLogin"), anyString())).thenReturn("");
    }

    public void sharedPrefs_pasarAIdentificado() {
        when(mockedEditor.putString(eq("JsonLogin"), anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                when(mockedSharedPrefs.getString("JsonLogin", "")).thenReturn(createRequest_Login_Valido().toString());
                return null;
            }
        });

        when(mockedEditor.putBoolean("Logged", true)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                when(mockedSharedPrefs.getBoolean("Logged", false)).thenReturn(true);
                return null;
            }
        });

        when(mockedEditor.putBoolean("Logged", false)).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                when(mockedSharedPrefs.getBoolean("Logged", false)).thenReturn(false);
                return null;
            }
        });
    }

    public MyBetsMock setUpWebServiceLogic() throws IOException, JSONException {
//        peitciónAñadirUsuario_respuestaUsuarioNoIdentificado();
//        peticiónGetContactos_respuestaUsuarioNoIdentificado();
//        peticiónLoginInvalido_respuestaLoginError();
//        peticiónRegistroAmigo_respuestaRegistrado_respuestaRepetido();
//        peticiónRegistroUsuario_respuestaRegistrado_respuestaRepetido();
//
        return this;
    }

    public void peticiónLoginInvalido_respuestaLoginError() throws JSONException, IOException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_LOGIN), argThat(new JsonObjectComparator(createRequest_Login_Invalido())))).thenReturn(createResponse_Login_FailDatosIncorrectos());
    }

    public void peticiónRegistroUsuario_respuestaRegistrado_respuestaRepetido() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_REGISTER), argThat(new JsonObjectComparator(createRequest_Registro_DatosValidos())))).thenReturn(createResponse_Registro_Ok()).thenReturn(createResponse_Registro_FailDatosYaExistentes());
    }

    public void peticiónLoginValido_respuestaLoginOk() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_LOGIN), argThat(new JsonObjectComparator(createRequest_Login_Valido())))).thenReturn(createResponse_Login_Ok());
    }

    public void peticiónCrearPartidaNoExistente_respuestaError() throws JSONException, IOException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_NUEVA_PARTIDA), argThat(new JsonObjectComparator(createRequest_NuevaPartida_PartidaInexistente())))).thenReturn(createResponse_NuevaPartida_FailPartidaInexistente());
    }


    public void peticiónCrearPartidaExistente_AmigosNoEnContactos_respuestaError() throws JSONException, IOException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_NUEVA_PARTIDA), argThat(new JsonObjectComparator(createRequest_NuevaPartida_AmigosNoEnContactos())))).thenReturn(createResponse_NuevaPartida_FailNoEnContactos());
    }

    public void peticiónCrearPartidaExistente_SinAmigos_respuestaError() throws JSONException, IOException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_NUEVA_PARTIDA), argThat(new JsonObjectComparator(createRequest_NuevaPartida_SinAmigos())))).thenReturn(createResponse_NuevaPartida_FailSinAmigos());
    }

    public void peticiónCrearPartidaExistente_ConAmigos_respuestaOk() throws JSONException, IOException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_NUEVA_PARTIDA), argThat(new JsonObjectComparator(createRequest_NuevaPartida_ConAmigos())))).thenReturn(createResponse_NuevaPartida_Ok());
    }

    public void peticiónAñadirAmigo_respuestaOk_añadirUsuarioContactos() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_AÑADIR_AMIGO), (JSONObject) anyObject())).thenAnswer(new Answer<JSONObject>() {
            @Override
            public JSONObject answer(InvocationOnMock invocation) throws Throwable {
                peticiónGetContactos_respuestaListaUnContacto();
                return createResponse_NuevoAmigo_Ok();
            }
        });
    }

    public void peticiónGetContactos_respuestaListaUnContacto() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_GET_CONTACTOS), argThat(new JsonObjectComparator(createRequest_UsuarioIdentificado())))).thenReturn(createResponse_ConsultarContactos_UnContacto());
    }

    public void peticiónGetContactos_respuestaListaVacía() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_GET_CONTACTOS), argThat(new JsonObjectComparator(createRequest_UsuarioIdentificado())))).thenReturn(createResponse_ConsultarContactos_SinContactos());
    }

    public void peticiónRegistroAmigo_respuestaRegistrado_respuestaRepetido() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_REGISTER), argThat(new JsonObjectComparator(createRequest_Registro_DatosValidosAmigo())))).thenReturn(createResponse_Registro_AmigoOk());
    }

    public void peticiónGetContactos_respuestaUsuarioNoIdentificado() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_GET_CONTACTOS), (JSONObject) anyObject())).thenReturn(createResponse_NoIdentificado());
    }

    public void peitciónAñadirUsuario_respuestaUsuarioNoIdentificado() throws IOException, JSONException {
        when(JsonWebServiceUtils.petición(eq(GestorDataWebServices.URL_PETICIÓN_AÑADIR_AMIGO), (JSONObject) anyObject())).thenReturn(createResponse_NoIdentificado());
    }

    public void sharedPrefs_estaIdentificado() throws JSONException {
        when(SharedPreferencesUtils.isLogged()).thenReturn(true);
        when(SharedPreferencesUtils.getLoginJsonCopy()).thenReturn(createRequest_Login_Valido());
    }

    public void sharedPrefs_noEstaIdentificado() {
        when(SharedPreferencesUtils.isLogged()).thenReturn(false);
    }

    public void sistemaConUnUsuarioRegistrado() throws IOException, JSONException {
        peticiónLoginValido_respuestaLoginOk();
        peticiónLoginInvalido_respuestaLoginError();
    }

    public void habilitarRegistro() throws IOException, JSONException {
        peticiónRegistroUsuario_respuestaRegistrado_respuestaRepetido();
    }

    public void sistemUnUsuarioConAmigo() throws IOException, JSONException {
        sharedPrefs_estaIdentificado();
        peticiónGetContactos_respuestaListaUnContacto();
    }

    public void sistemaUnUsuarioSinAmigos() throws IOException, JSONException {
        sharedPrefs_estaIdentificado();
        peticiónGetContactos_respuestaListaVacía();
    }

    public void sistemaUsuarioNoIdentificado() {
        sharedPrefs_noEstaIdentificado();
    }

    public void partidaInxistente() throws IOException, JSONException {
        peticiónCrearPartidaNoExistente_respuestaError();
    }

    public void partidaExistente() throws IOException, JSONException {
        peticiónCrearPartidaExistente_AmigosNoEnContactos_respuestaError();
        peticiónCrearPartidaExistente_ConAmigos_respuestaOk();
        peticiónCrearPartidaExistente_SinAmigos_respuestaError();
    }

    public void usuarioIdentificado() throws JSONException {
        sharedPrefs_estaIdentificado();
    }

    public void partidaExistentePeticiónNoAmigos() throws IOException, JSONException {
        peticiónCrearPartidaExistente_AmigosNoEnContactos_respuestaError();
    }
}
