package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by CDD on 19/05/2016.
 */
public interface ViewListaPartidasPopulares {
    void showLoadingPartidas();

    void dismissLoadingPartidas();

    void alert(String message);

    void setListaPartidasPopulares(List<Partida> partidas);
}
