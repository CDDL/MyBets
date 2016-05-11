package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.controladores.comunicaciónDatos.DataContacts;
import project.catalin.mybets.controladores.comunicaciónVista.ViewListaAmigos;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerListaAmigos;

/**
 * Created by Catalin on 05/04/2016.
 */
public class ControladorAmigos implements INotificable, ControllerListaAmigos {
    private DataContacts mDataContactos;
    private ViewListaAmigos mVistaListaAmigos;
    private int[] eventosSuscritos = {
            TipoEvento.AMIGO_NUEVO,
            TipoEvento.AMIGO_BORRADO};

    public ControladorAmigos(ViewListaAmigos listaAmigos) {
        mVistaListaAmigos = listaAmigos;
        mDataContactos = new GestorDataWebServices();

        GestorEventosUtil.suscribirseAEventos(this, eventosSuscritos);
    }

    @Override
    public void getContactos() {
        new GetContactosTask().execute();
    }

    @Override
    public void notificar(final int idEvento, final Object event) {
        getContactos();
    }

    @Override
    public void destroy() {
        GestorEventosUtil.desuscribirseDeEventos(this, eventosSuscritos);
    }

    private class GetContactosTask extends AsyncTask<Void, Void, List<Persona>> {

        private Exception mError;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mVistaListaAmigos.showLoadgingAmigos();
        }

        @Override
        protected List<Persona> doInBackground(Void... params) {
            try {
                return mDataContactos.getContactos();
            } catch (Exception e){
                mError = e;
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPostExecute(List<Persona> result) {
            mVistaListaAmigos.setListaAmigos(result);
            if(mError != null) mVistaListaAmigos.alert(mError.getMessage());
            mVistaListaAmigos.dismissLoadingAmigos();
        }
    }
}
