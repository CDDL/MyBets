package project.catalin.mybets.datos.datosWebService;

import org.json.JSONObject;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataCategorias;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Subcategoria;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;

/**
 * Created by CDD on 17/05/2016.
 */
public class DatosCategorias implements DataCategorias {
    @Override
    public List<Subcategoria> getListSubcategorias(int idCategoria) throws ErrorInternoException, ErrorServerException {
        return new RequestListSubcategorias(idCategoria).sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_LISTA_SUBCATEGORIAS);
    }

    @Override
    public Subcategoria getSubcategoriaPopular(int idCategoria) throws ErrorInternoException, ErrorServerException {
        return new RequestSubcategoriaPopular(idCategoria).sendRequest(GestorDataWebServices.URL_PETICIÓN_GET_LISTA_SUBCATEGORIAS);
    }

    private class RequestListSubcategorias extends JsonRequest<List<Subcategoria>> {
        private final int mIdCategoria;

        public RequestListSubcategorias(int idCategoria) {
            mIdCategoria = idCategoria;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject requestData = SharedPreferencesUtils.getLoginJsonCopy();
            requestData.getJSONObject("request").put("idcategoria", mIdCategoria);
            return requestData;
        }

        @Override
        List<Subcategoria> ParseResponse(JSONObject response) throws Exception {
            return JsonParserUtils.jsonToCategoriasList(response);
        }
    }

    private class RequestSubcategoriaPopular extends  JsonRequest<Subcategoria> {
        private final int mIdCategoria;

        public RequestSubcategoriaPopular(int idCategoria) {
            mIdCategoria = idCategoria;
        }

        @Override
        JSONObject CreateRequest() throws Exception {
            JSONObject requestData = SharedPreferencesUtils.getLoginJsonCopy();
            requestData.getJSONObject("request").put("idcategoria", mIdCategoria);
            return requestData;
        }

        @Override
        Subcategoria ParseResponse(JSONObject response) throws Exception {
            Subcategoria subcategoria =  JsonParserUtils.jsonToCategoria(response);
            subcategoria.setNombreSubcategoria("POPULARES");
            return subcategoria;
        }
    }
}
