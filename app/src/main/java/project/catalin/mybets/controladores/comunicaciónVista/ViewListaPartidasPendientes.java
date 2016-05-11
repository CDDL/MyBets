package project.catalin.mybets.controladores.comunicaciónVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by Trabajo on 10/05/2016.
 */
public interface ViewListaPartidasPendientes {
    void showLoadingPartidasPendientes();

    void dismissLoadingPartidasPendientes();

    void alert(String message);

    void setListaPartidasPendientes(List<Partida> partidas);
}
