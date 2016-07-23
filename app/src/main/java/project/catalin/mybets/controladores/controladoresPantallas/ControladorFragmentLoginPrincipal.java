package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicacionVista.ViewFragmentLoginPrincipal;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFragmentLoginPrincipal;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.IniciarSesionPantallaPrincipal;

/**
 * Created by CDD on 24/05/2016.
 */
public class ControladorFragmentLoginPrincipal implements ControllerFragmentLoginPrincipal, INotificable {
    private final ViewFragmentLoginPrincipal mViewFragmentLoginPrincipal;
    private final int[] eventosSuscritos = new int[]{
            TipoEvento.BOTON_REGISTER_TEXT_PULSADO
    };

    public ControladorFragmentLoginPrincipal(ViewFragmentLoginPrincipal viewFragmentLoginPrincipal) {
        mViewFragmentLoginPrincipal = viewFragmentLoginPrincipal;
        GestorEventosUtil.suscribirseAEventos(this, eventosSuscritos);
    }



    @Override
    public void notificar(int idEvento, Object evento) {
        switch (idEvento) {
            case TipoEvento.BOTON_REGISTER_TEXT_PULSADO:
                mViewFragmentLoginPrincipal.cambiarFragmentARegister();
        }
    }
}
