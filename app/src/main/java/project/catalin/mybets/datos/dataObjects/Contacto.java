package project.catalin.mybets.datos.dataObjects;

import java.util.List;

/**
 * Created by CDD on 23/05/2016.
 */
public class Contacto {
    private List<String> mTelefonos;
    private List<String> mEmails;
    private String mNombre;
    private String mFoto;
    private Persona mPersona;

    public void setTelefonos(List<String> telefonos) {
        mTelefonos = telefonos;
    }

    public void setEmails(List<String> emails) {
        mEmails = emails;
    }

    public List<String> getListaEmails() {
        return mEmails;
    }

    public List<String> getListaTelefonos() {
        return mTelefonos;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public void setFoto(String foto) {
        mFoto = foto;
    }

    public void setPersona(Persona persona) {
        mPersona = persona;
    }

    public Persona getPersona() {
        return mPersona;
    }
}
