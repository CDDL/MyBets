package project.catalin.mybets.controladores.controladoresPantallas;

import android.util.Base64;

import project.catalin.mybets.controladores.comunicacionDatos.DataDatosPropios;
import project.catalin.mybets.controladores.comunicacionVista.ViewDatosPropios;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDatosPropios;

/**
 * Created by Trabajo on 09/05/2016.
 */
public class ControladorDatosPropios implements ControllerDatosPropios {
    private final ViewDatosPropios mViewDatosPropios;
    private final DataDatosPropios mDatosPropios;

    public ControladorDatosPropios(ViewDatosPropios viewDatosPropios) {
        mViewDatosPropios = viewDatosPropios;
        mDatosPropios = new GestorDataWebServices();
    }

    @Override
    public void initVista() {
        new TascaInicializarVista().execute();
    }

    @Override
    public void guardarEdicionPerfil() {
        Persona persona = new Persona();
        persona.setUsername(mViewDatosPropios.getValorCampoUsername());
        persona.setNombre(mViewDatosPropios.getValorCampoNombre());
        persona.setImagen(Base64.encodeToString(mViewDatosPropios.getDataIconImagen(), Base64.DEFAULT));

        new TascaGuardarDatosUsuario().execute(persona);
     }

    private class TascaInicializarVista extends ExceptionHandlingAsyncTask<Void, Void, Persona>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewDatosPropios.showLoadingUsuario();
        }

        @Override
        protected Persona executeTask(Void... params) throws Exception {
            return mDatosPropios.getDatosPropios();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewDatosPropios.alert(e.getMessage());
            mViewDatosPropios.dismissLoadingUsuario();
        }

        @Override
        protected void onTaskSuccess(Persona persona) {
            mViewDatosPropios.setDatosUsuario(persona);
            mViewDatosPropios.dismissLoadingUsuario();
        }
    }

    private class TascaGuardarDatosUsuario extends ExceptionHandlingAsyncTask<Persona, Void, Void>{

        @Override
        protected Void executeTask(Persona... params) throws Exception {
            mDatosPropios.guardarEdicionDatos(params[0]);
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {

        }

        @Override
        protected void onTaskSuccess(Void aVoid) {
            GestorEventosUtil.notificarEvento(TipoEvento.DATOS_PROPIOS_ACTUALIZADOS);
        }

    }
}
