package project.catalin.mybets.controladores.comunicacionVista;

import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 03/05/2016.
 */
public interface ViewDatosUsuario {
    void showLoadingDatosUsuario();

    void dismissLoadingDatosUsuario();

    void alert(String message);

    void setDatosUsuario(Persona persona);
}
