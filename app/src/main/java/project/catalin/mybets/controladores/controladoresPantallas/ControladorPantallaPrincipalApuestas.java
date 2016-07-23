package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicacionDatos.DataPantallaApuestas;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPrincipalApuestas;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.datosWebService.DatosPantallaApuestas;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPantallaPrincipalApuestas;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentApuestas;

/**
 * Created by CDD on 25/05/2016.
 */
public class ControladorPantallaPrincipalApuestas implements ControllerPantallaPrincipalApuestas {
    private final ViewPantallaPrincipalApuestas mViewPantallaPrincipalApuestas;
    private final DataPantallaApuestas mDataPantallaApuestas;

    public ControladorPantallaPrincipalApuestas(ViewPantallaPrincipalApuestas viewPantallaPrincipalApuestas) {
        mDataPantallaApuestas = new DatosPantallaApuestas();
        mViewPantallaPrincipalApuestas = viewPantallaPrincipalApuestas;
        new TascaInicializarIdFutbol().execute();

    }

    @Override
    public void botonCategoriaPulsado() {
        mViewPantallaPrincipalApuestas.iniciarPantallaCategorias();
    }

    @Override
    public void botonPopularesPulsado() {
        mViewPantallaPrincipalApuestas.iniciarPantallaPartidasPopulares();
    }

    @Override
    public void botonFutbolPulsado() {
        mViewPantallaPrincipalApuestas.iniciarPantallaCategoriaFutbol();
    }

    private class TascaInicializarIdFutbol extends ExceptionHandlingAsyncTask<Void, Void, Void>{
        @Override
        protected Void executeTask(Void... params) throws Exception {
            mViewPantallaPrincipalApuestas.setIdFutbol(mDataPantallaApuestas.getIdFutbol());
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewPantallaPrincipalApuestas.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Void aVoid) {

        }
    }
}
