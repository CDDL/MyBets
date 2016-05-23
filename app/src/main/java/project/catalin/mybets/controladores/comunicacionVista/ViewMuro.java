package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.EntradaMuro;

/**
 * Created by CDD on 19/05/2016.
 */
public interface ViewMuro {
    void showLoadingEntradas();

    void dismissLoadingEntradas();

    void alert(String message);

    void setData(List<EntradaMuro> entradaMuros);
}
