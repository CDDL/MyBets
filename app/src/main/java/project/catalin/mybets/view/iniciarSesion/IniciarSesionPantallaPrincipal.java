package project.catalin.mybets.view.iniciarSesion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import project.catalin.mybets.R;
import project.catalin.mybets.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.comunicación.eventos.TipoEvento;
import project.catalin.mybets.controladores.iniciarSesion.ControladorIniciarSesion;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentFormularioLogin;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentFormularioRegistrarse;
import project.catalin.mybets.view.iniciarSesion.fragments.IniciarSesionFragmentOpcionesLogin;

public class IniciarSesionPantallaPrincipal extends AppCompatActivity {
    private IniciarSesionFragmentOpcionesLogin fragmentOpcionesLogin;
    private IniciarSesionFragmentFormularioLogin fragmentFormularioLogin;
    private IniciarSesionFragmentFormularioRegistrarse fragmentFormularioRegistro;


    public void clickLoginText(View view) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,  R.anim.slide_out_right)
                .replace(R.id.fragment_opciones_login, fragmentFormularioLogin)
                .addToBackStack(null)
                .commit();
    }

    public void clickRegisterText(View view) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations( R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left,  R.anim.slide_out_right)
                .replace(R.id.fragment_opciones_login, fragmentFormularioRegistro)
                .addToBackStack(null)
                .commit();
    }

    public void clickButtonLogin(View view) {
        GestorEventosUtil.notificarEvento(TipoEvento.PULSADO_BOTON_LOGIN_PANTALLA_INICIO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fragmentFormularioLogin == null) fragmentFormularioLogin = new IniciarSesionFragmentFormularioLogin();
        if(fragmentOpcionesLogin == null) fragmentOpcionesLogin = new IniciarSesionFragmentOpcionesLogin();
        if(fragmentFormularioRegistro == null ) fragmentFormularioRegistro = new IniciarSesionFragmentFormularioRegistrarse();

        if(savedInstanceState == null) {
            añadirFragmentBase();
        }

        setContentView(R.layout.iniciar_sesion_pantalla_principal);

    }


    private void añadirFragmentBase() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_opciones_login, fragmentOpcionesLogin)
                .commit();
    }


    public IniciarSesionFragmentFormularioLogin getFragmentFormularioLogin() {
        return fragmentFormularioLogin;
    }
}
