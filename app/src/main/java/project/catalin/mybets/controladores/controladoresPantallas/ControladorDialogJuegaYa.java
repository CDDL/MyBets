package project.catalin.mybets.controladores.controladoresPantallas;

import android.os.Bundle;

import project.catalin.mybets.controladores.comunicacionVista.ViewDialogJuegaYa;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDialogJuegaYa;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentDialogJuegaYa;

/**
 * Created by CDD on 25/05/2016.
 */
public class ControladorDialogJuegaYa implements ControllerDialogJuegaYa {
    private final ViewDialogJuegaYa mViewDialogJuegaYa;

    public ControladorDialogJuegaYa(ViewDialogJuegaYa viewDialogJuegaYa) {
        mViewDialogJuegaYa = viewDialogJuegaYa;
    }

    @Override
    public void botonApostarPulsado() {
        Bundle args = mViewDialogJuegaYa.getBundleParametros();
        mViewDialogJuegaYa.iniciarPantallaApostar(args);
        mViewDialogJuegaYa.cerrarDialog();
    }

    @Override
    public void botonBuscarOponentePulsado() {
        Bundle args = mViewDialogJuegaYa.getBundleParametros();
        mViewDialogJuegaYa.iniciarPantallaBuscarOponentes(args);
        mViewDialogJuegaYa.cerrarDialog();
    }
}
