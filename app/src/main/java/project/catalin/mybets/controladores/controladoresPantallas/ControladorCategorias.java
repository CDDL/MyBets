package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataListaCategorias;
import project.catalin.mybets.controladores.comunicacionVista.ViewCategorias;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Categoria;
import project.catalin.mybets.datos.datosWebService.DatosListaCategorias;
import project.catalin.mybets.vistas.pantallas.categorias.PantallaCategorias;

/**
 * Created by CDD on 17/05/2016.
 */
public class ControladorCategorias implements project.catalin.mybets.vistas.comunicacionControlador.ControllerCategorias {
    private final ViewCategorias mViewCategorias;
    private final DataListaCategorias mDataListaCategorias;

    public ControladorCategorias(ViewCategorias pantallaCategorias) {
        mViewCategorias = pantallaCategorias;
        mDataListaCategorias = new DatosListaCategorias();
    }

    @Override
    public void inicializarVista() {
        new TascaInicializarVistaCategorias().execute();
    }

    private class TascaInicializarVistaCategorias extends ExceptionHandlingAsyncTask<Void, Void, List<Categoria>> {
        @Override
        protected void onPreExecute() {
            mViewCategorias.showLoadingCategorias();
        }

        @Override
        protected List<Categoria> executeTask(Void... params) throws Exception {
            return mDataListaCategorias.getListaCategorias();
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewCategorias.dismissLoadingCategorias();
            mViewCategorias.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Categoria> categorias) {
            mViewCategorias.setListaCategorias(categorias);
            mViewCategorias.dismissLoadingCategorias();
        }
    }
}
