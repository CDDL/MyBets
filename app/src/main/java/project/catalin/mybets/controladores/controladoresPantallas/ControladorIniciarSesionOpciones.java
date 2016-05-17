package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerOpcionesLogin;

/**
 * Created by Trabajo on 18/04/2016.
 */
public class ControladorIniciarSesionOpciones implements ControllerOpcionesLogin {

    @Override
    public void botonLoginTextPulsado() {
        GestorEventosUtil.notificarEvento(TipoEvento.BOTON_LOGIN_TEXT_PULSADO);
    }

    @Override
    public void botonRegisterPulsado() {
        GestorEventosUtil.notificarEvento(TipoEvento.BOTON_REGISTER_TEXT_PULSADO);
    }

    @Override
    public void botonLoginFacebookPulsado() {
        GestorEventosUtil.notificarEvento(TipoEvento.BOTON_LOGIN_FACEBOOK_PULSADO);
    }

    @Override
    public void botonLoginMailPulsado() {
        GestorEventosUtil.notificarEvento(TipoEvento.BOTON_LOGIN_EMAIL_PULSADO);
    }

    @Override
    public void destroy() {}
}
