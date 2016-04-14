package project.catalin.mybets.datos.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import project.catalin.mybets.datos.MyBetsMock;
import project.catalin.mybets.view.MainApp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createRequest_UsuarioIdentificado;

/**
 * Created by Trabajo on 12/04/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MainApp.class)
public class SharedPreferencesUtilTest {

    @Before
    public void setUp() throws IOException, JSONException {
        mockStatic(MainApp.class);

        new MyBetsMock().setUpSharedPrefs();
    }

    @Test
    public void saveLoginTest() throws JSONException {
        JSONObject loginData = createRequest_UsuarioIdentificado();
        SharedPreferencesUtils.saveLogin(loginData);

        JSONObject loginCopy = SharedPreferencesUtils.getLoginJsonCopy();

        assertFalse(loginData == loginCopy);

        assertTrue(loginCopy.getJSONObject("request").getString("email").equals("datos@validos.com"));
        assertTrue(loginCopy.getJSONObject("request").getString("password").equals(EncryptionUtils.toMD5("123456")));
    }

    @Test
    public void loggedTest() throws JSONException {
        assertFalse(SharedPreferencesUtils.isLogged());

        JSONObject loginData = createRequest_UsuarioIdentificado();
        SharedPreferencesUtils.saveLogin(loginData);

        assertTrue(SharedPreferencesUtils.isLogged());

        SharedPreferencesUtils.logOut();

        assertFalse(SharedPreferencesUtils.isLogged());
    }

    @After
    public void logOut(){ SharedPreferencesUtils.logOut(); }
}