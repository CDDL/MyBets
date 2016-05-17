package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.EntradaHistorial;

/**
 * Created by Trabajo on 03/05/2016.
 */
public interface ViewHistorial {
    void showLoadingHistorial();

    void dismissLoadingHistorial();

    void alert(String message);

    void setListaHistorial(List<EntradaHistorial> listaHistoriales);
}
