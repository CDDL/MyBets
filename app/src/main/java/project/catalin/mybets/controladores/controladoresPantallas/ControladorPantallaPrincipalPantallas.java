package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPrincipalPantallas;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.datosWebService.DatosMiPuntuación;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPantallaPrinciaplPantallas;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorPantallaPrincipalPantallas implements INotificable,ControllerPantallaPrinciaplPantallas {
    private final ViewPantallaPrincipalPantallas mViewPantallas;
    private int[] listaEventos = {
            TipoEvento.PARTIDA_CREADA,
            TipoEvento.APUESTA_REALIZADA,
            TipoEvento.PUNTUACIÓN_UPDATED
    };
    private DatosMiPuntuación mDataMisPuntos;

    public ControladorPantallaPrincipalPantallas(ViewPantallaPrincipalPantallas pantallaPrincipal) {
        mViewPantallas = pantallaPrincipal;
        GestorEventosUtil.suscribirseAEventos(this, listaEventos);
        mDataMisPuntos = new DatosMiPuntuación();
        new TascaGetPuntos().execute();
    }

    @Override
    public void notificar(int idEvento, Object evento) {
        switch (idEvento) {
            case TipoEvento.APUESTA_REALIZADA:
            case TipoEvento.PARTIDA_CREADA:
                mViewPantallas.cambiarAPantallaMisApuestas();
                break;
            case TipoEvento.PUNTUACIÓN_UPDATED:
                mViewPantallas.actualizarPuntuación(((Integer) evento));
                break;
        }
    }

    private class TascaGetPuntos  extends ExceptionHandlingAsyncTask<Void, Void, Integer> {

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
