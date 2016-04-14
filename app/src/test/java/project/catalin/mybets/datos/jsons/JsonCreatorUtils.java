package project.catalin.mybets.datos.jsons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.catalin.mybets.datos.utils.EncryptionUtils;

/**
 * Created by Catalin on 08/04/2016.
 */
public class JsonCreatorUtils {
    private static JSONObject jsonRequestRegistroValido;
    private static JSONObject jsonRequestLoginInvalido;
    private static JSONObject jsonRequestLoginValido;
    private static JSONObject jsonRequestRegistroAmigoValido;
    private static JSONObject jsonRequestDataIdentificación;

    private static JSONObject jsonResponseRegistroSuccess;
    private static JSONObject jsonResponseRegistroFail_DatosRepetidos;
    private static JSONObject jsonResponseLoginSuccess;
    private static JSONObject jsonResponseLoginFail_DatosRepetidos;
    private static JSONObject jsonResponseNuevoAmigoOk;
    private static JSONObject jsonResponseConsultarContactosUnContacto;
    private static JSONObject jsonResponseRegistroAmigoSuccess;
    private static JSONObject jsonResponseConsultarContactosSinContactos;
    private static JSONObject jsonRequestNoIndentificado;
    private static JSONObject jsonResponseNoIdentificado;
    private static JSONObject jsonRequestNuevoAmigo;

