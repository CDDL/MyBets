package project.catalin.mybets.vistas.pantallas.iniciarSesion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorIniciarSesionPantallas;
import project.catalin.mybets.controladores.comunicacionVista.ViewLoginBase;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDestructible;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments.IniciarSesionFragmentFormularioLogin;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments.IniciarSesionFragmentOpcionesLogin;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments.IniciarSesionFragmentFormularioRegistrarse;
import project.catalin.mybets.vistas.pantallas.principal.PantallaPrincipal;

public class IniciarSesionPantallaPrincipal extends AppCompatActivity implements ViewLoginBase {

    private ControllerDestructible mControladorFragmentos;

    private static final String TAG_FRAG_FORM_LOGIN = "tagFL";
    private static final String TAG_FRAG_FORM_REG = "tagFR";
    private static final String TAG_FRAG_OPT_LOGIN = "tagOL";

    private Fragment mFragmentFormularioLogin;
    private Fragment mFragmentFormularioRegistro;
    private Fragment mFragmentOpcionesLogin;

    public IniciarSesionPantallaPrincipal() {
        mControladorFragmentos = new ControladorIniciarSesionPantallas(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mControladorFragmentos.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.iniciar_sesion_pantalla_principal);

        if(savedInstanceState == null) {
            añadirFragmentBase();
        } else recuperarFragments();
    }


    private void recuperarFragments() {
        mFragmentFormularioLogin = getSupportFragmentManager().findFragmentByTag(TAG_FRAG_FORM_LOGIN);
        mFragmentFormularioRegistro = getSupportFragmentManager().findFragmentByTag(TAG_FRAG_FORM_REG);
        mFragmentOpcionesLogin = getSupportFragmentManager().findFragmentByTag(TAG_FRAG_OPT_LOGIN);
    }

    private void añadirFragmentBase() {
        if(mFragmentOpcionesLogin == null) mFragmentOpcionesLogin = new IniciarSesionFragmentOpcionesLogin();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.plantilla_fragment_opciones_login, mFragmentOpcionesLogin, TAG_FRAG_OPT_LOGIN)
                .commit();
    }

    @Override
    public void cambiarFragmentALoginForm() {
        if(mFragmentFormularioLogin == null) mFragmentFormularioLogin = new IniciarSesionFragmentFormularioLogin();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,  R.anim.slide_out_right)
                .replace(R.id.plantilla_fragment_opciones_login, mFragmentFormularioLogin, TAG_FRAG_FORM_LOGIN)
                .addToBackStack(TAG_FRAG_FORM_LOGIN)
                .commit();
    }

    @Override
    public void cambiarFragmentARegisterForm() {
        if(mFragmentFormularioRegistro == null) mFragmentFormularioRegistro = new IniciarSesionFragmentFormularioRegistrarse();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,  R.anim.slide_out_right)
                .replace(R.id.plantilla_fragment_opciones_login, mFragmentFormularioRegistro, TAG_FRAG_FORM_REG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void iniciarActivityPantallaPrincipal() {
        startActivity(new Intent(this, PantallaPrincipal.class));
    }
}
