package project.catalin.mybets.controladores.comunicaciónVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 15/04/2016.
 */
public interface ViewListaAmigos {
    void setListaAmigos(List<Persona> personas);

    void alert(String message);

    void showLoadgingAmigos();

    void dismissLoadingAmigos();
}
