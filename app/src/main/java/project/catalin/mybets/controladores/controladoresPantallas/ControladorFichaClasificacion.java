package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.Collections;
import java.util.Comparator;

import project.catalin.mybets.controladores.comunicacionDatos.DataClasificacionPartida;
import project.catalin.mybets.controladores.comunicacionVista.ViewFichaClasificacion;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.EntradaClasificacion;
import project.catalin.mybets.datos.dataObjects.FichaClasificacion;
import project.catalin.mybets.datos.datosWebService.DatosClasificacionPartida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFichaClasificacion;

/**
 * Created by CDD on 16/05/2016.
 */
public class ControladorFichaClasificacion implements ControllerFichaClasificacion {
    private ViewFichaClasificacion mViewFichaClasificacion;
    private DataClasificacionPartida mDataClasificacionPartida;
    private int mIdPartida;

    public ControladorFichaClasificacion(ViewFichaClasificacion fichaClasificacion, int idPartida) {
        mViewFichaClasificacion = fichaClasificacion;
        mDataClasificacionPartida = new DatosClasificacionPartida();
        mIdPartida = idPartida;
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaClasificacion(mIdPartida).execute();
    }

    private class TascaInicializarVistaClasificacion extends ExceptionHandlingAsyncTask<Void,Void,FichaClasificacion> {
        private int mIdPartida;

        public TascaInicializarVistaClasificacion(int idPartida) {
            mIdPartida = idPartida;
        }

        @Override
        protected void onPreExecute() {
            mViewFichaClasificacion.showLoadingClasificacion();
        }

        @Override
        protected FichaClasificacion executeTask(Void... params) throws Exception {
            return mDataClasificacionPartida.getClasificacion(mIdPartida);
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewFichaClasificacion.dismissLoadingClasificacion();
            mViewFichaClasificacion.alert(e.getMessage());

        }

        @Override
        protected void onTaskSuccess(FichaClasificacion fichaClasificacion) {
            Collections.sort(fichaClasificacion.getEntradas(), new Comparator<EntradaClasificacion>() {
                @Override
                public int compare(EntradaClasificacion lhs, EntradaClasificacion rhs) {
                    return lhs.getPosicion() - rhs.getPosicion();
                }
            });
            mViewFichaClasificacion.setDatosClasificacion(fichaClasificacion);
            mViewFichaClasificacion.dismissLoadingClasificacion();
        }
    }
}
