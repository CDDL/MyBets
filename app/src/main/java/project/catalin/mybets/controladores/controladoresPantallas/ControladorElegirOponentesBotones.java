package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.AsyncTask;

import project.catalin.mybets.controladores.comunicaciónDatos.DataGestionPartidas;
import project.catalin.mybets.controladores.comunicaciónVista.ViewOponentes;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerElegirOponentesBotones;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorElegirOponentesBotones implements ControllerElegirOponentesBotones {
    private final ViewOponentes mViewOponentes;
    private final DataGestionPartidas mDataPartidas;

    public ControladorElegirOponentesBotones(ViewOponentes viewOponentes) {
        mViewOponentes = viewOponentes;
        mDataPartidas = new GestorDataWebServices();
    }

    @Override
    public void botonJuegaYaPulsado() {
        new TascaCrearPartida().execute();
    }

    private class TascaCrearPartida extends AsyncTask<Void, Void, Void> {

        private Exception mError;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mDataPartidas.crearPartida(mViewOponentes.getIdJuego(), mViewOponentes.getListaOponentes());

            } catch (Exception e) {
                mError = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mError == null) {
                GestorEventosUtil.notificarEvento(TipoEvento.PARTIDA_CREADA);
                mViewOponentes.cerrar();
            } else mViewOponentes.alert(mError.getMessage());
        }
    }
}
