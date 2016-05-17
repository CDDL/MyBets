package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPasadas;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPasadas;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.datosWebService.DatosPartidasPasadas;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPasadas;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class ControladorApuestasPasadas implements ControllerPartidasPasadas {

    private ViewListaPartidasPasadas mViewListaPartidas;
    private DataPartidasPasadas mDataPartidasPasadas;

    public ControladorApuestasPasadas(ViewListaPartidasPasadas viewListaPartidas) {
        mViewListaPartidas = viewListaPartidas;
        mDataPartidasPasadas = new DatosPartidasPasadas();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVista().execute();
    }

    private class TascaInicializarVista extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mViewListaPartidas.showLoadingPartidasPasadas();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDataPartidasPasadas.getPartidasPasadas();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewListaPartidas.dismissLoadingPartidasPasadas();
            mViewListaPartidas.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mViewListaPartidas.setListaPartidasPasadas(partidas);
            mViewListaPartidas.dismissLoadingPartidasPasadas();
        }
    }
}
