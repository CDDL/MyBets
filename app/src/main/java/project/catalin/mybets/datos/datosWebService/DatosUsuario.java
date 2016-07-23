package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import project.catalin.mybets.controladores.comunicacionDatos.DataRegister;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.utils.JsonParserUtils;

/**
 * Created by CDD on 24/05/2016.
 */
public class DatosUsuario implements DataRegister {
    @Override
    public int registrarUsuario(Persona dataUsuario, String password) throws EmailMalFormadoException, UsuarioRepetidoException, TelefonoMalFormadoException, EmailVacioException, NombreVacioException, ErrorInternoException, ErrorServerException {
        return new RegisterRequest(dataUsuario, password).sendRequest(GestorDataWebServices.URL_PETICIÓN_REGISTER);
    }

    private class RegisterRequest extends JsonRequest<Integer>{
        private final Persona mUsuario;
        private final String mPassword;

        public RegisterRequest(Persona dataUsuario, String password) {
            mUsuario = dataUsuario;
            mPassword = password;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            return JsonParserUtils.registerToJson(mUsuario, mPassword);
        }

        @Override
        Integer ParseResponse(JSONObject response) throws Exception {
            return response.getJSONObject("data").getInt("id");
        }
    }
}
