package project.catalin.mybets.controladores.iniciarSesion;

import android.os.AsyncTask;

import project.catalin.mybets.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.comunicación.eventos.INotificable;
import project.catalin.mybets.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.IGestorData;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.objetosData.LoginData;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentFormularioLogin;

/**
 * Created by Catalin on 31/03/2016.
 */
public class ControladorIniciarSesion implements INotificable {

    private final IGestorData mGestorDatos;
    private IniciarSesionFragmentFormularioLogin mFragmentFormularioLogin;

    public ControladorIniciarSesion(IGestorData gestorDatoss) {
        mGestorDatos = gestorDatoss;
        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.PULSADO_BOTON_LOGIN_PANTALLA_INICIO);
    }

    @Override
    public void notificar(final int idEvento, Object info) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                switch (idEvento) {
                    case TipoEvento.PULSADO_BOTON_LOGIN_PANTALLA_INICIO:
                        intentarIdentificarUsuario();
                        break;
                }
                return null;
            }
        }.execute();
    }

    private void intentarIdentificarUsuario() {

        try {

            mGestorDatos.validarIdentificación(new LoginData(mFragmentFormularioLogin.getEmail(), mFragmentFormularioLogin.getPassword()));
            GestorEventosUtil.notificarEvento(TipoEvento.LOGIN_SUCCESS);
        } catch (EmailVacioException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.CAMPO_EMAIL_VACIO);
        } catch (ContraseñaVaciaException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.CAMPO_CONTRASEÑA_VACÍO);
        } catch (ErrorInternoException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.ERROR_INTERNO);
        } catch (EmailMalFormadoException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.CAMPO_EMAIL_MALFORMADO);
        } catch (ErrorServerException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.ERROR_SERVER, e.getMessage());
        }

    }

    public void setFragmentFormularioLogin(IniciarSesionFragmentFormularioLogin fragmentFormularioLogin) {
        mFragmentFormularioLogin = fragmentFormularioLogin;
    }
}
