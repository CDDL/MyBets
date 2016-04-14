package project.catalin.mybets.datos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import project.catalin.mybets.view.ContextCreator;

/**
 * Created by Catalin on 08/04/2016.
 */
public class SharedPreferencesUtils {


    private static final String SHARED_PREFS_DB = "MyBestPrefs";
    private static final String SHARED_PREFS_KEY_JSON_LOGIN = "JsonLogin";
    private static final String SHARED_PREFS_KEY_LOGGED = "Logged";

    private static SharedPreferences prefs;

    static {
        Context context = ContextCreator.getAppContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveLogin(JSONObject loginData) {
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(SHARED_PREFS_KEY_JSON_LOGIN, loginData.toString());
        editor.putBoolean(SHARED_PREFS_KEY_LOGGED, true);
        editor.apply();
    }

    public static JSONObject getLoginJsonCopy() throws JSONException {

        return new JSONObject(prefs.getString(SHARED_PREFS_KEY_JSON_LOGIN, ""));
    }

    public static boolean isLogged() {
        return prefs.getBoolean(SHARED_PREFS_KEY_LOGGED, false);
    }

    public static void logOut() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SHARED_PREFS_KEY_LOGGED, false);
        editor.putString(SHARED_PREFS_KEY_JSON_LOGIN, "");
        editor.apply();

    }
}
