package project.catalin.mybets.controladores.controladoresPantallas;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.controladores.comunicacionDatos.DataAmigosInvitables;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaAmigos;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.Contacto;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.datosWebService.DatosAmigosInvitables;
import project.catalin.mybets.vistas.ContextCreator;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerInvitarAmigos;

/**
 * Created by CDD on 23/05/2016.
 */
public class ControladorInvitarAmigos implements ControllerInvitarAmigos {

    private final ViewPantallaAmigos mViewPantallaAmigos;
    private final Context mContext;
    private DataAmigosInvitables mDataAmigosInvitables;
    private List<Contacto> mListaContactos;

    public ControladorInvitarAmigos(ViewPantallaAmigos viewPantallaAmigos) {
        mViewPantallaAmigos = viewPantallaAmigos;
        mDataAmigosInvitables = new DatosAmigosInvitables();
        mContext = ContextCreator.getAppContext();
    }

    @Override
    public void inicializarVista() {
        mListaContactos = obtenerContactos();
        new TascaInicializarVistaInvitarAmigos(mListaContactos).execute();
    }

    private List<Contacto> obtenerContactos() {
        Cursor cursorContactos = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        List<Contacto> listaContactos = new LinkedList<>();
        while (cursorContactos.moveToNext()) {
            String idContacto = cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts._ID));
            String nombre = cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String foto = cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setImagen(foto);
            persona.setEstaEnSistema(false);

            Contacto contacto = new Contacto();
            contacto.setTelefonos(obtenerTelefonos(idContacto));
            contacto.setEmails(obtenerEmails(idContacto));
            contacto.setPersona(persona);

            listaContactos.add(contacto);
        }
        return listaContactos;
    }

    private List<String> obtenerEmails(String idContacto) {
        Cursor cursorEmails = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{idContacto}, null);
        List<String> listaEmails = new LinkedList<>();
        while (cursorEmails.moveToNext()) {
            String email = cursorEmails.getString(cursorEmails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            listaEmails.add(email);
        }
        cursorEmails.close();

        return listaEmails;
    }

    private List<String> obtenerTelefonos(String idContacto) {
        Cursor cursorTelefonos = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{idContacto}, null);
        List<String> listaTelefonos = new LinkedList<>();
        while (cursorTelefonos.moveToNext()) {
            String telefono = cursorTelefonos.getString(cursorTelefonos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            listaTelefonos.add(telefono);
        }
        cursorTelefonos.close();

        return listaTelefonos;

    }

    private void anadirAmigosSinAplicacion(List<Persona> listaAmigos) {
        for (Contacto contacto : mListaContactos) listaAmigos.add(contacto.getPersona());
    }

    private class TascaInicializarVistaInvitarAmigos extends ExceptionHandlingAsyncTask<Void, Void, List<Persona>> {
        private List<Contacto> mContactos;

        public TascaInicializarVistaInvitarAmigos(List<Contacto> listaContactos) {
            mContactos = listaContactos;
        }

        @Override
        protected void onPreExecute() {
            mViewPantallaAmigos.showLoadingAmigos();
        }

        @Override
        protected List<Persona> executeTask(Void... params) throws Exception {
            List<Persona> listaAmigos = mDataAmigosInvitables.getListaAmigosInvitables(mContactos);
            anadirAmigosSinAplicacion(listaAmigos);
            Collections.sort(listaAmigos, new Comparator<Persona>() {
                @Override
                public int compare(Persona lhs, Persona rhs) {
                    return lhs.getNombre().compareTo(rhs.getNombre());
                }
            });
            return listaAmigos;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewPantallaAmigos.dismissLoadingAmigos();
            mViewPantallaAmigos.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(List<Persona> personas) {
            mViewPantallaAmigos.setData(personas);
            mViewPantallaAmigos.dismissLoadingAmigos();
        }
    }
}
