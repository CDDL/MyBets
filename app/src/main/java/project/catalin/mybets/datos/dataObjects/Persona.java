package project.catalin.mybets.datos.dataObjects;

/**
 * Created by Demils on 22/03/2016.
 */
public class Persona {
    private String mEmail;
    private String mNombre;
    private String mUsername;
    private String mTelefono;
    private String mImage;
    private long mId;
    private int mPuntos;


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTelefono() {
        return mTelefono;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getImagen() {
        return mImage;
    }

    public void setImagen(String image) {
        mImage = image;
    }

    public int getPuntos() {
        return mPuntos;
    }

    public void setPuntos(int puntos) {
        mPuntos = puntos;
    }
}
