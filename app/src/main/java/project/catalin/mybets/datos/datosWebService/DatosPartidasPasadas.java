package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPasadas;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class DatosPartidasPasadas implements DataPartidasPasadas{
    @Override
    public List<Partida> getPartidasPasadas() throws ErrorServerException, ErrorInternoException {
        return new RequestPartidasPasadas().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_PARTIDAS_PASADAS);
    }

    private class RequestPartidasPasadas extends JsonRequest<List<Partida>> {

        @Override
        public JSONObject CreateRequest() throws Exception {
            return SharedPreferencesUtils.getLoginJsonCopy();
        }

        @Override
        public List<Partida> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToPartidasList(response);
        }
    }
}
