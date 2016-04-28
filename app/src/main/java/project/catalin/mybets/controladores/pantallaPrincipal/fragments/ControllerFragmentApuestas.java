package project.catalin.mybets.controladores.pantallaPrincipal.fragments;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.objetosData.Partida;

/**
 * Created by Trabajo on 27/04/2016.
 */
public class ControllerFragmentApuestas implements project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador.ControllerPartidasPopulares {

    private DataPartidasPopulares mDatosPartidasPopulares;

    public ControllerFragmentApuestas() {
        mDatosPartidasPopulares = new GestorDataWebServices();
    }

    @Override
    public List<Partida> getPartidasPopulares() {
        try {
            return mDatosPartidasPopulares.getPartidasPopulares();
        } catch (ErrorInternoException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.ERROR_INTERNO);
        }
        return new Collections.emptyList();
    }
}
