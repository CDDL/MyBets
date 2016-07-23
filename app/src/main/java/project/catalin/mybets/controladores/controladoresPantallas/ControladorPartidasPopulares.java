package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPopulares;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.datosWebService.DatosPartidasPopulares;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPopulares;

/**
 * Created by CDD on 19/05/2016.
 */
public class ControladorPartidasPopulares implements ControllerPartidasPopulares {
    private final ViewListaPartidasPopulares mViewListaPartidasPopulares;
    private DataPartidasPopulares mDataPartidasPopulares;

    public ControladorPartidasPopulares(ViewListaPartidasPopulares pantallaPopulares) {
        mViewListaPartidasPopulares = pantallaPopulares;
        mDataPartidasPopulares = new DatosPartidasPopulares();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaPartidasPopulares().execute();
    }

    private class TascaInicializarVistaPartidasPopulares extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>>{
        @Override
        protected void onPreExecute() {
            mViewListaPartidasPopulares.showLoadingPartidas();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDataPartidasPopulares.getPartidasPopulares();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewListaPartidasPopulares.dismissLoadingPartidas();
            mViewListaPartidasPopulares.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mViewListaPartidasPopulares.setListaPartidasPopulares(partidas);
            mViewListaPartidasPopulares.dismissLoadingPartidas();
        }
    }
}
