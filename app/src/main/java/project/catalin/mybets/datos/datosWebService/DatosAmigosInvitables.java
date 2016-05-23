package project.catalin.mybets.datos.datosWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataAmigosInvitables;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Contacto;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 23/05/2016.
 */
public class DatosAmigosInvitables implements DataAmigosInvitables {

    @Override
    public List<Persona> getListaAmigosInvitables(List<Contacto> contactos) throws ErrorInternoException, ErrorServerException {
        return new RequestGetAmigosInvitables(contactos).sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_AMIGOS_INVITABLES);
    }

    private class RequestGetAmigosInvitables extends JsonRequest<List<Persona>> {
        private final List<Contacto> mListaContactos;

        public RequestGetAmigosInvitables(List<Contacto> contactos) {
            mListaContactos = contactos;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject jsonRequest = SharedPreferencesUtils.getLoginJsonCopy();

            JSONArray jsonArrayEmails = new JSONArray();
            JSONArray jsonArrayTelefonos = new JSONArray();
            for (Contacto contacto : mListaContactos) {
                for (String email : contacto.getListaEmails()) jsonArrayEmails.put(email);
                for (String telefono : contacto.getListaTelefonos())
                    jsonArrayTelefonos.put(telefono);
            }

            jsonRequest.put("emails", jsonArrayEmails);
            jsonRequest.put("telefonos", jsonArrayTelefonos);

            return jsonRequest;
        }

        @Override
        List<Persona> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToPersonaList(response);
        }
    }
}
