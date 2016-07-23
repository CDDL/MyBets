package project.catalin.mybets.controladores.comunicacionVista;

/**
 * Created by Trabajo on 15/04/2016.
 */
public interface ViewLoginForm {
    String getEmail();

    String getPassword();

    void setEmailError(String s);

    void alert(String message);

    void abrirPantallaPrincipal();
}
