package project.catalin.mybets.controladores.pantallaPrincipal.fragments;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónVista.ViewListaPartidas;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.objetosData.Partida;

/**
 * Created by Trabajo on 27/04/2016.
 */
public class ControladorFragmentApuestas implements project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador.ControllerPartidasPopulares {

    private DataPartidasPopulares mDatosPartidasPopulares;
    private ViewListaPartidas mVistaPartidas;

    public ControladorFragmentApuestas(ViewListaPartidas vistaPartidas) {
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Partida> partidas) {
            if (partidas == null) {
                mVistaPartidas.alert("Error: " + mError.getMessage());
                mVistaPartidas.setListaPartidas(Collections.<Partida>emptyList());
            } else mVistaPartidas.setListaPartidas(partidas);
            mVistaPartidas.dismissLoadingPartidas();
        }
    }
}
