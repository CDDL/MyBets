package project.catalin.mybets.controladores.comunicacionVista;

/**
 * Created by CDD on 25/05/2016.
 */
public interface ViewPantallaPrincipalApuestas {
    void iniciarPantallaCategorias();

    void iniciarPantallaPartidasPopulares();

    void iniciarPantallaCategoriaFutbol();

    void setIdFutbol(int idFutbol);

    void alert(String message);
}
