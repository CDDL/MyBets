package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import project.catalin.mybets.controladores.comunicacionDatos.DataClasificacionPartida;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.FichaClasificacion;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 16/05/2016.
 */
public class DatosClasificacionPartida implements DataClasificacionPartida {
    @Override
    public FichaClasificacion getClasificacion(int idPartida) throws ErrorInternoException, ErrorServerException {
        return new RequestClasificacion(idPartida).sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_CLASIFICACION_PARTIDA);
    }

    private class RequestClasificacion extends JsonRequest<FichaClasificacion> {
        private final int mIdPartida;

        public RequestClasificacion(int idPartida) {
            mIdPartida = idPartida;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject jsonData = SharedPreferencesUtils.getLoginJsonCopy();
            jsonData.getJSONObject("request").put("idpartida", mIdPartida);
            return jsonData;
        }

        @Override
        FichaClasificacion ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToFichaClasificacion(response);
        }
    }
}
