package project.catalin.mybets.controladores.comunicaciónVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 03/05/2016.
 */
public interface ViewOponentes {
    int getIdJuego();

    List<Persona> getListaOponentes();

    void cerrar();

    void alert(String message);
}
