package project.catalin.mybets.datos.datosWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import project.catalin.mybets.controladores.comunicacionDatos.DataApuesta;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.FichaApuestas;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 26/05/2016.
 */
public class DatosApuesta implements DataApuesta {
    @Override
    public void enviarApuesta(FichaApuestas fichaApuestas) throws ErrorInternoException, ErrorServerException {
        new RequestApuesta(fichaApuestas).sendRequest(GestorDataWebServices.URL_PETICIÓN_APOSTAR);
    }

    @Override
    public void aceptarPartida(int idPartida) throws ErrorInternoException, ErrorServerException {
        new RequestAceptarPartida(idPartida).sendRequest(GestorDataWebServices.URL_PETICIÓN_ACEPTAR_PARTIDA);
    }

    private class RequestApuesta extends JsonRequest<Void>{
        private final FichaApuestas mFichaApuestas;

        public RequestApuesta(FichaApuestas fichaApuestas) {
            mFichaApuestas = fichaApuestas;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONArray vectorApuestas = new JSONArray();
            for (Integer apuesta : mFichaApuestas.getListaApuestas()) vectorApuestas.put(apuesta);
            JSONObject logindata = SharedPreferencesUtils.getLoginJsonCopy();
            logindata.getJSONObject("request").put("idpartida", mFichaApuestas.getIdPartida());
            logindata.getJSONObject("request").put("idjornada", mFichaApuestas.getIdJornada());
            logindata.getJSONObject("request").put("apuestas", vectorApuestas);
            return logindata;
        }

        @Override
        Void ParseResponse(JSONObject response) throws Exception {
            return null;
        }
    }

    private class RequestAceptarPartida extends JsonRequest<Void> {
        private final int mIdPartida;

        public RequestAceptarPartida(int idPartida) {
            mIdPartida = idPartida;
        }


        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject requestData = SharedPreferencesUtils.getLoginJsonCopy();
            requestData.getJSONObject("request").put("idpartida", mIdPartida);
            return requestData;
        }

        @Override
        Void ParseResponse(JSONObject response) throws Exception {
            return null;
        }
    }
}
