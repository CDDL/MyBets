package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by Trabajo on 28/04/2016.
 */
public interface ViewListaPartidas {
    void alert(String message);

    void showLoadingPartidas();

    void dismissLoadingPartidas();

    void setListaPartidas(List<Partida> objects);
}
