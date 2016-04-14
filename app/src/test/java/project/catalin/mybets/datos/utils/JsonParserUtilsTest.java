package project.catalin.mybets.datos.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.datos.objetosData.Persona;

import static org.junit.Assert.assertTrue;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_ConsultarContactos_UnContacto;
import static project.catalin.mybets.datos.jsons.JsonCreatorUtils.createResponse_Registro_Ok;

/**
 * Created by Demils on 31/03/2016.
 */
public class JsonParserUtilsTest {

    @Test
    public void jsonToPersonaTest() throws JSONException {
        Persona persona = JsonParserUtils.jsonToPersona(createResponse_Registro_Ok());

        assertTrue(persona.getId() == 0);
        assertTrue(persona.getEmail().equals("datos@validos.com"));
        assertTrue(persona.getNombre().equals("test"));
        assertTrue(persona.getUsername().equals("test123"));
        assertTrue(persona.getTelefono().equals("+123456789"));
    }
    
    @Test
    public void loginToJsonTest() throws JSONException {
        LoginData loginData = new LoginData("nombre","pass");
        JSONObject jsonLoginData = JsonParserUtils.loginToJson(loginData);

        JSONObject requestData = jsonLoginData.getJSONObject("request");
        assertTrue(requestData.getString("email").equals("nombre"));
        assertTrue(requestData.getString("password").equals(EncryptionUtils.toMD5("pass")));
    }

    @Test
    public void registerToJsonTest() throws JSONException {
        JSONObject jsonRegisterData = JsonParserUtils.registerToJson(new Persona("email@valido.com", "test", "test123", "+123456789"), EncryptionUtils.toMD5("123456"));

        JSONObject requestData = jsonRegisterData.getJSONObject("request");
        assertTrue(requestData.getString("email").equals("email@valido.com"));
        assertTrue(requestData.getString("name").equals("test"));
        assertTrue(requestData.getString("username").equals("test123"));
        assertTrue(requestData.getString("phone").equals("+123456789"));
        assertTrue(requestData.getString("password").equals(EncryptionUtils.toMD5("123456")));
    }

    @Test
    public void jsonToPersonaListTest() throws  JSONException {
        List<Persona> listaPersonas = JsonParserUtils.jsonToPersonaList(createResponse_ConsultarContactos_UnContacto());

        Persona personaUno = listaPersonas.get(0);
        assertTrue(personaUno.getEmail().equals("amigo@validos.com"));
        assertTrue(personaUno.getNombre().equals("test2"));
        assertTrue(personaUno.getUsername().equals("test321"));
        assertTrue(personaUno.getTelefono().equals("+123456799"));
        assertTrue(personaUno.getImage().equals("http://imagenes.com/img.png"));
    }
}
