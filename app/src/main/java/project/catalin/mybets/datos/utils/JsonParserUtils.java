package project.catalin.mybets.datos.utils;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.datos.objetosData.Partida;
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

    public static JSONArray contactsToJsonArray(List<Integer> contactos) {
        JSONArray array = new JSONArray();
        for (int idContacto : contactos) array.put(idContacto);

        return array;
    }

    public static List<Partida> jsonToPartidasList(JSONObject jsonObject) throws JSONException, ParseException {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<Partida> partidas = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonData = jsonArray.getJSONObject(i);

            Partida partida = new Partida(
                    jsonData.getInt("idpartida"),
                    jsonData.getString("nombrepartida"),
                    new SimpleDateFormat("dd/MM/yy HH:mm", Locale.US).parse(jsonData.getString("fecha")),
                    jsonData.getInt("bote"),
                    jsonData.getInt("numpersonas"),
                    jsonData.getString("urlicono"));

            partida.setColorIcono(Integer.parseInt(jsonData.getString("coloricono").substring(1)));
            partida.setTipoPartida(jsonData.getInt("tipopartida"));

            partidas.add(partida);
        }
        return partidas;
    }
}