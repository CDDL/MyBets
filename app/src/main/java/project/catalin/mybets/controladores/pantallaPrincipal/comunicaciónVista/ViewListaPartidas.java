package project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónVista;

import java.util.List;

import project.catalin.mybets.datos.objetosData.Partida;

/**
 * Created by Trabajo on 28/04/2016.
 */
public interface ViewListaPartidas {
    void alert(String message);

    void showLoadingPartidas();

    void dismissLoadingPartidas();

    void setListaPartidas(List<Partida> objects);
}
