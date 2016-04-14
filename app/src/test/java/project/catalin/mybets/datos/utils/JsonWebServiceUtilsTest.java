package project.catalin.mybets.datos.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.MyBetsMock;
import project.catalin.mybets.datos.jsons.JsonCreatorUtils;

import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Demils on 23/03/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JsonWebServiceUtils.class)
public class JsonWebServiceUtilsTest {

    @Before
    public void setUp() throws IOException, JSONException {
        mockStatic(JsonWebServiceUtils.class);

        new MyBetsMock().setUpWebService();
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
