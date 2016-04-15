package project.catalin.mybets.view.iniciarSesion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import project.catalin.mybets.R;
import project.catalin.mybets.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.comunicación.eventos.TipoEvento;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentFormularioLogin;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentFormularioRegistrarse;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentOpcionesLogin;

public class IniciarSesionPantallaPrincipal extends AppCompatActivity {

    private static final String TAG_FRAG_FORM_LOGIN = "tagFL";
    private static final String TAG_FRAG_FORM_REG = "tagFR";
    private static final String TAG_FRAG_OPT_LOGIN = "tagOL";

    private Fragment fragmentFormularioLogin;
    private Fragment fragmentFormularioRegistro;
    private Fragment fragmentOpcionesLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.iniciar_sesion_pantalla_principal);

        if(savedInstanceState == null) {
            añadirFragmentBase();
        } else recuperarFragments();
    }

    public void clickLoginText(View view) {
        if(fragmentFormularioLogin == null) fragmentFormularioLogin = new IniciarSesionFragmentFormularioLogin();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,  R.anim.slide_out_right)
                .replace(R.id.plantilla_fragment_opciones_login, fragmentFormularioLogin, TAG_FRAG_FORM_LOGIN)
                .addToBackStack(TAG_FRAG_FORM_LOGIN)
                .commit();
    }

    public void clickRegisterText(View view) {
        if(fragmentFormularioRegistro == null) fragmentFormularioRegistro = new IniciarSesionFragmentFormularioRegistrarse();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,  R.anim.slide_out_right)
                .replace(R.id.plantilla_fragment_opciones_login, fragmentFormularioRegistro, TAG_FRAG_FORM_REG)
                .addToBackStack(null)
                .commit();
    }

    public void clickButtonLogin(View view) {
        GestorEventosUtil.notificarEvento(TipoEvento.PULSADO_BOTON_LOGIN_PANTALLA_INICIO);
    }

    private void recuperarFragments() {
        fragmentFormularioLogin = getSupportFragmentManager().findFragmentByTag(TAG_FRAG_FORM_LOGIN);
        fragmentFormularioRegistro = getSupportFragmentManager().findFragmentByTag(TAG_FRAG_FORM_REG);
        fragmentOpcionesLogin = getSupportFragmentManager().findFragmentByTag(TAG_FRAG_OPT_LOGIN);
    }

    private void añadirFragmentBase() {
        if(fragmentOpcionesLogin == null) fragmentOpcionesLogin = new IniciarSesionFragmentOpcionesLogin();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.plantilla_fragment_opciones_login, fragmentOpcionesLogin, TAG_FRAG_OPT_LOGIN)
                .commit();
    }
}
