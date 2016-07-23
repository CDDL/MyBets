package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import project.catalin.mybets.controladores.comunicacionDatos.DataIdentificación;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;

/**
 * Created by CDD on 25/05/2016.
 */
public class DatosIdentificación implements DataIdentificación {
    @Override
    public Persona validarIdentificación(LoginData loginData) throws ErrorInternoException, ErrorServerException {
        return new RequestIndentificación(loginData).sendRequest(GestorDataWebServices.URL_PETICIÓN_LOGIN);
    }

    private class RequestIndentificación extends JsonRequest<Persona>{
        private final LoginData mLoginData;

        public RequestIndentificación(LoginData loginData) {
            mLoginData = loginData;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            return JsonParserUtils.loginToJson(mLoginData);
        }

        @Override
        Persona ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToPersona(response);
        }
    }
}
