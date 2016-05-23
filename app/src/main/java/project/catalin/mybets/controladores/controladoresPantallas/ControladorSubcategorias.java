package project.catalin.mybets.controladores.controladoresPantallas;

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataSubcategoria;
import project.catalin.mybets.controladores.comunicacionDatos.DataCategorias;
import project.catalin.mybets.controladores.comunicacionVista.ViewSubcategorias;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Subcategoria;
import project.catalin.mybets.datos.datosWebService.DatosCategorias;
import project.catalin.mybets.datos.datosWebService.DatosSubcategoria;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerSubcategorias;

/**
 * Created by CDD on 17/05/2016.
 */
public class ControladorSubcategorias implements ControllerSubcategorias {
    private final ViewSubcategorias mViewSubcategorias;
    private final DataSubcategoria mDataSubcategoria;
    private final DataCategorias mDataCategorias;
    private int mIdSubcategoria;

    public ControladorSubcategorias(ViewSubcategorias pantallaSubCategoria, int idSubcategoria) {
        mViewSubcategorias = pantallaSubCategoria;
        mDataCategorias = new DatosCategorias();
        mDataSubcategoria = new DatosSubcategoria();
        mIdSubcategoria = idSubcategoria;
    }



    @Override
    public void inicializarVista() {
        new TascaInicializarVistaSubcategoria(mIdSubcategoria).execute();
    }

    private class TascaInicializarVistaSubcategoria extends ExceptionHandlingAsyncTask<Void, Void, List<Subcategoria>> {
        private int mIdCategoria;

        public TascaInicializarVistaSubcategoria(int idSubcategoria) {
            mIdCategoria = idSubcategoria;
        }

        @Override
        protected void onPreExecute() {
            mViewSubcategorias.showLoadingSubcategoria();
        }

        @Override
        protected List<Subcategoria> executeTask(Void... params) throws Exception {
            List<Subcategoria> listSubcategorias = mDataCategorias.getListSubcategorias(mIdCategoria);
            for (Subcategoria subcategoria: listSubcategorias)
                subcategoria.setListPartidas(mDataSubcategoria.getListPartidosSubcategoriaId(subcategoria.getId()));
            listSubcategorias.add(0, mDataCategorias.getSubcategoriaPopular(mIdCategoria));

            return listSubcategorias;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewSubcategorias.dismissLoadingSubcategoria();
            mViewSubcategorias.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Subcategoria> fichaSubcategoria) {
            mViewSubcategorias.setDatos(fichaSubcategoria);
            mViewSubcategorias.dismissLoadingSubcategoria();
        }
    }
}
