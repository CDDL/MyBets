package project.catalin.mybets.datos.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import project.catalin.mybets.datos.dataObjects.EntradaHistorial;
import project.catalin.mybets.datos.dataObjects.Equipo;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Demils on 23/03/2016.
 */
public class JsonParserUtils {

    public static Persona jsonToPersona(JSONObject jsonResult) throws JSONException {
        JSONObject jsonData = jsonResult.getJSONObject("data");
        Persona persona = new Persona();

        try {persona.setId(jsonData.getInt("id"));} catch (Exception ignored) {}
        persona.setNombre(jsonData.getString("name"));
        persona.setEmail(jsonData.getString("email"));
        persona.setUsername(jsonData.getString("username"));
        try {persona.setImagen(jsonData.getString("imagen"));} catch (Exception ignored) {ignored.printStackTrace();}

        return persona;
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

            Persona persona = new Persona();
            persona.setId(personaJson.getInt("id"));
            persona.setImagen(personaJson.getString("imagen"));

            listaPersonas.add(persona);
        }


        return listaPersonas;
    }

    public static JSONArray contactsToJsonArray(List<Persona> contactos) {
        JSONArray array = new JSONArray();
        for (Persona contacto : contactos) array.put(contacto.getId());

        return array;
    }

    public static List<Partida> jsonToPartidasList(JSONObject jsonObject) throws JSONException, ParseException {
        JsonWrapper jsonWrapper = new JsonWrapper(jsonObject);
        List<JsonWrapper> jsonArray = jsonWrapper.getArray("data", null);
        List<Partida> partidas = new LinkedList<>();

        for (JsonWrapper jsonDataObject:jsonArray) {
            Partida partida = new Partida();
            partida.setId(jsonDataObject.getInt("idpartida", 0));
            partida.setNombrePartida(jsonDataObject.getString("nombrepartida", "Sin nombre"));
            partida.setFecha(new SimpleDateFormat("dd/MM/yy HH:mm", Locale.US).parse(jsonDataObject.getString("fecha", "11/11/00 11:11")));
            partida.setBote(jsonDataObject.getInt("bote", 0));
            partida.setNumPersonas(jsonDataObject.getInt("numpersonas", 0));
            partida.setUrlIcono(jsonDataObject.getString("urlicono", ""));
            partida.setColorIcono(jsonDataObject.getString("coloricono", "#3F51B5"));
            partida.setTipoPartida(jsonDataObject.getInt("tipopartida", -1));

            partidas.add(partida);
        }
        return partidas;
    }

    public static Persona jsonToUserData(JSONObject resultData) throws JSONException {
        JSONObject jsonUserData = resultData.getJSONObject("user");
        Persona persona = new Persona();
        persona.setNombre(jsonUserData.getString("nombre"));
        persona.setPuntos(jsonUserData.getInt("puntosTotales"));
        persona.setImagen(jsonUserData.getString("imagen"));


        return persona;
    }

    public static List<Partido> jsonToPartidosList(JSONObject jsonObject) throws JSONException {
        List<Partido> listaPartidos = new LinkedList<>();
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray arrayPartidos = data.getJSONArray("listapartidos");

        for (int i = 0; i < arrayPartidos.length(); i++) {
            JSONObject jsonPartido = arrayPartidos.getJSONObject(i);
            Partido partido = new Partido();
            partido.setId(jsonPartido.getInt("idpartido"));

            JSONArray arrayEquipos = jsonPartido.getJSONArray("listaequipos");

            for (int j = 0; j < arrayEquipos.length(); j++) {
                JSONObject jsonEquipo = arrayEquipos.getJSONObject(j);
                Equipo equipo = new Equipo();
                equipo.setId(jsonEquipo.getInt("id"));
                equipo.setNombre(jsonEquipo.getString("nombre"));
                equipo.setIcono(jsonEquipo.getString("iconurl"));

                partido.addEquipo(equipo);
            }

            listaPartidos.add(partido);
        }

        return listaPartidos;
    }

    public static List<EntradaHistorial> jsonToEntradasHistorialList(JSONObject resultData) throws JSONException, ParseException {
        List<EntradaHistorial> listaEntradasHistorial = new LinkedList<>();

        JSONArray jsonVectorEntradas = resultData.getJSONArray("data");
        for (int i = 0; i < jsonVectorEntradas.length(); i++) {
            JSONObject jsonEntrada = jsonVectorEntradas.getJSONObject(i);

            EntradaHistorial entrada = new EntradaHistorial();
            entrada.setTitulo(jsonEntrada.getString("nombre"));
            entrada.setPuntos(jsonEntrada.getInt("puntos"));
            entrada.setFecha(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(jsonEntrada.getString("fecha")));

            listaEntradasHistorial.add(entrada);
        }

        return listaEntradasHistorial;
    }


}




























