package project.catalin.mybets.datos.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import project.catalin.mybets.datos.dataObjects.Apuesta;
import project.catalin.mybets.datos.dataObjects.Categoria;
import project.catalin.mybets.datos.dataObjects.EntradaClasificacion;
import project.catalin.mybets.datos.dataObjects.EntradaHistorial;
import project.catalin.mybets.datos.dataObjects.EntradaMuro;
import project.catalin.mybets.datos.dataObjects.Equipo;
import project.catalin.mybets.datos.dataObjects.FichaClasificacion;
import project.catalin.mybets.datos.dataObjects.FichaHistorial;
import project.catalin.mybets.datos.dataObjects.FichaJornada;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.dataObjects.Subcategoria;

/**
 * Created by Demils on 23/03/2016.
 */
public class JsonParserUtils {

    public static Persona jsonToPersona(JSONObject jsonResult) throws JSONException {
        JsonWrapper wrapper = new JsonWrapper(jsonResult);
        JsonWrapper jsonData = wrapper.getObject("data", null);

        Persona persona = new Persona();
        persona.setId(jsonData.getInt("id", -1));
        persona.setEmail(jsonData.getString("email", ""));
        persona.setNombre(jsonData.getString("name", ""));
        persona.setUsername(jsonData.getString("username", ""));
        persona.setTelefono(jsonData.getString("phone", ""));
        persona.setImagen(jsonData.getString("imagen", ""));

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
        jsonData.put("image", dataUsuario.getImagen());

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("request", jsonData);

        return jsonResult;
    }

