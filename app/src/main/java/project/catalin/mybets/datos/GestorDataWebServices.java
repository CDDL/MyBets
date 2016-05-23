package project.catalin.mybets.datos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataDatosPropios;
import project.catalin.mybets.controladores.comunicacionDatos.DataGestionPartidas;
import project.catalin.mybets.controladores.comunicacionDatos.DataHistorialUsuario;
import project.catalin.mybets.controladores.comunicacionDatos.DataIdentificación;
import project.catalin.mybets.controladores.comunicacionDatos.DataJornada;
import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPasadas;
import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPendientes;
import project.catalin.mybets.controladores.comunicacionDatos.DataRegister;
import project.catalin.mybets.controladores.comunicacionDatos.DataContacts;
import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.comunicacionDatos.DataUsuario;
import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.dataObjects.EntradaHistorial;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.utils.EncryptionUtils;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.datos.utils.UserInputValidationUtils;

/**
 * Created by Demils on 23/03/2016.
 */
public class GestorDataWebServices implements DataIdentificación, DataRegister, DataContacts, DataPartidasPopulares, DataGestionPartidas, DataUsuario, DataJornada, DataHistorialUsuario, DataDatosPropios, DataPartidasPendientes, DataPartidasPasadas {

    public static final String URL_PETICIÓN_REGISTER = "http://mybetstest.cuatroochenta.com/services/response_login_error.json";
    public static final String URL_PETICIÓN_LOGIN = "http://www.mocky.io/v2/570f41e8250000661729c725";
    public static final String URL_PETICIÓN_AÑADIR_AMIGO = "http://mybetstest.cuatroochenta.com/services/responsfasdfe_login_ok.json";
    public static final String URL_PETICIÓN_GET_CONTACTOS = "http://mybetstest.cuatroochenta.com/services/response_amigos_ok.json";
    public static final String URL_PETICIÓN_NUEVA_PARTIDA = "http://mybetstest.cuatroochenta.com/services/response_amigos_ok.json";
    public static final String URL_PETICIÓN_GET_PARTIDAS_POPULARES = "http://mybetstest.cuatroochenta.com/services/response_partidas_populares.json";
    public static final String URL_PETICIÓN_CONSLTAR_PERFIL_USUARIO = "http://mybetstest.cuatroochenta.com/services/response_consultar_perfil.json";
    public static final String URL_PETICIÓN_GET_DATOS_JORNADA = "http://mybetstest.cuatroochenta.com/services/response_pedir_datos_jornada.json";
    public static final String URL_PETICIÓN_CONSLTAR_DATOS_PROPIOS = "http://mybetstest.cuatroochenta.com/services/response_consultar_datos_usuario_ok.json";
    public static final String URL_PETICIÓN_MODIFICAR_DATOS_PROPIOS = "http://mybetstest.cuatroochenta.com/services/response_consultar_datos_usuario_ok.json";
    public static final String URL_PETICIÓN_GET_PARTIDAS_PENDIENDES = "http://mybetstest.cuatroochenta.com/services/response_partidas_pendientes_ok.json";
    public static final String URL_PETICIÓN_GET_PARTIDAS_PASADAS = "http://mybetstest.cuatroochenta.com/services/response_partidas_pasadas_ok.json";
    public static final String URL_PETICIÓN_HISTORIAL_PARTIDA = "http://mybetstest.cuatroochenta.com/services/response_historial.json";
    public static final String URL_PETICIÓN_ACTUALIZAR_NOMBRE_PARTIDA = "";
    public static final String URL_PETICIÓN_RECHAZAR_PARTIDA = "";
    public static final String URL_PETICIÓN_GET_CLASIFICACION_PARTIDA = "http://mybetstest.cuatroochenta.com/services/response_consultar_clasificacion.json";
    public static final String URL_PETICIÓN_GET_LISTA_CATEGORIAS = "http://mybetstest.cuatroochenta.com/services/response_list_categorias.json";
    public static final String URL_PETICIÓN_GET_DATOS_SUBCATEGORIA = "http://mybetstest.cuatroochenta.com/services/response_get_data_subcategoria.json";
    public static final String URL_PETICIÓN_GET_LISTA_SUBCATEGORIAS = "http://mybetstest.cuatroochenta.com/services/response_consultar_partidas_categoria.json";
    public static final String URL_PETICIÓN_GET_PARTIDAS_HOY = URL_PETICIÓN_GET_PARTIDAS_POPULARES;
    public static final String URL_PETICIÓN_GET_DATOS_MURO = "http://mybetstest.cuatroochenta.com/services/response_consultar_muro.json";
    public static final String URL_PETICIÓN_GET_AMIGOS_INVITABLES = "http://mybetstest.cuatroochenta.com/services/response_amigos_invitables.json";

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
            if(dataRespuesta.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(dataRespuesta.getString("message"));
            SharedPreferencesUtils.saveLogin(dataRequest);
            return JsonParserUtils.jsonToPersona(dataRespuesta);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new ErrorInternoException();
        }
    }

    public void añadirAmigo(int idAmigo) throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException {
        try {
            JSONObject loginData =  SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idamigo",idAmigo);
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_AÑADIR_AMIGO, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public List<Persona> getContactos() throws UsuarioNoIdentificadoException, ErrorInternoException, ErrorServerException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_CONTACTOS, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPersonaList(resultData);
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public void crearPartida(int idJuego, List<Persona> contactos) throws ErrorServerException, ErrorInternoException {
        try{
            JSONObject requestData = SharedPreferencesUtils.getLoginJsonCopy();
            requestData.getJSONObject("request").put("idjuego", idJuego);
            requestData.getJSONObject("request").put("idamigos", JsonParserUtils.contactsToJsonArray(contactos));
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_NUEVA_PARTIDA, requestData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public List<Partida> getPartidasPendientes() throws ErrorInternoException, ErrorServerException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_PARTIDAS_PENDIENDES, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPartidasList(resultData);
        } catch (ParseException | IOException | JSONException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public void actualizarNombrePartida(int idPartida, String nuevoNombre) throws ErrorInternoException, ErrorServerException {

    }

    @Override
    public void rechazarPartida(int mIdPartida) throws ErrorInternoException, ErrorServerException {

    }

    @Override
    public List<Partida> getPartidasPopulares() throws ErrorInternoException, ErrorServerException {
        try{
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición((URL_PETICIÓN_GET_PARTIDAS_POPULARES), loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPartidasList(resultData);
        } catch (ParseException | IOException | JSONException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public List<Partido> getDatosJornada(int idPartida) throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idpartida", idPartida);
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_DATOS_JORNADA, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPartidosList(resultData);
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public List<EntradaHistorial> getHistorialUsuario(int idUsuario) throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idamigo", idUsuario);
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_CONSLTAR_PERFIL_USUARIO, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToEntradasHistorialList(resultData);
        } catch (JSONException | IOException | ParseException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public Persona getDatosUsuario(Integer idAmigo) throws ErrorInternoException, ErrorServerException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idamigo", idAmigo);
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_CONSLTAR_PERFIL_USUARIO, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToUserData(resultData);
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public Persona getDatosPropios() throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_CONSLTAR_DATOS_PROPIOS, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPersona(resultData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            throw new ErrorInternoException();
        }
    }

    @Override
    public void guardarEdicionDatos(Persona persona) throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("username", persona.getUsername());
            loginData.getJSONObject("request").put("name", persona.getNombre());
            loginData.getJSONObject("request").put("image", persona.getImagen());
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_MODIFICAR_DATOS_PROPIOS, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
        } catch (JSONException | IOException e) {
            throw new ErrorInternoException();
        }
    }

    @Override
    public List<Partida> getPartidasPasadas() throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_PARTIDAS_PASADAS, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToPartidasList(resultData);
        } catch (JSONException | IOException | ParseException e) {
            throw new ErrorInternoException();
        }
    }
}
