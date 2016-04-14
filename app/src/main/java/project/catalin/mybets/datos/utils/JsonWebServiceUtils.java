package project.catalin.mybets.datos.utils;


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
        URLConnection connection = getConnectionDeUrl(url);

        OutputStream os = connection.getOutputStream();
        os.write(datosPetición.toString().getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String linea = "";
        while(linea != null) {
            linea = br.readLine();
            sb.append(linea);
        }

        return new JSONObject(sb.toString());
    }

    private static HttpURLConnection getConnectionDeUrl(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }
}
