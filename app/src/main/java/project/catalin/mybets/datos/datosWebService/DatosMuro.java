package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataMuro;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.EntradaMuro;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 19/05/2016.
 */
public class DatosMuro implements DataMuro {
    @Override
    public List<EntradaMuro> getDataMuro() throws ErrorInternoException, ErrorServerException {
        return new RequestDataMuro().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_DATOS_MURO);
    }

    private class RequestDataMuro extends JsonRequest<List<EntradaMuro>>{
        @Override
        JSONObject CreateRequest() throws Exception {
            return SharedPreferencesUtils.getLoginJsonCopy();
        }

        @Override
        List<EntradaMuro> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToEntradasMuroList(response);
        }
    }
}
