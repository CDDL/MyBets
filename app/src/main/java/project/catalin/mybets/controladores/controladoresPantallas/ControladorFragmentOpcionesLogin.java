package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicacionVista.ViewFragmentsLogin;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFragmentOpcionesLogin;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments.IniciarSesionFragmentBase;

/**
 * Created by CDD on 24/05/2016.
 */
public class ControladorFragmentOpcionesLogin implements ControllerFragmentOpcionesLogin, INotificable {
    private final ViewFragmentsLogin mViewFragmentsLogin;
    private final int[] eventosSuscritos = new int[]{
            TipoEvento.BOTON_LOGIN_EMAIL_PULSADO,
            TipoEvento.BOTON_LOGIN_FACEBOOK_PULSADO,
            TipoEvento.BOTON_LOGIN_TEXT_PULSADO,
    };


    public ControladorFragmentOpcionesLogin(ViewFragmentsLogin viewFragmentsLogin) {
        mViewFragmentsLogin = viewFragmentsLogin;
        GestorEventosUtil.suscribirseAEventos(this, eventosSuscritos);

    }

    @Override
    public void notificar(int idEvento, Object evento) {
        switch (idEvento) {
            case TipoEvento.BOTON_LOGIN_TEXT_PULSADO:
                mViewFragmentsLogin.cambiarFragmentALogin();
                break;
        }
    }
}
