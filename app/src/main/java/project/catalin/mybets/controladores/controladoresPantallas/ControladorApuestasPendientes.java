package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicaciónDatos.DataPartidasPendientes;
import project.catalin.mybets.controladores.comunicaciónVista.ViewListaPartidasPendientes;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.datosWebService.DatosPartidasPendientes;
import project.catalin.mybets.datos.dataObjects.Partida;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class ControladorApuestasPendientes implements project.catalin.mybets.vistas.comunicaciónControlador.ControllerPartidasPendientes {

    private final ViewListaPartidasPendientes mViewListaPartidas;
    private final DataPartidasPendientes mDatosPartidasPandientes;

    public ControladorApuestasPendientes(ViewListaPartidasPendientes viewListaPartidas) {
        mViewListaPartidas = viewListaPartidas;
        mDatosPartidasPandientes = new DatosPartidasPendientes();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVista().execute();
    }

    private class TascaInicializarVista extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewListaPartidas.showLoadingPartidasPendientes();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDatosPartidasPandientes.getPartidasPendientes();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewListaPartidas.dismissLoadingPartidasPendientes();
            mViewListaPartidas.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mViewListaPartidas.setListaPartidasPendientes(partidas);
            mViewListaPartidas.dismissLoadingPartidasPendientes();
        }
    }
}
