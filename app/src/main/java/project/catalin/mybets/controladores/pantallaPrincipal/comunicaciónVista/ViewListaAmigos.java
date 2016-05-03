package project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónVista;

import project.catalin.mybets.datos.objetosData.Persona;

/**
 * Created by Trabajo on 15/04/2016.
 */
public interface ViewListaAmigos {
    void add(Persona persona);

    void remove(Persona persona);

    void recargarAmigos();

    void mensaje(String message);
}
