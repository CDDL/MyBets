package project.catalin.mybets.datos.jsons;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.mockito.ArgumentMatcher;

/**
 * Created by Catalin on 11/04/2016.
 */
public class JsonObjectComparator extends ArgumentMatcher<JSONObject> {

    private final JSONObject mComparar;

    public JsonObjectComparator(JSONObject aComparar) {
        mComparar = aComparar;
    }

    @Override
    public boolean matches(Object otro) {
        if(otro == null) return false;

        JsonParser parser = new JsonParser();
        JsonElement comp1 = parser.parse(mComparar.toString());
        JsonElement comp2 = parser.parse(otro.toString());

        return comp1.equals(comp2);
    }




}
