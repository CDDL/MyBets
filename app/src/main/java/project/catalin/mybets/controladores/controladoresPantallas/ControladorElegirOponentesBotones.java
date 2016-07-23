package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.AsyncTask;

import project.catalin.mybets.controladores.comunicacionDatos.DataGestionPartidas;
import project.catalin.mybets.controladores.comunicacionVista.ViewOponentes;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerElegirOponentesBotones;

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

    private class TascaCrearPartida extends ExceptionHandlingAsyncTask<Void, Void,Void> {

        @Override
        protected Void executeTask(Void... params) throws Exception {
            mDataPartidas.crearPartida(mViewOponentes.getIdJuego(), mViewOponentes.getListaOponentes());
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewOponentes.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Void aVoid) {
            GestorEventosUtil.notificarEvento(TipoEvento.PARTIDA_CREADA);
            mViewOponentes.cerrar();
        }
    }
}
