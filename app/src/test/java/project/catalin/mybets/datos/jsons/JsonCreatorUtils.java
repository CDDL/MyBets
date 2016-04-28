package project.catalin.mybets.datos.jsons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import project.catalin.mybets.datos.utils.EncryptionUtils;

/**
 * Created by Catalin on 08/04/2016.
 */
public class JsonCreatorUtils {



    public static JSONObject createRequest_Login_Valido() throws JSONException {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "datos@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123456"));

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonRequest);
        return jsonResult;
    }

    public static JSONObject createRequest_Login_Invalido() throws JSONException {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "datos@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123"));

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonRequest);

        return jsonResult;

    }

    public static JSONObject createResponse_Login_Ok() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("id", 0);
        jsonData.put("email", "datos@validos.com");
        jsonData.put("name", "test");
        jsonData.put("username", "test123");
        jsonData.put("phone", "+123456789");

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("data", jsonData);
        jsonResult.put("code", 0);
        jsonResult.put("message", "");

        return jsonResult;
    }

    public static JSONObject createResponse_Login_FailDatosIncorrectos() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 1);
        jsonResult.put("message", "El usuario y la contraseña no coinciden.");

        return jsonResult;
    }


    public static JSONObject createRequest_Registro_DatosValidos() throws JSONException {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "datos@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123456"));
        jsonRequest.put("name", "test");
        jsonRequest.put("username", "test123");
        jsonRequest.put("phone", "+123456789");

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonRequest);


        return jsonResult;
    }

    public static JSONObject createRequest_Registro_DatosValidosAmigo() throws JSONException {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "amigo@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123456"));
        jsonRequest.put("name", "test2");
        jsonRequest.put("username", "test321");
        jsonRequest.put("phone", "+123456799");

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonRequest);


        return jsonResult;
    }

    public static JSONObject createResponse_Registro_Ok() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("id", 0);
        jsonData.put("email", "datos@validos.com");
        jsonData.put("name", "test");
        jsonData.put("username", "test123");
        jsonData.put("phone", "+123456789");

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 0);
        jsonResult.put("message", "");
        jsonResult.put("data", jsonData);

        return jsonResult;
    }

    public static JSONObject createResponse_Registro_AmigoOk() throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("id", 1);
        jsonData.put("email", "amigo@validos.com");
        jsonData.put("name", "test2");
        jsonData.put("username", "test321");
        jsonData.put("phone", "+123456799");

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 0);
        jsonResult.put("message", "");
        jsonResult.put("data", jsonData);

        return jsonResult;
    }

    public static JSONObject createResponse_Registro_FailDatosYaExistentes() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 1);
        jsonResult.put("message", "El email ya existe en el sistema.");

        return jsonResult;
    }


    public static JSONObject createRequest_NuevoAmigo() throws JSONException {
        JSONObject requestData = new JSONObject();
        requestData.put("email", "datos@validos.com");
        requestData.put("password", EncryptionUtils.toMD5("123456"));
        requestData.put("idamigo", 1);

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", requestData);

        return jsonResult;
    }

    public static JSONObject createResponse_NuevoAmigo_Ok() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 0);
        jsonResult.put("message", "");


        return jsonResult;
    }


    public static JSONObject createResponse_ConsultarContactos_UnContacto() throws JSONException {
        JSONArray vectorJsonContactos = new JSONArray();

        JSONObject personaUno = new JSONObject();
        personaUno.put("id", 1);
        personaUno.put("username", "test321");
        personaUno.put("email", "amigo@validos.com");
        personaUno.put("phone", "+123456799");
        personaUno.put("name", "test2");
        personaUno.put("imagen", "http://imagenes.com/img.png");

        vectorJsonContactos.put(personaUno);

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("data", vectorJsonContactos);
        jsonResult.put("code", 0);
        jsonResult.put("message", "");

        return jsonResult;
    }

    public static JSONObject createResponse_ConsultarContactos_SinContactos() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("data", new JSONArray());
        jsonResult.put("code", 0);
        jsonResult.put("message", "");

        return jsonResult;
    }


    public static JSONObject createRequest_UsuarioIdentificado() throws JSONException {
        JSONObject requestData = new JSONObject();
        requestData.put("email", "datos@validos.com");
        requestData.put("password", EncryptionUtils.toMD5("123456"));

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", requestData);

        return jsonResult;
    }

    public static JSONObject createRequest_UsuarioNoIdentificado() throws JSONException {
        JSONObject requestData = new JSONObject();
        requestData.put("email", "no@identificado.com");
        requestData.put("password", EncryptionUtils.toMD5("123456"));

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", requestData);

        return jsonResult;
    }

    public static JSONObject createResponse_NoIdentificado() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 1);
        jsonResult.put("message", "El usuario no esta identificado.");

        return jsonResult;
    }

    public static JSONObject createRequest_NuevaPartida_PartidaInexistente() throws JSONException {
        JSONObject jsonResult = createRequest_Login_Valido();
        JSONObject requestData = jsonResult.getJSONObject("request");

        requestData.put("idjuego", -1);
        requestData.put("idamigos", new JSONArray(Collections.emptyList()));

        return jsonResult;
    }

    public static JSONObject createResponse_NuevaPartida_FailPartidaInexistente() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 1);
        jsonResult.put("message", "La partida no existe.");

        return jsonResult;
    }

    public static JSONObject createRequest_NuevaPartida_AmigosNoEnContactos() throws JSONException {
        JSONObject jsonRsult = createRequest_Login_Valido();
        jsonRsult.getJSONObject("request").put("idjuego", 0);
        jsonRsult.getJSONObject("request").put("idamigos", new JSONArray(Collections.singletonList(1)));

        return jsonRsult;
    }

    public static JSONObject createResponse_NuevaPartida_FailNoEnContactos() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 1);
        jsonResult.put("message", "Hay usuarios que intentas retar que no estan en tus contactos.");

        return jsonResult;
    }

    public static JSONObject createRequest_NuevaPartida_SinAmigos() throws JSONException {
        JSONObject jsonResult = createRequest_Login_Valido();
        jsonResult.getJSONObject("request").put("idjuego", 0);
        jsonResult.getJSONObject("request").put("idamigos", new JSONArray(Collections.emptyList()));

        return jsonResult;
    }

    public static JSONObject createResponse_NuevaPartida_FailSinAmigos() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 1);
        jsonResult.put("message", "No se puede crear una partida solitaria.");

        return jsonResult;
    }

    public static JSONObject createRequest_NuevaPartida_ConAmigos() throws JSONException {
        JSONObject jsonResult = createRequest_Login_Valido();
        jsonResult.getJSONObject("request").put("idjuego", 0);
        jsonResult.getJSONObject("request").put("idamigos", new JSONArray(Collections.singletonList(1)));

        return jsonResult;
    }

    public static JSONObject createResponse_NuevaPartida_Ok() throws JSONException {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("code", 0);
        jsonResult.put("message", "");

        return jsonResult;
    }
}
