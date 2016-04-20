package project.catalin.mybets.datos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;

import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.controladores.iniciarSesion.comunicaciónDatos.DataIdentificación;
import project.catalin.mybets.controladores.iniciarSesion.comunicaciónDatos.DataRegister;
import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónDatos.DataContacts;
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
import project.catalin.mybets.datos.objetosData.Partida;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.datos.utils.EncryptionUtils;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.datos.utils.UserInputValidationUtils;

/**
 * Created by Demils on 23/03/2016.
 */
public class GestorDataWebServices implements DataIdentificación, DataRegister, DataContacts {

    public static final String URL_PETICIÓN_REGISTER = "http://mybetstest.cuatroochenta.com/services/response_login_error.json";
    public static final String URL_PETICIÓN_LOGIN = "http://www.mocky.io/v2/570f41e8250000661729c725";
    public static final String URL_PETICIÓN_AÑADIR_AMIGO = "http://mybetstest.cuatroochenta.com/services/responsfasdfe_login_ok.json";
    public static final String URL_PETICIÓN_GET_CONTACTOS = "http://mybetstest.cuatroochenta.com/services/response_amigos_ok.json";
    public static final String URL_PETICIÓN_NUEVA_PARTIDA = "";
    public static final String URL_PETICIÓN_GET_PARTIDAS_PENDIENDES = "";

    @Override
    public int registrarUsuario(Persona dataUsuario, String password) throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, EmailVacioException, NombreVacioException, ErrorInternoException, ErrorServerException {
        UserInputValidationUtils.validarEmail(dataUsuario.getEmail());
        UserInputValidationUtils.validarNombre(dataUsuario.getNombre());
        UserInputValidationUtils.validarUsername(dataUsuario.getTelefono());
        UserInputValidationUtils.validarTelefono(dataUsuario.getTelefono());

        try {

            JSONObject dataRequest = JsonParserUtils.registerToJson(dataUsuario, EncryptionUtils.toMD5(password));
            JSONObject dataRespuesta = JsonWebServiceUtils.petición(URL_PETICIÓN_REGISTER, dataRequest);
            if(dataRespuesta.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR) throw new ErrorServerException(dataRespuesta.getString("message"));
            return dataRespuesta.getJSONObject("data").getInt("id");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new ErrorInternoException();
        }
    }

    @Override
    public Persona validarIdentificación(LoginData dataLogin) throws EmailVacioException, EmailMalFormadoException, ContraseñaVaciaException, ErrorInternoException, ErrorServerException {
        UserInputValidationUtils.validarEmail(dataLogin.getEmail());
        UserInputValidationUtils.validarContraseña(dataLogin.getPassword());


        try {
            JSONObject dataRequest = JsonParserUtils.loginToJson(dataLogin);
            JSONObject dataRespuesta = JsonWebServiceUtils.petición(URL_PETICIÓN_LOGIN, dataRequest);
            if(dataRespuesta.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR) throw new ErrorServerException(dataRespuesta.getString("message"));
            SharedPreferencesUtils.saveLogin(dataRequest);
            return JsonParserUtils.jsonToPersona(dataRespuesta);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new ErrorInternoException();
        }
    }

    public void añadirAmigo(int idAmigo) throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException {
        comprobarIdentificado();

        try {
            JSONObject loginData =  SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idamigo",idAmigo);
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_AÑADIR_AMIGO, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR) throw new ErrorServerException(resultData.getString("message"));
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public List<Persona> getContactos() throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException {
        comprobarIdentificado();

        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_CONTACTOS, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR) throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPersonaList(resultData);
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    public void crearPartida(int idJuego, List<Integer> contactos) throws UsuarioNoIdentificadoException, ErrorServerException, ErrorInternoException {
        comprobarIdentificado();

        try{
            JSONObject requestData = SharedPreferencesUtils.getLoginJsonCopy();
            requestData.getJSONObject("request").put("idjuego", idJuego);
            requestData.getJSONObject("request").put("idamigos", JsonParserUtils.contactsToJsonArray(contactos));
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_NUEVA_PARTIDA, requestData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR) throw new ErrorServerException(resultData.getString("message"));
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    private void comprobarIdentificado() throws UsuarioNoIdentificadoException {
        if (!SharedPreferencesUtils.isLogged()) throw new UsuarioNoIdentificadoException();
    }

    public List<Partida> getPartidasPendientes() throws UsuarioNoIdentificadoException, ErrorInternoException {
        comprobarIdentificado();

        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_PARTIDAS_PENDIENDES, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR) throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPartidasList(resultData);
        } catch (JSONException | IOException | ErrorServerException e) {
            throw new ErrorInternoException();
        }
    }
}
