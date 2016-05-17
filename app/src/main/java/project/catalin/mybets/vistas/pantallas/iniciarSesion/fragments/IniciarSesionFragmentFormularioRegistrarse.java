package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewRegisterForm;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFormRegister;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerRegister;

/**
 * A simple {@link Fragment} subclass.
 */
public class IniciarSesionFragmentFormularioRegistrarse extends Fragment implements ViewRegisterForm {


    private EditText mCampoEmail;
    private EditText mCampoNombreApellidos;
    private EditText mCampoAlias;
    private EditText mCampoTelefono;
    private EditText mCampoContraseña;
    private View mBotonEnviar;
    private ControllerRegister mControladorRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_formulario_crear_cuenta, container, false);

        mControladorRegister = new ControladorFormRegister(this);
        mCampoEmail = (EditText) layout.findViewById(R.id.input_email);
        mCampoNombreApellidos = (EditText) layout.findViewById(R.id.input_nombre);
        mCampoAlias = (EditText) layout.findViewById(R.id.input_username);
        mCampoTelefono = (EditText) layout.findViewById(R.id.input_telefono);
        mCampoContraseña = (EditText) layout.findViewById(R.id.input_password);
        mBotonEnviar = layout.findViewById(R.id.boton_registro_enviar);

        asignarFuncionalidadBotones();

        return layout;
    }

    private void asignarFuncionalidadBotones() {
        mBotonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControladorRegister.botonEnviarRegistroClick();
            }
        });
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
        return mCampoNombreApellidos.getText().toString();
    }

    @Override
    public String getEmail() {
        return mCampoEmail.getText().toString();
    }
}
