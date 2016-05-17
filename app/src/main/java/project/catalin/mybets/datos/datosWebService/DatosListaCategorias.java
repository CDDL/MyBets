package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataListaCategorias;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Categoria;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 17/05/2016.
 */
public class DatosListaCategorias implements DataListaCategorias {
    @Override
    public List<Categoria> getListaCategorias() throws ErrorInternoException, ErrorServerException {
        return new RequestListaCategorias().sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_LISTA_CATEGORIAS);
    }

    private class RequestListaCategorias extends JsonRequest<List<Categoria>> {
        @Override
        JSONObject CreateRequest() throws Exception {
            return SharedPreferencesUtils.getLoginJsonCopy();
        }

        @Override
        List<Categoria> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToCategoriaList(response);
        }
    }
}
