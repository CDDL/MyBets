package project.catalin.mybets.controladores.comunicaciónVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by Trabajo on 10/05/2016.
 */
public interface ViewListaPartidasPasadas {
    void showLoadingPartidasPasadas();

    void dismissLoadingPartidasPasadas();

    void alert(String message);

    void setListaPartidasPasadas(List<Partida> partidas);
}
