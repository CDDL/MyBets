package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicaciónDatos.DataPartidasPendientes;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class DatosPartidasPendientes implements DataPartidasPendientes {
    @Override
    public List<Partida> getPartidasPendientes() throws ErrorInternoException, ErrorServerException {
        return new PeticionPartidasPendientes().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_PARTIDAS_PENDIENDES);
    }

    private class PeticionPartidasPendientes extends JsonRequest<List<Partida>> {

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
