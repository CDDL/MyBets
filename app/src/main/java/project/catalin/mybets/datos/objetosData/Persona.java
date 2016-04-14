package project.catalin.mybets.datos.objetosData;

/**
 * Created by Demils on 22/03/2016.
 */
public class Persona {
    private final String mEmail;
    private final String mNombre;
    private final String mUsername;
    private final String mTelefono;
    private long mId;
    private String mImage;


    public Persona(String email, String nombre, String username, String telefono) {
        mEmail = email;
        mNombre = nombre;
        mUsername = username;
        mTelefono = telefono;
    }


    public long getId() {
        return mId;
    }

    public String getTelefono() {
        return mTelefono;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getNombre() {
        return mNombre;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getImage() {
        return mImage;
    }
}
