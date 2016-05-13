package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicaciónDatos.DataHistorialPartidasPasadas;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.FichaHistorial;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 12/05/2016.
 */
public class DatosHistorialPartidasPasadas implements DataHistorialPartidasPasadas {
    private int mIdPartida;

    @Override
    public List<FichaHistorial> getHistorialDePartidaId(int idPartida) throws ErrorInternoException, ErrorServerException {
        mIdPartida = idPartida;
        return new PeticionHistorialApuestas().sendRequest(GestorDataWebServices.URL_PETICIÓN_HISTORIAL_PARTIDA);
    }

    private class PeticionHistorialApuestas extends JsonRequest<List<FichaHistorial>> {

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject loginData = SharedPreferencesUtils.getLoginJsonCopy();
            loginData.getJSONObject("request").put("idpartida", mIdPartida);
            return loginData;
        }

        @Override
        List<FichaHistorial> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToDataHistorialPartidasPasadas(response);
        }
    }
}
