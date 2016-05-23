package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.FichaSubcategoria;
import project.catalin.mybets.datos.dataObjects.Subcategoria;

/**
 * Created by CDD on 17/05/2016.
 */
public interface ViewSubcategorias {
    void dismissLoadingSubcategoria();

    void showLoadingSubcategoria();

    void alert(String message);

    void setDatos(List<Subcategoria> subcategorias);
}
