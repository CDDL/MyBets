package project.catalin.mybets.controladores.comunicacionVista;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.FichaHistorial;

/**
 * Created by CDD on 12/05/2016.
 */
public interface ViewHistorialJornada {
    void showLoadingPartidasPasadas();

    void alert(String message);

    void dismissLoadingPartidasPasadas();

    void setHistorialPartidaPasada(List<FichaHistorial> historialApuestasPartida);
}
