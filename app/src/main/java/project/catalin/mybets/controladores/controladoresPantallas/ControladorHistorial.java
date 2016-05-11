package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicaciónDatos.DataHistorialUsuario;
import project.catalin.mybets.controladores.comunicaciónVista.ViewHistorial;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.EntradaHistorial;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerHistorial;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorHistorial implements ControllerHistorial {
    private final ViewHistorial mViewHistorial;
    private final DataHistorialUsuario mDataHistorialUsuario;
    private final int mIdUsuario;

    public ControladorHistorial(ViewHistorial viewHistorial, int idUsuario) {
        mViewHistorial = viewHistorial;
        mDataHistorialUsuario = new GestorDataWebServices();
        mIdUsuario = idUsuario;
    }

    @Override
    public void initVista() {
        new TaskCargarHistorialUsuario().execute(mIdUsuario);
    }

    private class TaskCargarHistorialUsuario extends ExceptionHandlingAsyncTask<Integer, Void, List<EntradaHistorial>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewHistorial.showLoadingHistorial();
        }

        @Override
        protected List<EntradaHistorial> executeTask(Integer... idUsuario) throws Exception {
            return mDataHistorialUsuario.getHistorialUsuario(idUsuario[0]);
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewHistorial.dismissLoadingHistorial();
            mViewHistorial.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<EntradaHistorial> result) {
            mViewHistorial.setListaHistorial(result);
            mViewHistorial.dismissLoadingHistorial();
        }
    }
}
