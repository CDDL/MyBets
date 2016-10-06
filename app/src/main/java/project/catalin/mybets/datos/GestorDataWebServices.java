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
import project.catalin.mybets.datos.dataObjects.FichaJornada;
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

    private static final String URL_BASE = "http://mybetstest.cuatroochenta.com";
    public static final String URL_PETICIÓN_REGISTER = URL_BASE + "/services/mybets_register";
    public static final String URL_PETICIÓN_LOGIN = URL_BASE + "/services/mybets_login";
    public static final String URL_PETICIÓN_AÑADIR_AMIGO = "http://mybetstest.cuatroochenta.com/services/responsfasdfe_login_ok.json";
    public static final String URL_PETICIÓN_GET_CONTACTOS = URL_BASE + "/services/mybets_consult_contacts";
    public static final String URL_PETICIÓN_NUEVA_PARTIDA = URL_BASE+ "/services/mybets_crear_partida";
    public static final String URL_PETICIÓN_GET_PARTIDAS_POPULARES = URL_BASE + "/services/mybets_ultimas_partidas";
    public static final String URL_PETICIÓN_CONSLTAR_PERFIL_USUARIO = URL_BASE + "/services/mybets_perfil_amigo";
    public static final String URL_PETICIÓN_GET_DATOS_JORNADA = URL_BASE + "/services/mybets_pedir_datos_jornada";
    public static final String URL_PETICIÓN_CONSLTAR_DATOS_PROPIOS = URL_BASE + "/services/mybets_consultar_datos_usuario";
    public static final String URL_PETICIÓN_MODIFICAR_DATOS_PROPIOS = URL_BASE + "/services/mybets_modificar_datos_usuario";
    public static final String URL_PETICIÓN_GET_PARTIDAS_PENDIENDES = URL_BASE + "/services/mybets_partidas_pendientes";
    public static final String URL_PETICIÓN_GET_PARTIDAS_PASADAS = URL_BASE + "/services/mybets_partidas_pasadas";
    public static final String URL_PETICIÓN_HISTORIAL_PARTIDA =  URL_BASE + "/services/mybets_apuestas_realizadas";
    public static final String URL_PETICIÓN_ACTUALIZAR_NOMBRE_PARTIDA = URL_BASE+"/services/mybets_cambiar_nombre_partida";
    public static final String URL_PETICIÓN_RECHAZAR_PARTIDA =URL_BASE + "/services/mybets_rechazar_partida";
    public static final String URL_PETICIÓN_GET_CLASIFICACION_PARTIDA = URL_BASE + "/services/mybets_clasificacion";
    public static final String URL_PETICIÓN_GET_LISTA_CATEGORIAS = URL_BASE + "/services/mybets_consult_categories";
    public static final String URL_PETICIÓN_GET_DATOS_SUBCATEGORIA = URL_BASE + "/services/mybets_partidas_subcategoria";
    public static final String URL_PETICIÓN_GET_LISTA_SUBCATEGORIAS = URL_BASE + "/services/mybets_partidas_categoria";
    public static final String URL_PETICIÓN_GET_PARTIDAS_HOY = URL_PETICIÓN_GET_PARTIDAS_POPULARES;
    public static final String URL_PETICIÓN_GET_DATOS_MURO = URL_BASE + "/services/mybets_consultar_muro";
    public static final String URL_PETICIÓN_GET_AMIGOS_INVITABLES = "http://mybetstest.cuatroochenta.com/services/response_amigos_invitables.json";
    public static final String URL_PETICIÓN_APOSTAR = URL_BASE + "/services/mybets_realizar_apuesta";
    public static final String URL_PETICIÓN_ACEPTAR_PARTIDA = URL_BASE + "/services/mybets_aceptar_partida";
    public static final String URL_PETICIÓN_MIS_PUNTOS = URL_BASE + "/services/mybets_puntuacion";

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
    public Persona validarIdentificación(LoginData dataLogin) throws ErrorInternoException, ErrorServerException {


        try {
            JSONObject dataRequest = JsonParserUtils.loginToJson(dataLogin);
            JSONObject dataRespuesta = JsonWebServiceUtils.petición(URL_PETICIÓN_LOGIN, dataRequest);
            if(dataRespuesta.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(dataRespuesta.getString("message"));
            SharedPreferencesUtils.guardarJsonLogin(dataRequest);
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
            requestData.getJSONObject("request").put("idpartida", idJuego);
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
    public FichaJornada getDatosJornada(int idPartida) throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idpartida", idPartida);
            JSONObject resultData = JsonWebServiceUtils.petición(URL_PETICIÓN_GET_DATOS_JORNADA, loginData);
            if(resultData.getInt("code") == Constantes.RESPUESTA_WEBSERV_ERROR)
                throw new ErrorServerException(resultData.getString("message"));
            return JsonParserUtils.jsonToFichaJornada(resultData);
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
            if(!persona.getImagen().equals("")) loginData.getJSONObject("request").put("image", persona.getImagen());
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
