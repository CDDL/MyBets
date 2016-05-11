package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicaciónVista.ViewPantallaPrincipalPantallas;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerPantallaPrinciaplPantallas;
import project.catalin.mybets.vistas.pantallas.principal.PantallaPrincipal;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorPantallaPrincipalPantallas implements INotificable,ControllerPantallaPrinciaplPantallas {
    private final ViewPantallaPrincipalPantallas mViewPantallas;
    private int[] listaEventos = {
            TipoEvento.PARTIDA_CREADA
    };

    public ControladorPantallaPrincipalPantallas(ViewPantallaPrincipalPantallas pantallaPrincipal) {
        mViewPantallas = pantallaPrincipal;
        GestorEventosUtil.suscribirseAEventos(this, listaEventos);
    }

    @Override
    public void notificar(int idEvento, Object evento) {
        mViewPantallas.cambiarAPantallaMisApuestas();
    }
}
