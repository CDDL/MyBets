package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.Persona;

/**
 * Created by CDD on 23/05/2016.
 */
public interface ViewPantallaAmigos {
    void showLoadingAmigos();

    void dismissLoadingAmigos();

    void alert(String message);

    void setData(List<Persona> personas);
}
