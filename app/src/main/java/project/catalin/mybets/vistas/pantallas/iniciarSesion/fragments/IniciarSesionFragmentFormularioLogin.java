package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicaciónVista.ViewLoginForm;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFormLogin;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerFormLogin;

public class IniciarSesionFragmentFormularioLogin extends Fragment implements ViewLoginForm {
    private ControllerFormLogin mControllerLoginForm;

    private TextView mCampoEmail;
    private TextView mCampoPassword;
    private View mBotonLogin;

    public IniciarSesionFragmentFormularioLogin() {
        mControllerLoginForm = new ControladorFormLogin(this);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_formulario_login, container, false);

        mCampoEmail = (TextView) layout.findViewById(R.id.input_email);
        mCampoPassword = (TextView) layout.findViewById(R.id.input_password);
        mBotonLogin = layout.findViewById(R.id.boton_login);

        asignarFuncionalidadBotones();

        return layout;
    }

    private void asignarFuncionalidadBotones() {
        mBotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerLoginForm.botonLoginPulsado();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mControllerLoginForm.destroy();
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
    public void campoContraseñaErroneo(String errorMsg) {
        mCampoPassword.setError(errorMsg);
    }

    @Override
    public void campoEmailErroneo(String errorMsg) {
        mCampoEmail.setError(errorMsg);
    }

    @Override
    public void mensaje(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
