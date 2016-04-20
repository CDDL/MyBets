package project.catalin.mybets.controladores.pantallaPrincipal.fragments;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;
import project.catalin.mybets.controladores.utils.comunicación.eventos.TipoEvento;
import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónDatos.DataContacts;
import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónVista.MisAmigosView;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador.ControllerMisAmigos;

/**
 * Created by Catalin on 05/04/2016.
 */
public class ControladorMisAmigos implements INotificable, ControllerMisAmigos {
    private DataContacts mDataContactos;
    private MisAmigosView mListaAmigosView;
    private int[] eventosSuscritos = {
            TipoEvento.AMIGO_NUEVO,
            TipoEvento.AMIGO_BORRADO};

    public ControladorMisAmigos(MisAmigosView listaAmigos) {
        mListaAmigosView = listaAmigos;
        mDataContactos = new GestorDataWebServices();

        GestorEventosUtil.suscribirseAEventos(this, eventosSuscritos);
    }

    @Override
    public List<Persona> getContactos() {
        final List<Persona> contactos = new ArrayList<>();
        new GetContactosTeask(contactos).execute();

        return contactos;
    }

    @Override
    public void notificar(final int idEvento, final Object event) {
        mListaAmigosView.recargarAmigos();
    }

    @Override
    public void destroy() {
        GestorEventosUtil.desuscribirseDeEventos(this, eventosSuscritos);
    }

    private class GetContactosTeask extends AsyncTask<Void, Void, Exception> {
        private final List<Persona> mContactos;

        public GetContactosTeask(List<Persona> contactos) {
            mContactos = contactos;
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                mContactos.addAll(mDataContactos.getContactos());
                return null;
            } catch (Exception e){
                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception result) {
            super.onPostExecute(result);
            if(result == null) return;
            else if(result instanceof UsuarioNoIdentificadoException) GestorEventosUtil.notificarEvento(TipoEvento.USUARIO_NO_IDENTIFICADO);
            else if(result instanceof ErrorInternoException) GestorEventosUtil.notificarEvento(TipoEvento.ERROR_INTERNO);
            else if(result instanceof ErrorServerException) mListaAmigosView.mensaje(result.getMessage());
        }
    }
}
