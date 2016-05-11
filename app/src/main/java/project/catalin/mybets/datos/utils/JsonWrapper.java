package project.catalin.mybets.datos.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class JsonWrapper {
    private final JSONObject mJsonObject;

    public JsonWrapper(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public List<JsonWrapper> getArray(String identificador, List<JsonWrapper> defaultValue) {
        try {
            JSONArray jsonArray = mJsonObject.getJSONArray(identificador);
            List<JsonWrapper> wrapperList = new LinkedList<>();
            for (int i=0 ; i<jsonArray.length(); i++) {
                wrapperList.add(new JsonWrapper(jsonArray.getJSONObject(i)));
            }
            return wrapperList;
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public int getInt(String identificador, int defaultValue) {
        try {
            return mJsonObject.getInt(identificador);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public String getString(String identificador, String defaultValue) {
        try {
            return mJsonObject.getString(identificador);
        } catch (JSONException e) {
            return defaultValue;
        }
    }
}
