package project.catalin.mybets.datos.shadows;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(PreferenceManager.class)
public class ShadowSharedPreferences {

    @Implementation
    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        Application cntx = RuntimeEnvironment.application;
        SharedPreferences prefs = cntx.getSharedPreferences("test", Context.MODE_PRIVATE);
        return prefs;
    }

}