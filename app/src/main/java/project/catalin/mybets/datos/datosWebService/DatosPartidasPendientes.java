package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPendientes;
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

    @Override
    public void actualizarNombrePartida(int idPartida, String nuevoNombre) throws ErrorInternoException, ErrorServerException {
        new PeticionActualizarNombrePartida(idPartida, nuevoNombre).sendRequest(GestorDataWebServices.URL_PETICIÓN_ACTUALIZAR_NOMBRE_PARTIDA);
    }

    @Override
    public void rechazarPartida(int idPartida) throws ErrorInternoException, ErrorServerException {
        new PeticionRechazarPartida(idPartida).sendRequest(GestorDataWebServices.URL_PETICIÓN_RECHAZAR_PARTIDA);
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

    private class PeticionActualizarNombrePartida extends JsonRequest<Void> {
        private final String mNuevoNombre;
        private final int mIdPartida;

        public PeticionActualizarNombrePartida(int idPartida, String nuevoNombre) {
            mIdPartida = idPartida;
            mNuevoNombre = nuevoNombre;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject jsonData = SharedPreferencesUtils.getLoginJsonCopy();
            jsonData.getJSONObject("request").put("idpartida", mIdPartida);
            jsonData.getJSONObject("request").put("nuevonombre", mNuevoNombre);
            return jsonData;
        }

        @Override
        Void ParseResponse(JSONObject response) throws Exception {
            return null;
        }
    }

    private class PeticionRechazarPartida extends JsonRequest<Void>{
        private final int mIdParitda;

        public PeticionRechazarPartida(int idPartida) {
            mIdParitda = idPartida;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject jsonData = SharedPreferencesUtils.getLoginJsonCopy();
            jsonData.getJSONObject("request").put("idpartida", mIdParitda);
            return jsonData;
        }

        @Override
        Void ParseResponse(JSONObject response) throws Exception {
            return null;
        }
    }
}
