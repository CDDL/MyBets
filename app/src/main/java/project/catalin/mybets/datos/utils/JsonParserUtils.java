package project.catalin.mybets.datos.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.datos.objetosData.Persona;

/**
 * Created by Demils on 23/03/2016.
 */
public class JsonParserUtils {

    public static Persona jsonToPersona(JSONObject jsonData) throws JSONException {
        JSONObject extractedData = jsonData.getJSONObject("data");
        Persona personaResultante = new Persona(
                extractedData.getString("email"),
                extractedData.getString("name"),
                extractedData.getString("username"),
                extractedData.getString("phone")
        );

        personaResultante.setId(extractedData.getInt("id"));

        return personaResultante;
    }

    public static JSONObject loginToJson(LoginData login) throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("email", login.getEmail());
        jsonData.put("password", EncryptionUtils.toMD5(login.getPassword()));

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonData);

        return jsonResult;
    }

    public static JSONObject registerToJson(Persona dataUsuario, String encryptedPassword) throws JSONException {
        JSONObject jsonData = new JSONObject();
        jsonData.put("email", dataUsuario.getEmail());
        jsonData.put("password", encryptedPassword);
        jsonData.put("name", dataUsuario.getNombre());
        jsonData.put("phone", dataUsuario.getTelefono());
        jsonData.put("username", dataUsuario.getUsername());

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonData);

        return jsonResult;
    }

    public static List<Persona> jsonToPersonaList(JSONObject jsonObject) throws JSONException {
        List<Persona> listaPersonas = new LinkedList<>();

        JSONArray listaPersonasJson = jsonObject.getJSONArray("data");
        for (int i = 0; i < listaPersonasJson.length(); i++) {
            JSONObject personaJson = listaPersonasJson.getJSONObject(i);

            Persona persona = new Persona(personaJson.getString("email"), personaJson.getString("name"), personaJson.getString("username"), personaJson.getString("phone"));
            persona.setId(personaJson.getInt("id"));
            persona.setImage(personaJson.getString("imagen"));

            listaPersonas.add(persona);
        }


        return listaPersonas;
    }
}