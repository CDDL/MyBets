package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 19/05/2016.
 */
public class DatosPartidasPopulares implements DataPartidasPopulares {
    @Override
    public List<Partida> getPartidasPopulares() throws ErrorInternoException, ErrorServerException {
        return new RequestPartidasPopulares().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_PARTIDAS_POPULARES);
    }

    private class RequestPartidasPopulares extends JsonRequest<List<Partida>>{
        @Override
        JSONObject CreateRequest() throws Exception {
            return SharedPreferencesUtils.getLoginJsonCopy();
        }

        @Override
        List<Partida> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToPartidasList(response);
        }
    }
}
