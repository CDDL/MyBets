package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataMuro;
import project.catalin.mybets.controladores.comunicacionVista.ViewMuro;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.EntradaMuro;
import project.catalin.mybets.datos.datosWebService.DatosMuro;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerMuro;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentMuro;

/**
 * Created by CDD on 19/05/2016.
 */
public class ControladorMuro implements ControllerMuro {


    private final ViewMuro mViewMuro;
    private final DataMuro mDataMuro;

    public ControladorMuro(ViewMuro viewMuro) {
        mViewMuro = viewMuro;
        mDataMuro = new DatosMuro();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaMuro().execute();
    }

    private class TascaInicializarVistaMuro extends ExceptionHandlingAsyncTask<Void, Void, List<EntradaMuro>>{
        @Override
        protected void onPreExecute() {
            mViewMuro.showLoadingEntradas();
        }

        @Override
        protected List<EntradaMuro> executeTask(Void... params) throws Exception {
            return mDataMuro.getDataMuro();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewMuro.dismissLoadingEntradas();
            mViewMuro.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<EntradaMuro> entradaMuros) {
            mViewMuro.setData(entradaMuros);
            mViewMuro.dismissLoadingEntradas();
        }
    }
}
