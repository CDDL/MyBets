package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonWebServiceUtils;

/**
 * Created by Trabajo on 10/05/2016.
 */
public abstract class JsonRequest<T> {

    abstract JSONObject CreateRequest() throws Exception;

    abstract T ParseResponse(JSONObject response) throws Exception;

    public T sendRequest(String urlPeticion) throws ErrorServerException, ErrorInternoException {
        try {
            JSONObject response = JsonWebServiceUtils.petición(urlPeticion, CreateRequest());
            if (response.getInt("code") == Constantes.RESPUESTA_WEBSERV_OK)
                throw new ErrorServerException(response.getString("message"));
            return ParseResponse(response);
        } catch (ErrorServerException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorInternoException();
        }
    }
}
