package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.controladores.comunicaciónVista.ViewLoginBase;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerDestructible;

/**
 * Created by Trabajo on 18/04/2016.
 */
public class ControladorIniciarSesionPantallas implements INotificable, ControllerDestructible {

    ViewLoginBase mViewLoginBase;
    int[] mListaEventos = {
            TipoEvento.BOTON_LOGIN_TEXT_PULSADO,
            TipoEvento.BOTON_REGISTER_TEXT_PULSADO,
            TipoEvento.LOGIN_SUCCESS};

    public ControladorIniciarSesionPantallas(ViewLoginBase viewLoginBase) {
        mViewLoginBase = viewLoginBase;
        GestorEventosUtil.suscribirseAEventos(this, mListaEventos);
    }


    @Override
    public void notificar(int idEvento, Object evento) {
        switch (idEvento){
            case TipoEvento.BOTON_LOGIN_TEXT_PULSADO:
                mViewLoginBase.cambiarFragmentALoginForm();
                break;
            case TipoEvento.BOTON_LOGIN_FACEBOOK_PULSADO:
                break;
            case TipoEvento.BOTON_LOGIN_EMAIL_PULSADO:
                break;
            case TipoEvento.BOTON_REGISTER_TEXT_PULSADO:
                mViewLoginBase.cambiarFragmentARegisterForm();
                break;
            case TipoEvento.LOGIN_SUCCESS:
                mViewLoginBase.iniciarActivityPantallaPrincipal();
                break;
        }
    }

    @Override
    public void destroy() {
        GestorEventosUtil.desuscribirseDeEventos(this, mListaEventos);
    }
}
