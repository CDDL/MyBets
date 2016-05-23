package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasHoy;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 19/05/2016.
 */
public class DatosPartidasHoy implements DataPartidasHoy {
    @Override
    public List<Partida> getPartidasHoy() throws ErrorInternoException, ErrorServerException {
        return new RequestGetPartidasHoy().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_PARTIDAS_HOY);
    }

    private class RequestGetPartidasHoy extends JsonRequest<List<Partida>> {
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
