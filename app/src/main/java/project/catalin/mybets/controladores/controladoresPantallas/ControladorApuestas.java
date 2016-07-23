package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidas;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
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
    public void inicializarVista() {
        new TascaGetPartidas().execute();
    }

    private class TascaGetPartidas extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>> {

        @Override
        protected void onPreExecute() {
            mVistaPartidas.showLoadingPartidas();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDatosPartidasPopulares.getPartidasPopulares();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mVistaPartidas.dismissLoadingPartidas();
            mVistaPartidas.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mVistaPartidas.setListaPartidas(partidas);
            mVistaPartidas.dismissLoadingPartidas();
        }
    }
}
