package project.catalin.mybets.datos.dataObjects;

/**
 * Created by Catalin on 07/04/2016.
 */
public class LoginData {
    private final String mEmail;
    private final String mPassword;

    public LoginData(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }
}
