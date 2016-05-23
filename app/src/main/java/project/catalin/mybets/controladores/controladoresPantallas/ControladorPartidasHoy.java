package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasHoy;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPartidasHoy;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.datosWebService.DatosPartidasHoy;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasHoy;
import project.catalin.mybets.vistas.pantallas.categorias.PantallaHoy;

/**
 * Created by CDD on 19/05/2016.
 */
public class ControladorPartidasHoy implements ControllerPartidasHoy {
    private final DataPartidasHoy mDataPartidasHoy;
    private ViewPantallaPartidasHoy mViewPantallaHoy;

    public ControladorPartidasHoy(ViewPantallaPartidasHoy pantallaHoy) {
        mViewPantallaHoy = pantallaHoy;
        mDataPartidasHoy = new DatosPartidasHoy();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaPartidasHoy().execute();
    }

    private class TascaInicializarVistaPartidasHoy extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>>{
        @Override
        protected void onPreExecute() {
            mViewPantallaHoy.showLoadingPartidas();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDataPartidasHoy.getPartidasHoy();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewPantallaHoy.dismissLoadingPartidas();
            mViewPantallaHoy.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mViewPantallaHoy.setData(partidas);
            mViewPantallaHoy.dismissLoadingPartidas();
        }
    }
}
