package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewRegisterForm;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFormRegister;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerRegister;
import project.catalin.mybets.vistas.pantallas.principal.PantallaPrincipal;

/**
 * A simple {@link Fragment} subclass.
 */
public class IniciarSesionFragmentFormularioRegistrarse extends Fragment implements ViewRegisterForm {


    private EditText mCampoEmail;
    private EditText mCampoNombre;
    private EditText mCampoAlias;
    private EditText mCampoContraseña;
    private View mBotonEnviar;
    private ControllerRegister mControladorRegister;
    private EditText mCampoTelefono;
    private EditText mCampoComprobacionContraseña;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_formulario_registrarse, container, false);

        inicializarComponentes(layout);
        inicializarControlador();
        inicializarBotones();

        return layout;
    }

    private void inicializarBotones() {
        mBotonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControladorRegister.botonEnviarRegistroClick();
            }
        });
    }

    private void inicializarControlador() {
        mControladorRegister = new ControladorFormRegister(this);
    }

    private void inicializarComponentes(View layout) {
        mCampoTelefono = (EditText) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_telefono);
        mCampoEmail = (EditText) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_email);
        mCampoNombre = (EditText) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_nombre);
        mCampoAlias = (EditText) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_username);
        mCampoContraseña = (EditText) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_contrasena);
        mCampoComprobacionContraseña = (EditText) layout.findViewById(R.id.iniciar_sesion_fragment_formulario_registrarse_input_repite_contrasena);
        mBotonEnviar = layout.findViewById(R.id.iniciar_sesion_fragment_formulario_boton_enviar);
    }

    @Override
    public String getPassword() {
        return mCampoContraseña.getText().toString();
    }

    @Override
    public String getTelefono() {
        return mCampoTelefono.getText().toString();
    }

    @Override
    public String getUsername() {
        return mCampoAlias.getText().toString();
    }

    @Override
    public String getNombre() {
        return mCampoNombre.getText().toString();
    }

    @Override
    public String getEmail() {
        return mCampoEmail.getText().toString();
    }

    @Override
    public void errorEmail(String mensaje) {
        mCampoEmail.setError(mensaje);
    }

    @Override
    public void errorPassword(String mensaje) {
        mCampoContraseña.setError(mensaje);
    }

    @Override
    public void errorComprobacionPassword(String mensaje) {
        mCampoComprobacionContraseña.setError(mensaje);
    }

    @Override
    public String getComprobacionPassword() {
        return mCampoComprobacionContraseña.getText().toString();
    }

    @Override
    public void errorTelefono(String mensaje) {
        mCampoTelefono.setError(mensaje);
    }

    @Override
    public void alert(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void abrirPantallaPrincipal() {
        startActivity(new Intent(getActivity(), PantallaPrincipal.class));
        getActivity().finish();
    }
}
