package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Subcategoria;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 17/05/2016.
 */
public interface DataCategorias {
    List<Subcategoria> getListSubcategorias(int mIdCategoria) throws ErrorInternoException, ErrorServerException;

    Subcategoria getSubcategoriaPopular(int mIdCategoria) throws ErrorInternoException, ErrorServerException;
}
