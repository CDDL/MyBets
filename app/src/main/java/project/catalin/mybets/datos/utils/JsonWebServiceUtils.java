package project.catalin.mybets.datos.utils;


import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Demils on 23/03/2016.
 */

public class JsonWebServiceUtils {

    public static JSONObject petición(String url, JSONObject datosPetición) throws IOException, JSONException {
        URLConnection mConexion = getConnectionDeUrl(url);
        escribirDatos(datosPetición, mConexion);
        StringBuilder sb = leerRespuesta(mConexion);

        return new JSONObject(sb.toString());
    }

    @NonNull
    private static StringBuilder leerRespuesta(URLConnection mConexion) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(mConexion.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String linea = "";

        while (linea != null) {
            linea = br.readLine();
            sb.append(linea);
        }
        return sb;
    }

    private static void escribirDatos(JSONObject datosPetición, URLConnection connection) throws IOException {
        OutputStream os = connection.getOutputStream();
        os.write(datosPetición.toString().getBytes());
        os.flush();
    }

    private static HttpURLConnection getConnectionDeUrl(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }
}
