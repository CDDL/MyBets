package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPasadas;
import project.catalin.mybets.controladores.comunicacionDatos.DataPuntos;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPasadas;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.datosWebService.DatosMiPuntuación;
import project.catalin.mybets.datos.datosWebService.DatosPartidasPasadas;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPasadas;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class ControladorApuestasPasadas implements ControllerPartidasPasadas {

    private ViewListaPartidasPasadas mViewListaPartidas;
    private DataPartidasPasadas mDataPartidasPasadas;
    private DataPuntos mDataMisPuntos;

    public ControladorApuestasPasadas(ViewListaPartidasPasadas viewListaPartidas) {
        mViewListaPartidas = viewListaPartidas;
        mDataPartidasPasadas = new DatosPartidasPasadas();
        mDataMisPuntos = new DatosMiPuntuación();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVista().execute();
        new TascaGetPuntos().execute();
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

    private class TascaGetPuntos  extends ExceptionHandlingAsyncTask<Void, Void, Integer>{

        @Override
        protected Integer executeTask(Void... params) throws Exception {
            return mDataMisPuntos.obtenerMisPuntos();
        }

        @Override
        protected void onTaskFailture(Exception e) {

        }


        @Override
        protected void onTaskSuccess(Integer puntos) {
            GestorEventosUtil.notificarEvento(TipoEvento.PUNTUACIÓN_UPDATED, puntos);
        }

    }
}
