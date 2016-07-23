package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataApuesta;
import project.catalin.mybets.controladores.comunicacionDatos.DataJornada;
import project.catalin.mybets.controladores.comunicacionVista.ViewApostar;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.FichaApuestas;
import project.catalin.mybets.datos.dataObjects.FichaJornada;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.datos.datosWebService.DatosApuesta;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerApuesta;

/**
 * Created by Trabajo on 03/05/2016.
 */
public class ControladorApuesta implements ControllerApuesta {
    private final ViewApostar mViewApostar;
    private final int mIdPartida;
    private final DataApuesta mDataApuesta;
    private DataJornada mDataJornada;

    public ControladorApuesta(ViewApostar viewApostar, int idPartida) {
        mViewApostar = viewApostar;
        mDataJornada = new GestorDataWebServices();
        mDataApuesta = new DatosApuesta();
        mIdPartida = idPartida;
    }

    @Override
    public void inicializarVista() {
        new TaskActualizarListaApuestas().execute(mIdPartida);

    }

    @Override
    public void botonEnviarPulsado() {
        new TaskRealizarApuesta().execute();
    }

    private class TaskActualizarListaApuestas extends ExceptionHandlingAsyncTask<Integer, Void, FichaJornada> {

        @Override
        protected void onPreExecute() {
            mViewApostar.showLoadingJornada();
        }

        @Override
        protected FichaJornada executeTask(Integer... params) throws Exception {
            return mDataJornada.getDatosJornada(params[0]);
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewApostar.dismissLoadingJornada();
            mViewApostar.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(FichaJornada fichaJornada) {
            mViewApostar.setListaApuestas(fichaJornada);
            mViewApostar.dismissLoadingJornada();
            GestorEventosUtil.notificarEvento(TipoEvento.APUESTA_REALIZADA);
        }

    }

    private class TaskRealizarApuesta extends ExceptionHandlingAsyncTask<Void, Void, Void>{
        @Override
        protected Void executeTask(Void... params) throws Exception {
            try{
                mDataApuesta.aceptarPartida(mIdPartida);
            } catch (ErrorServerException ignored) {}
            FichaApuestas fichaApuestas = new FichaApuestas();
            fichaApuestas.setIdPartida(mViewApostar.getIdPartida());
            fichaApuestas.setIdJornada(mViewApostar.getIdJornada());
            fichaApuestas.setListaApuestas(mViewApostar.getListApuestas());
            mDataApuesta.enviarApuesta(fichaApuestas);
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewApostar.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Void aVoid) {
            mViewApostar.alert("Apuesta realizada");
            mViewApostar.mostrarPopUpEnviado();
            GestorEventosUtil.notificarEvento(TipoEvento.APUESTA_REALIZADA);
        }
    }
}