    public static JSONObject createRequest_Login_Valido() throws JSONException {
        if(jsonRequestLoginValido != null) return jsonRequestLoginValido;


        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "datos@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123456"));

        jsonRequestLoginValido = new JSONObject();
        jsonRequestLoginValido.put("request", jsonRequest);
        return jsonRequestLoginValido;
    }

    public static JSONObject createRequest_Login_Invalido() throws JSONException {
        if(jsonRequestLoginInvalido != null) return jsonRequestLoginInvalido;

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "datos@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123"));

        jsonRequestLoginInvalido = new JSONObject();
        jsonRequestLoginInvalido.put("request", jsonRequest);

        return jsonRequestLoginInvalido;

    }

    public static JSONObject createResponse_Login_Ok() throws JSONException {
        if(jsonResponseLoginSuccess != null) return jsonResponseLoginSuccess;

        JSONObject jsonData = new JSONObject();
        jsonData.put("id", 0);
        jsonData.put("email", "datos@validos.com");
        jsonData.put("name", "test");
        jsonData.put("username", "test123");
        jsonData.put("phone", "+123456789");

        jsonResponseLoginSuccess = new JSONObject();
        jsonResponseLoginSuccess.put("data",jsonData);
        jsonResponseLoginSuccess.put("code", 0);
        jsonResponseLoginSuccess.put("message", "");

        return jsonResponseLoginSuccess;
    }

    public static JSONObject createResponse_Login_FailDatosIncorrectos() throws JSONException {
        if(jsonResponseLoginFail_DatosRepetidos != null) return jsonResponseLoginFail_DatosRepetidos;

        jsonResponseLoginFail_DatosRepetidos = new JSONObject();
        jsonResponseLoginFail_DatosRepetidos.put("code", 1);
        jsonResponseLoginFail_DatosRepetidos.put("message", "El usuario y la contraseña no coinciden.");

        return  jsonResponseLoginFail_DatosRepetidos;
    }



    public static JSONObject createRequest_Registro_DatosValidos() throws JSONException {
        if(jsonRequestRegistroValido != null) return jsonRequestRegistroValido;

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "datos@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123456"));
        jsonRequest.put("name", "test");
        jsonRequest.put("username", "test123");
        jsonRequest.put("phone", "+123456789");

        jsonRequestRegistroValido = new JSONObject();
        jsonRequestRegistroValido.put("request", jsonRequest);


        return jsonRequestRegistroValido;
    }

    public static JSONObject createRequest_Registro_DatosValidosAmigo() throws JSONException {
        if(jsonRequestRegistroAmigoValido != null) return jsonRequestRegistroAmigoValido;

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", "amigo@validos.com");
        jsonRequest.put("password", EncryptionUtils.toMD5("123456"));
        jsonRequest.put("name", "test2");
        jsonRequest.put("username", "test321");
        jsonRequest.put("phone", "+123456799");

        jsonRequestRegistroAmigoValido = new JSONObject();
        jsonRequestRegistroAmigoValido.put("request", jsonRequest);


        return jsonRequestRegistroAmigoValido;
    }

    public static JSONObject createResponse_Registro_Ok() throws JSONException {
        if(jsonResponseRegistroSuccess != null) return jsonResponseRegistroSuccess;

        JSONObject jsonData = new JSONObject();
        jsonData.put("id", 0);
        jsonData.put("email", "datos@validos.com");
        jsonData.put("name", "test");
        jsonData.put("username", "test123");
        jsonData.put("phone", "+123456789");

        jsonResponseRegistroSuccess = new JSONObject();
        jsonResponseRegistroSuccess.put("code", 0);
        jsonResponseRegistroSuccess.put("message", "");
        jsonResponseRegistroSuccess.put("data", jsonData);

        return jsonResponseRegistroSuccess;
    }

    public static JSONObject createResponse_Registro_AmigoOk() throws JSONException {
        if(jsonResponseRegistroAmigoSuccess != null) return jsonResponseRegistroAmigoSuccess;

        JSONObject jsonData = new JSONObject();
        jsonData.put("id", 1);
        jsonData.put("email", "amigo@validos.com");
        jsonData.put("name", "test2");
        jsonData.put("username", "test321");
        jsonData.put("phone", "+123456799");

        jsonResponseRegistroAmigoSuccess = new JSONObject();
        jsonResponseRegistroAmigoSuccess.put("code", 0);
        jsonResponseRegistroAmigoSuccess.put("message", "");
        jsonResponseRegistroAmigoSuccess.put("data", jsonData);

        return jsonResponseRegistroAmigoSuccess;
    }

    public static JSONObject createResponse_Registro_FailDatosYaExistentes() throws JSONException {
        if(jsonResponseRegistroFail_DatosRepetidos != null) return jsonResponseRegistroFail_DatosRepetidos;

        jsonResponseRegistroFail_DatosRepetidos = new JSONObject();
        jsonResponseRegistroFail_DatosRepetidos.put("code", 1);
        jsonResponseRegistroFail_DatosRepetidos.put("message", "El email ya existe en el sistema.");

        return jsonResponseRegistroFail_DatosRepetidos;
    }



    public static JSONObject createRequest_NuevoAmigo() throws JSONException {
        if(jsonRequestNuevoAmigo!= null) return jsonRequestDataIdentificación;

        JSONObject requestData = new JSONObject();
        requestData.put("email", "datos@validos.com");
        requestData.put("password", EncryptionUtils.toMD5("123456"));
        requestData.put("idamigo", 1);

        jsonRequestDataIdentificación = new JSONObject();
        jsonRequestDataIdentificación.put("request", requestData);

        return jsonRequestDataIdentificación;
    }
    
    public static JSONObject createResponse_NuevoAmigo_Ok() throws JSONException {
        if(jsonResponseNuevoAmigoOk != null) return jsonResponseNuevoAmigoOk;

        jsonResponseNuevoAmigoOk = new JSONObject();
        jsonResponseNuevoAmigoOk.put("code", 0);
        jsonResponseNuevoAmigoOk.put("message", "");


        return jsonResponseNuevoAmigoOk;
    }



    public static JSONObject createResponse_ConsultarContactos_UnContacto() throws JSONException {
        if(jsonResponseConsultarContactosUnContacto != null) return jsonResponseConsultarContactosUnContacto;

        JSONArray vectorJsonContactos = new JSONArray();

        JSONObject personaUno = new JSONObject();
        personaUno.put("id", 1);
        personaUno.put("username", "test321");
        personaUno.put("email", "amigo@validos.com");
        personaUno.put("phone", "+123456799");
        personaUno.put("name", "test2");
        personaUno.put("imagen", "http://imagenes.com/img.png");

        vectorJsonContactos.put(personaUno);

        jsonResponseConsultarContactosUnContacto = new JSONObject();
        jsonResponseConsultarContactosUnContacto.put("data", vectorJsonContactos);
        jsonResponseConsultarContactosUnContacto.put("code", 0);
        jsonResponseConsultarContactosUnContacto.put("message", "");

        return jsonResponseConsultarContactosUnContacto;
    }

    public static JSONObject createResponse_ConsultarContactos_SinContactos() throws JSONException {
        if(jsonResponseConsultarContactosSinContactos != null) return jsonResponseConsultarContactosSinContactos;


        jsonResponseConsultarContactosSinContactos = new JSONObject();
        jsonResponseConsultarContactosSinContactos.put("data", new JSONArray());
        jsonResponseConsultarContactosSinContactos.put("code", 0);
        jsonResponseConsultarContactosSinContactos.put("message", "");

        return jsonResponseConsultarContactosSinContactos;
    }


    public static JSONObject createRequest_UsuarioIdentificado() throws JSONException {
        if(jsonRequestDataIdentificación != null) return jsonRequestDataIdentificación;

        JSONObject requestData = new JSONObject();
        requestData.put("email", "datos@validos.com");
        requestData.put("password", EncryptionUtils.toMD5("123456"));

        jsonRequestDataIdentificación = new JSONObject();
        jsonRequestDataIdentificación.put("request", requestData);

        return jsonRequestDataIdentificación;
    }

    public static JSONObject createRequest_UsuarioNoIdentificado() throws JSONException {
        if(jsonRequestNoIndentificado != null) return jsonRequestNoIndentificado;

        JSONObject requestData = new JSONObject();
        requestData.put("email", "no@identificado.com");
        requestData.put("password", EncryptionUtils.toMD5("123456"));

        jsonRequestNoIndentificado = new JSONObject();
        jsonRequestNoIndentificado.put("request", requestData);

        return jsonRequestNoIndentificado;
    }

    public static JSONObject createResponse_NoIdentificado() throws JSONException {
        if(jsonResponseNoIdentificado != null) return jsonResponseNoIdentificado;

        jsonResponseNoIdentificado = new JSONObject();
        jsonResponseNoIdentificado.put("code", 1);
        jsonResponseNoIdentificado.put("message", "El usuario no esta identificado.");

        return jsonResponseNoIdentificado;
    }
}
