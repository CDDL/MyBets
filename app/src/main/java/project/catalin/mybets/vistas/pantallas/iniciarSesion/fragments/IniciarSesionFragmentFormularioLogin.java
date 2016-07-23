package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewLoginForm;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFormLogin;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFormLogin;
import project.catalin.mybets.vistas.pantallas.principal.PantallaPrincipal;

public class IniciarSesionFragmentFormularioLogin extends Fragment implements ViewLoginForm {
    private ControllerFormLogin mControllerLoginForm;

    private TextView mCampoEmail;
    private TextView mCampoPassword;
    private View mBotonLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_formulario_login, container, false);

        inicializarComponentes(layout);
        inicializarControlador();
        inicializarBotones();


        return layout;
    }

    private void inicializarControlador() {
        mControllerLoginForm = new ControladorFormLogin(this);
    }

    private void inicializarComponentes(View layout) {
        mCampoEmail = (TextView) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_email);
        mCampoPassword = (TextView) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_contrasena);
        mBotonLogin = layout.findViewById(R.id.boton_login);
    }

    private void inicializarBotones() {
        mBotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerLoginForm.botonLoginPulsado();
            }
        });
    }

    @Override
    public String getEmail() {
        return mCampoEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return mCampoPassword.getText().toString();
    }

    @Override
    public void setEmailError(String errorMsg) {
        mCampoEmail.setError(errorMsg);
    }

    @Override
    public void alert(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void abrirPantallaPrincipal() {
        startActivity(new Intent(getActivity(), PantallaPrincipal.class));
        getActivity().finish();
    }
}
