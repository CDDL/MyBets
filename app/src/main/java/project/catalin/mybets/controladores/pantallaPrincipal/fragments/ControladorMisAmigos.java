package project.catalin.mybets.controladores.pantallaPrincipal.fragments;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.comunicación.eventos.INotificable;
import project.catalin.mybets.comunicación.eventos.TipoEvento;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.IGestorData;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.UsuarioNoIdentificadoException;
import project.catalin.mybets.datos.objetosData.Persona;

/**
 * Created by Catalin on 05/04/2016.
 */
public class ControladorMisAmigos implements INotificable {
    private final IGestorData mGestorDatos;
    private MisAmigosView mListaAmigosView;

    public ControladorMisAmigos(MisAmigosView listaAmigos) {
        mListaAmigosView = listaAmigos;
        mGestorDatos = new GestorDataWebServices();

        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.AMIGO_NUEVO);
    }

    public List<Persona> getContactos() {
        try {
            return mGestorDatos.getContactos();
        } catch (UsuarioNoIdentificadoException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.USUARIO_NO_IDENTIFICADO);
        } catch (ErrorInternoException e) {
            GestorEventosUtil.notificarEvento(TipoEvento.ERROR_INTERNO);
        } catch (ErrorServerException e){
            GestorEventosUtil.notificarEvento(TipoEvento.ERROR_SERVER, e.getMessage());
        }
        return new LinkedList<>();
    }

    @Override
    public void notificar(final int idEvento, Object mensaje) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                switch (idEvento){
                    case TipoEvento.AMIGO_NUEVO:
                        List<Persona> listaPersonas = getContactos();
                        Persona ultima = listaPersonas.get(listaPersonas.size()-1);
                        mListaAmigosView.add(ultima);
                        break;
                    case TipoEvento.AMIGO_BORRADO:

                }
            }
        }.execute();
    }
}
