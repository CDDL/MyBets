package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Categoria;

/**
 * Created by CDD on 17/05/2016.
 */
public interface ViewCategorias {
    void showLoadingCategorias();

    void dismissLoadingCategorias();

    void alert(String message);

    void setListaCategorias(List<Categoria> categorias);
}
