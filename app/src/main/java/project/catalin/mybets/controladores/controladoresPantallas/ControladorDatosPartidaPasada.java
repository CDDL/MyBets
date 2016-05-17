package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataHistorialPartidasPasadas;
import project.catalin.mybets.controladores.comunicacionVista.ViewHistorialJornada;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.FichaHistorial;
import project.catalin.mybets.datos.datosWebService.DatosHistorialPartidasPasadas;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDatosPartidaPasada;

/**
 * Created by CDD on 12/05/2016.
 */
public class ControladorDatosPartidaPasada implements ControllerDatosPartidaPasada {
    private final ViewHistorialJornada mVistaHistorialJornada;
    private final DataHistorialPartidasPasadas mDataHistorialPartidasPasadas;
    private final int mIdPartida;

    public ControladorDatosPartidaPasada(ViewHistorialJornada historialJornada, int idPartida) {
        mVistaHistorialJornada = historialJornada;
        mDataHistorialPartidasPasadas = new DatosHistorialPartidasPasadas();
        mIdPartida = idPartida;
    }

    @Override
    public void inicializarVista() {
        new TascaGetDatosPartidasPasadas().execute(mIdPartida);
    }

    private class TascaGetDatosPartidasPasadas extends ExceptionHandlingAsyncTask<Integer, Void, List<FichaHistorial>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mVistaHistorialJornada.showLoadingPartidasPasadas();
        }

        @Override
        protected List<FichaHistorial> executeTask(Integer... params) throws Exception {
            return mDataHistorialPartidasPasadas.getHistorialDePartidaId(params[0]);
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mVistaHistorialJornada.alert(e.getMessage());
            mVistaHistorialJornada.dismissLoadingPartidasPasadas();
        }

        @Override
        protected void onTaskSuccess(List<FichaHistorial> historialApuestasPartida) {
            mVistaHistorialJornada.setHistorialPartidaPasada(historialApuestasPartida);
            mVistaHistorialJornada.dismissLoadingPartidasPasadas();
        }
    }
}
