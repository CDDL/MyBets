package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidas;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPopulares;

/**
 * Created by Trabajo on 27/04/2016.
 */
public class ControladorApuestas implements ControllerPartidasPopulares {

    private DataPartidasPopulares mDatosPartidasPopulares;
    private ViewListaPartidas mVistaPartidas;

    public ControladorApuestas(ViewListaPartidas vistaPartidas) {
        mDatosPartidasPopulares = new GestorDataWebServices();
        mVistaPartidas = vistaPartidas;
    }

    @Override
    public void getPartidasPopulares() {
        new TascaGetPartidas().execute();
    }


    private class TascaGetPartidas extends AsyncTask<Void, Void, List<Partida>> {
        private Exception mError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mVistaPartidas.showLoadingPartidas();
        }

        @Override
        protected List<Partida> doInBackground(Void... params) {
            try {
                return mDatosPartidasPopulares.getPartidasPopulares();
            } catch (Exception e) {
                mError = e;
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPostExecute(List<Partida> partidas) {
            mVistaPartidas.setListaPartidas(partidas);
            if (mError != null) mVistaPartidas.alert("Error: " + mError.getMessage());
            mVistaPartidas.dismissLoadingPartidas();
        }
    }
}
