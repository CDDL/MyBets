package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataJornada;
import project.catalin.mybets.controladores.comunicacionVista.ViewApostar;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerApuesta;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorApuesta implements ControllerApuesta {
    private final ViewApostar mViewApostar;
    private DataJornada mDataJornada;

    public ControladorApuesta(ViewApostar viewApostar) {
        mViewApostar = viewApostar;
        mDataJornada = new GestorDataWebServices();
    }

    @Override
    public void actualizarListaApuestas(int idPartida) {
        new TaskActualizarListaApuestas().execute(idPartida);
    }

    private class TaskActualizarListaApuestas extends AsyncTask<Integer, Void, List<Partido>>{

        private Exception mError;

        @Override
        protected void onPostExecute(List<Partido> partidos) {
            super.onPostExecute(partidos);
            mViewApostar.setListaApuestas(partidos);
            if (mError != null) mViewApostar.alert(mError.getMessage());
            mViewApostar.dismissLoadingJornada();
        }

        @Override
        protected List<Partido> doInBackground(Integer... idPartida) {
            try {
                return mDataJornada.getDatosJornada(idPartida[0]);
            } catch (Exception e) {
                mError = e;
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewApostar.showLoadingJornada();
        }
    }
}
