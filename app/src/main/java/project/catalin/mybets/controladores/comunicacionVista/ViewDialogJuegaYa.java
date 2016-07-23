package project.catalin.mybets.controladores.comunicacionVista;

import android.os.Bundle;

/**
 * Created by CDD on 25/05/2016.
 */
public interface ViewDialogJuegaYa {
    void iniciarPantallaApostar(Bundle args);

    void iniciarPantallaBuscarOponentes(Bundle args);

    void cerrarDialog();

    Bundle getBundleParametros();
}
