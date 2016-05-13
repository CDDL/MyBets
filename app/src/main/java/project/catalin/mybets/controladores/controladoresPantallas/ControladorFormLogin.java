package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.AsyncTask;

import project.catalin.mybets.controladores.comunicaciónDatos.DataIdentificación;
import project.catalin.mybets.controladores.comunicaciónVista.ViewLoginForm;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerFormLogin;

/**
 * Created by Catalin on 31/03/2016.
 */
public class ControladorFormLogin implements ControllerFormLogin {

    private DataIdentificación mGestorCredenciales;
    private ViewLoginForm mLoginView;

    public ControladorFormLogin(ViewLoginForm loginView) {
        mLoginView = loginView;
        mGestorCredenciales = new GestorDataWebServices();
    }

    @Override
    public void destroy() {}

    @Override
    public void botonLoginPulsado() {
        new IdentificaciónTask().execute();
    }

    private class IdentificaciónTask extends AsyncTask<Void, Void, Exception>{
        @Override
        protected void onPostExecute(Exception result) {
            super.onPostExecute(result);
            if(result == null) GestorEventosUtil.notificarEvento(TipoEvento.LOGIN_SUCCESS);
            else if(result instanceof ContraseñaVaciaException) mLoginView.campoContraseñaErroneo("El campo contraseña no puede estar vacia.");
            else if(result instanceof EmailVacioException) mLoginView.campoEmailErroneo("El campo del email no puede estar vacío.");
            else if(result instanceof EmailMalFormadoException) mLoginView.campoEmailErroneo("El email introducido es erroneo.");
            else if(result instanceof ErrorInternoException) GestorEventosUtil.notificarEvento(TipoEvento.ERROR_INTERNO);
            else if(result instanceof ErrorServerException) mLoginView.mensaje(result.getMessage());
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                mGestorCredenciales.validarIdentificación(new LoginData(mLoginView.getEmail(),mLoginView.getPassword()));
                return null;
            } catch (Exception result) {
                return result;
            }
        }
    }
}