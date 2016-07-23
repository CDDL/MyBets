package project.catalin.mybets.controladores.comunicacionVista;

import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by Trabajo on 09/05/2016.
 */
public interface ViewDatosPropios {
    void showLoadingUsuario();

    void alert(String message);

    void dismissLoadingUsuario();

    void setDatosUsuario(Persona persona);

    byte[] getDataIconImagen();

    String getValorCampoNombre();

    String getValorCampoUsername();

    void cerrar();
}