    public static List<Persona> jsonToPersonaList(JSONObject jsonObject) throws JSONException {
        List<Persona> listaPersonas = new LinkedList<>();

        List<JsonWrapper> jsonListaPersonas = new JsonWrapper(jsonObject).getArray("data", null);
        for (JsonWrapper jsonPersona:jsonListaPersonas) {
            Persona persona = new Persona();
            persona.setId(jsonPersona.getInt("id", jsonPersona.getInt("idusuario", -1)));
            persona.setImagen(jsonPersona.getString("imagen", jsonPersona.getString("urlicono", "")));
            persona.setUsername(jsonPersona.getString("username", ""));
            persona.setNombre(jsonPersona.getString("name", jsonPersona.getString("nombre", "")));
            persona.setTelefono(jsonPersona.getString("phone", jsonPersona.getString("telefono", "")));
            persona.setEmail(jsonPersona.getString("email", ""));

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

        for (JsonWrapper jsonDataObject : jsonArray) {
            Partida partida = new Partida();
            partida.setId(jsonDataObject.getInt("idpartida", -1));
            partida.setNombrePartida(jsonDataObject.getString("nombrepartida", jsonDataObject.getString("nombrePartida" , "Sin nombre")));
            partida.setFecha(new SimpleDateFormat("dd/MM/yy HH:mm", Locale.US).parse(jsonDataObject.getString("fecha", "11/11/00 11:11")));
            partida.setBote(jsonDataObject.getInt("bote", -1));
            partida.setNumPersonas(jsonDataObject.getInt("numpersonas", -1));
            partida.setUrlIcono(jsonDataObject.getString("urlicono", ""));
            partida.setColorIcono(jsonDataObject.getString("coloricono", jsonWrapper.getString("color", "#111111")));
            partida.setTipoPartida(jsonDataObject.getInt("tipopartida", -1));
            partida.setEstadoPartida(jsonDataObject.getInt("estadoapuesta", Partida.ESTADO_JUEGA_YA));

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


    public static List<FichaHistorial> jsonToDataHistorialPartidasPasadas(JSONObject resultData) throws ParseException {
        JsonWrapper jsonWrapper = new JsonWrapper(resultData);
        JsonWrapper partidaWrapper = jsonWrapper.getObject("partida", null);
        List<FichaHistorial> historiales = new LinkedList<>();


        List<JsonWrapper> dataVectorWrapper = jsonWrapper.getArray("data", Collections.<JsonWrapper>emptyList());
        for (JsonWrapper dataWrapper : dataVectorWrapper) {
            FichaHistorial historialApuestas = new FichaHistorial();

            Persona persona = new Persona();
            persona.setImagen(dataWrapper.getString("urlperfil", ""));
            persona.setId(dataWrapper.getInt("idusuario", -1));

            historialApuestas.setPersona(persona);
            historialApuestas.setBote(partidaWrapper.getInt("bote", -1));
            historialApuestas.setColorFondo(partidaWrapper.getString("colorfondo", "#FFFFFF"));
            historialApuestas.setUrlIcono(partidaWrapper.getString("urlicono", ""));
            historialApuestas.setFecha(new DateUtils().from(partidaWrapper.getString("fecha", "11/11/1111 11:11")).toDate("dd/MM/yyyy HH:mm"));

            List<JsonWrapper> apuestasVectorWrapper = dataWrapper.getArray("apuestas", Collections.<JsonWrapper>emptyList());
            for (JsonWrapper apuestaWrapper : apuestasVectorWrapper) {
                Apuesta apuesta = new Apuesta();
                apuesta.setNombreLocal(apuestaWrapper.getString("nombrelocal", ""));
                apuesta.setUrlImagenLocal(apuestaWrapper.getString("urllocal", ""));
                apuesta.setNombreVisitante(apuestaWrapper.getString("nombrevisitante", ""));
                apuesta.setUrlImagenVisitante(apuestaWrapper.getString("urlvisitante", ""));
                apuesta.setApuesta(apuestaWrapper.getInt("apuesta", -1));
                historialApuestas.addApuesta(apuesta);
            }
            historiales.add(historialApuestas);
        }
        return historiales;
    }

    public static FichaClasificacion jsonToFichaClasificacion(JSONObject response) throws ParseException {
        JsonWrapper wrapper = new JsonWrapper(response);
        JsonWrapper jsonPartida = wrapper.getObject("partida", null);

        FichaClasificacion fichaClasificacion = new FichaClasificacion();
        fichaClasificacion.setNombrePartida(jsonPartida.getString("nombre", ""));
        fichaClasificacion.setFechaPartida(new DateUtils().from(jsonPartida.getString("fecha", "11/11/2011 11:11")).toDate());
        fichaClasificacion.setBotePartida(jsonPartida.getInt("bote", 0));
        fichaClasificacion.setUrlIcono(jsonPartida.getString("urlicono", ""));
        fichaClasificacion.setColorFondo(jsonPartida.getString("colorfondo", "#000000"));

        List<EntradaClasificacion> entradaClasificacionList = new LinkedList<>();
        for (JsonWrapper dataEntrada : wrapper.getArray("data", Collections.<JsonWrapper>emptyList())) {
            Persona persona = new Persona();
            persona.setNombre(dataEntrada.getString("nombre", ""));
            persona.setImagen(dataEntrada.getString("urlicono", ""));

            EntradaClasificacion entradaClasificacion = new EntradaClasificacion();
            entradaClasificacion.setPersona(persona);
            entradaClasificacion.setPuntos(dataEntrada.getInt("puntos", 0));
            entradaClasificacion.setPosicion(dataEntrada.getInt("posicion", 0));

            entradaClasificacionList.add(entradaClasificacion);
        }

        fichaClasificacion.setEntradasList(entradaClasificacionList);
        return fichaClasificacion;
    }

    public static List<Categoria> jsonToCategoriaList(JSONObject response) {
        JsonWrapper wrapper = new JsonWrapper(response);
        List<JsonWrapper> jsonVectorCategorias = wrapper.getArray("data", null);

        List<Categoria> categoriaList = new LinkedList<>();
        for (JsonWrapper jsonCategoria : jsonVectorCategorias) {
            Categoria categoria = new Categoria();
            categoria.setUrlIcono(jsonCategoria.getString("icon", ""));
            categoria.setId(jsonCategoria.getInt("id", 0));
            categoria.setNombre(jsonCategoria.getString("name", ""));

            categoriaList.add(categoria);
        }

        return categoriaList;
    }

    public static List<Subcategoria> jsonToCategoriasList(JSONObject response) {
        JsonWrapper wrapper = new JsonWrapper(response);
        List<JsonWrapper> jsonListSubcategorias = wrapper.getArray("subcategorias", null);
        List<Subcategoria> listaSubcategorias = new LinkedList<>();
        for (JsonWrapper jsonCategoria : jsonListSubcategorias) {
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setId(jsonCategoria.getInt("idsubcategoria", 0));
            subcategoria.setNombreSubcategoria(jsonCategoria.getString("nombresubcategoria", ""));

            listaSubcategorias.add(subcategoria);
        }

        return listaSubcategorias;
    }

    public static Subcategoria jsonToCategoria(JSONObject response) throws ParseException {
        JsonWrapper wrapper = new JsonWrapper(response);
        List<JsonWrapper> jsonListaPartidas = wrapper.getArray("data", null);

        Subcategoria subcategoria = new Subcategoria();

        List<Partida> listaPartidas = new LinkedList<>();
        for (JsonWrapper jsonPartida : jsonListaPartidas) {
            Partida partida = new Partida();
            partida.setId(jsonPartida.getInt("idpartida", 0));
            partida.setNombrePartida(jsonPartida.getString("nombrepartida", jsonPartida.getString("nombrePartida", "")));
            partida.setFecha(new DateUtils().from(jsonPartida.getString("fecha", "11/11/2011 11/11")).toDate());
            partida.setBote(jsonPartida.getInt("bote", 0));
            partida.setNumPersonas(jsonPartida.getInt("numpersonas", 0));
            partida.setUrlIcono(jsonPartida.getString("urlicono", ""));
            partida.setColorIcono(jsonPartida.getString("coloricono", wrapper.getString("color", "")));
            partida.setTipoPartida(jsonPartida.getInt("tipopartida", Partida.TIPO_PARTIDA_1x2));
            partida.setEstadoPartida(Partida.ESTADO_JUEGA_YA);

            listaPartidas.add(partida);
        }
        subcategoria.setListPartidas(listaPartidas);

        return subcategoria;
    }

    public static List<EntradaMuro> jsonToEntradasMuroList(JSONObject response) throws ParseException {
        JsonWrapper wrapper = new JsonWrapper(response);
        List<EntradaMuro> listaEntradasMuro = new LinkedList<>();
        List<JsonWrapper> jsonListEntradasMuro = wrapper.getArray("data", null);

        for (JsonWrapper jsonEntradaMuro : jsonListEntradasMuro) {
            EntradaMuro entrada = new EntradaMuro();
            entrada.setPuntosGanados(jsonEntradaMuro.getInt("puntosganados", 0));
            entrada.setNombreJuego(jsonEntradaMuro.getString("nombrejuego", ""));
            entrada.setFecha(new DateUtils().from(jsonEntradaMuro.getString("fecha", "11/11/2011 11:11")).toDate());

            Persona persona = new Persona();
            persona.setNombre(jsonEntradaMuro.getString("nombreamigo", ""));
            persona.setImagen(jsonEntradaMuro.getString("urlfoto", ""));

            entrada.setPersona(persona);
            listaEntradasMuro.add(entrada);
        }

        return listaEntradasMuro;
    }

    public static FichaJornada jsonToFichaJornada(JSONObject resultData) throws JSONException {
        FichaJornada fichaJornada = new FichaJornada();
        fichaJornada.setIdJornada(resultData.getJSONObject("data").getInt("idjornada"));
        fichaJornada.setListPartidos(jsonToPartidosList(resultData));
        return fichaJornada;
    }
}




























