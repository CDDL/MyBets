package project.catalin.mybets.datos.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import project.catalin.mybets.BuildConfig;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.MyBetsMock;
import project.catalin.mybets.datos.jsons.JsonCreatorUtils;
import project.catalin.mybets.datos.shadows.ShadowSharedPreferences;

import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Demils on 23/03/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows={ShadowSharedPreferences.class})
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*", "org.json.*" })
@PrepareForTest(JsonWebServiceUtils.class)
public class JsonWebServiceUtilsTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void reInicializarGestor() throws IOException, JSONException {

        mockStatic(JsonWebServiceUtils.class);

        new project.catalin.mybets.datos.MyBetsMock()
                .setUpWebService();
    }


    @Test
    public void testPetición_LoginOk() throws IOException, JSONException {
        JSONObject datosRequest = JsonCreatorUtils.createRequest_Login_Valido();
        JSONObject datosResponse = JsonWebServiceUtils.petición(GestorDataWebServices.URL_PETICIÓN_LOGIN, datosRequest);

        assertTrue(datosResponse.getInt("code") == 0);
    }

    @Test
    public void testPetición_LoginError() throws IOException, JSONException {
        JSONObject datosRequest = JsonCreatorUtils.createRequest_Login_Invalido();
        JSONObject datosResponse = JsonWebServiceUtils.petición(GestorDataWebServices.URL_PETICIÓN_LOGIN, datosRequest);

        assertTrue(datosResponse.getInt("code") == 1);
    }

    @Test
    public void testPetición_SignOk() throws IOException, JSONException {
        JSONObject datosRequest = JsonCreatorUtils.createRequest_Registro_DatosValidos();
        JSONObject datosResponse = JsonWebServiceUtils.petición(GestorDataWebServices.URL_PETICIÓN_REGISTER, datosRequest);

        assertTrue(datosResponse.getInt("code") == 0);
    }
}
