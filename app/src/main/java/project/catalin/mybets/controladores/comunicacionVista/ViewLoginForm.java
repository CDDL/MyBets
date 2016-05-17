package project.catalin.mybets.controladores.comunicacionVista;

/**
 * Created by Trabajo on 15/04/2016.
 */
public interface ViewLoginForm {
    String getEmail();

    String getPassword();

    void campoContraseñaErroneo(String s);

    void campoEmailErroneo(String s);

    void mensaje(String message);
}
