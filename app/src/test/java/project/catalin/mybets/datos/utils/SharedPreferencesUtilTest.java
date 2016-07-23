package project.catalin.mybets.datos.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import project.catalin.mybets.BuildConfig;
import project.catalin.mybets.datos.shadows.ShadowSharedPreferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_UsuarioIdentificado;

/**
 * Created by Trabajo on 12/04/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows={ShadowSharedPreferences.class})
public class SharedPreferencesUtilTest {

    @Test
    public void saveLoginTest() throws JSONException {
        JSONObject loginData = createRequest_UsuarioIdentificado();
        SharedPreferencesUtils.guardarJsonLogin(loginData);

        JSONObject loginCopy = SharedPreferencesUtils.getLoginJsonCopy();

        assertFalse(loginData == loginCopy);

        assertTrue(loginCopy.getJSONObject("request").getString("email").equals("datos@validos.com"));
        assertTrue(loginCopy.getJSONObject("request").getString("password").equals(EncryptionUtils.toMD5("123456")));
    }

    @Test
    public void loggedTest() throws JSONException {
        assertFalse(SharedPreferencesUtils.isLogged());

        JSONObject loginData = createRequest_UsuarioIdentificado();
        SharedPreferencesUtils.guardarJsonLogin(loginData);

        assertTrue(SharedPreferencesUtils.isLogged());

        SharedPreferencesUtils.logOut();

        assertFalse(SharedPreferencesUtils.isLogged());
    }

    @After
    public void logOut(){ SharedPreferencesUtils.logOut(); }
}