package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import project.catalin.mybets.controladores.comunicacionDatos.DataPantallaApuestas;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 26/05/2016.
 */
public class DatosPantallaApuestas implements DataPantallaApuestas {
    @Override
    public int getIdFutbol() throws ErrorInternoException, ErrorServerException {
        return new RequestIdCategoriaFutbol().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_PARTIDAS_POPULARES);
    }

    private class RequestIdCategoriaFutbol extends JsonRequest<Integer> {

        @Override
        JSONObject CreateRequest() throws Exception {
            return SharedPreferencesUtils.getLoginJsonCopy();
        }

        @Override
        Integer ParseResponse(JSONObject response) throws Exception {
            return response.getInt("idfutbol");
        }
    }
}
