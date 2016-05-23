package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPopulares;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPartidasPopulares;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.datosWebService.DatosPartidasPopulares;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPopulares;
import project.catalin.mybets.vistas.pantallas.categorias.PantallaPopulares;

/**
 * Created by CDD on 19/05/2016.
 */
public class ControladorPartidasPopulares implements ControllerPartidasPopulares {
    private final ViewPantallaPartidasPopulares mViewPantallaPartidasPopulares;
    private DataPartidasPopulares mDataPartidasPopulares;

    public ControladorPartidasPopulares(ViewPantallaPartidasPopulares pantallaPopulares) {
        mViewPantallaPartidasPopulares = pantallaPopulares;
        mDataPartidasPopulares = new DatosPartidasPopulares();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaPartidasPopulares().execute();
    }

    private class TascaInicializarVistaPartidasPopulares extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>>{
        @Override
        protected void onPreExecute() {
            mViewPantallaPartidasPopulares.showLoadingPartidas();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDataPartidasPopulares.getPartidasPopulares();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewPantallaPartidasPopulares.dismissLoadingPartidas();
            mViewPantallaPartidasPopulares.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mViewPantallaPartidasPopulares.setData(partidas);
            mViewPantallaPartidasPopulares.dismissLoadingPartidas();
        }
    }
}
