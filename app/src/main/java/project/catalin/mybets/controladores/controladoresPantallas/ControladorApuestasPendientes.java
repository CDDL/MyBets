package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataPartidasPendientes;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPendientes;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.datosWebService.DatosPartidasPendientes;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPendientes;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class ControladorApuestasPendientes implements ControllerPartidasPendientes, INotificable {

    private final ViewListaPartidasPendientes mViewListaPartidas;
    private final DataPartidasPendientes mDatosPartidasPandientes;
    private final int[] mEventosSuscritos = new int[]{
            TipoEvento.PARTIDA_CREADA,
            TipoEvento.APUESTA_REALIZADA
    };

    public ControladorApuestasPendientes(ViewListaPartidasPendientes viewListaPartidas) {
        mViewListaPartidas = viewListaPartidas;
        mDatosPartidasPandientes = new DatosPartidasPendientes();
        GestorEventosUtil.suscribirseAEventos(this, mEventosSuscritos);
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVista().execute();
    }

    @Override
    public void botonActualizarNombrePulsado() {
        int idPartida = mViewListaPartidas.getIdPartidaActualizar();
        String nuevoNombre = mViewListaPartidas.getNuevoNombrePartida();
        new TascaActualizarNombrePartida(idPartida, nuevoNombre).execute();
    }

    @Override
    public void botonRechazarPartidaPulsado() {
        int idPartida = mViewListaPartidas.getIdPartidaRechazar();
        new TascaRechazarPartida(idPartida).execute();
    }

    @Override
    public void notificar(int idEvento, Object evento) {
        inicializarVista();
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

    private class TascaActualizarNombrePartida extends ExceptionHandlingAsyncTask<String, Void, Void> {
        private final int mIdPartida;
        private final String mNuevoNombre;

        public TascaActualizarNombrePartida(int idPartida, String nuevoNombre) {
            mIdPartida = idPartida;
            mNuevoNombre = nuevoNombre;
        }

        @Override
        protected Void executeTask(String... params) throws Exception {
            mDatosPartidasPandientes.actualizarNombrePartida(mIdPartida, mNuevoNombre);
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewListaPartidas.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Void aVoid) {
            new TascaInicializarVista().execute();
        }
    }

    private class TascaRechazarPartida extends ExceptionHandlingAsyncTask<Void, Void, Void> {
        private final int mIdPartida;

        public TascaRechazarPartida(int idPartida) {
            mIdPartida = idPartida;
        }

        @Override
        protected Void executeTask(Void... params) throws Exception {
            mDatosPartidasPandientes.rechazarPartida(mIdPartida);
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewListaPartidas.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Void aVoid) {
            new TascaInicializarVista().execute();
        }
    }
}
