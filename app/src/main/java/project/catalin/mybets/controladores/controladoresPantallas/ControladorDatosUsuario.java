package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicacionDatos.DataUsuario;
import project.catalin.mybets.controladores.comunicacionVista.ViewDatosUsuario;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDatosUsuario;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorDatosUsuario implements ControllerDatosUsuario, INotificable {
    private final ViewDatosUsuario mViewDatosUsuario;
    private final DataUsuario mDataUsuario;
    private final int mIdUsuario;
    private final int[] eventosSuscritos = {TipoEvento.DATOS_PROPIOS_ACTUALIZADOS};

    public ControladorDatosUsuario(ViewDatosUsuario viewDatosUsuario, int idUsuario) {
        mViewDatosUsuario = viewDatosUsuario;
        mDataUsuario = new GestorDataWebServices();
        mIdUsuario = idUsuario;
        GestorEventosUtil.suscribirseAEventos(this, eventosSuscritos);
    }

    @Override
    public void initVista() {
        new TaskGetDatosUsuario().execute(mIdUsuario);
    }

    @Override
    public void notificar(int idEvento, Object evento) {
        new TaskGetDatosUsuario().execute(mIdUsuario);
    }

    private class TaskGetDatosUsuario extends ExceptionHandlingAsyncTask<Integer, Void, Persona> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewDatosUsuario.showLoadingDatosUsuario();
        }

        @Override
        protected Persona executeTask(Integer... idUsuario) throws Exception {
            return mDataUsuario.getDatosUsuario(idUsuario[0]);
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewDatosUsuario.dismissLoadingDatosUsuario();
            mViewDatosUsuario.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Persona persona) {
            mViewDatosUsuario.setDatosUsuario(persona);
            mViewDatosUsuario.dismissLoadingDatosUsuario();
        }
    }
}
