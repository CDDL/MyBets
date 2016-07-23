package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataSubcategoria;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPartidasFutbol;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.datosWebService.DatosSubcategoria;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasHoy;

/**
 * Created by CDD on 19/05/2016.
 */
public class ControladorPartidasFutbol implements ControllerPartidasHoy {
    private final DataSubcategoria mDataSubcategoria;
    private final int mIdSubcategoriaFubtol;
    private ViewPantallaPartidasFutbol mViewPantallaFutbol;

    public ControladorPartidasFutbol(ViewPantallaPartidasFutbol pantallaFutbol, int idSubcategoriaFutbol) {
        mViewPantallaFutbol = pantallaFutbol;
        mIdSubcategoriaFubtol = idSubcategoriaFutbol;
        mDataSubcategoria = new DatosSubcategoria();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaPartidasHoy(mIdSubcategoriaFubtol).execute();
    }

    private class TascaInicializarVistaPartidasHoy extends ExceptionHandlingAsyncTask<Void, Void, List<Partida>>{
        private final int mIdSubcategoriaFubtol;

        public TascaInicializarVistaPartidasHoy(int idSubcategoriaFubtol) {
            this.mIdSubcategoriaFubtol = idSubcategoriaFubtol;
        }

        @Override
        protected void onPreExecute() {
            mViewPantallaFutbol.showLoadingPartidas();
        }

        @Override
        protected List<Partida> executeTask(Void... params) throws Exception {
            return mDataSubcategoria.getListPartidosSubcategoriaId(mIdSubcategoriaFubtol);
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewPantallaFutbol.dismissLoadingPartidas();
            mViewPantallaFutbol.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Partida> partidas) {
            mViewPantallaFutbol.setData(partidas);
            mViewPantallaFutbol.dismissLoadingPartidas();
        }
    }
}
