package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.FichaJornada;
import project.catalin.mybets.datos.dataObjects.Partido;

/**
 * Created by Trabajo on 03/05/2016.
 */
public interface ViewApostar {
    void setListaApuestas(FichaJornada datosJornada);

    void dismissLoadingJornada();

    void alert(String message);

    void showLoadingJornada();

    List<Integer> getListApuestas();

    int getIdJornada();

    int getIdPartida();

    void mostrarPopUpEnviado();
}
