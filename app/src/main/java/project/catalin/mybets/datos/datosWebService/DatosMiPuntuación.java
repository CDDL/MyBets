package project.catalin.mybets.datos.datosWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import project.catalin.mybets.controladores.comunicacionDatos.DataApuesta;
import project.catalin.mybets.controladores.comunicacionDatos.DataPuntos;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.FichaApuestas;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 26/05/2016.
 */
public class DatosMiPuntuación implements DataPuntos {

    @Override
    public int obtenerMisPuntos() throws ErrorInternoException, ErrorServerException {
        return new RequestApuesta().sendRequest(GestorDataWebServices.URL_PETICIÓN_MIS_PUNTOS);
    }

    private class RequestApuesta extends JsonRequest<Integer>{
        @Override
        JSONObject CreateRequest() throws Exception {
            return SharedPreferencesUtils.getLoginJsonCopy();
        }

        @Override
        Integer ParseResponse(JSONObject response) throws Exception {
            return response.getJSONObject("data").getInt("puntuacion");
        }
    }
}
