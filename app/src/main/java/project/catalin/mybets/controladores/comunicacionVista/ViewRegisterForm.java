package project.catalin.mybets.controladores.comunicacionVista;

/**
 * Created by Trabajo on 18/04/2016.
 */
public interface ViewRegisterForm {
    String getPassword();

    String getTelefono();

    String getUsername();

    String getNombre();

    String getEmail();

    void errorEmail(String mensaje);

    void errorPassword(String mensaje);

    void errorComprobacionPassword(String mensaje);

    String getComprobacionPassword();

    void errorTelefono(String mensaje);

    void alert(String message);

    void abrirPantallaPrincipal();
}
