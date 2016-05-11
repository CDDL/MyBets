package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorIniciarSesionOpciones;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerOpcionesLogin;

public class IniciarSesionFragmentOpcionesLogin extends Fragment {

    private ControllerOpcionesLogin mControllerOpciones;
    private View mBotonLoginText;
    private View mBotonRegisterText;
    private View mBotonLoginFacebook;
    private View mBotonLoginMail;

    public IniciarSesionFragmentOpcionesLogin() {
        mControllerOpciones = new ControladorIniciarSesionOpciones();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_opciones_login, container, false);

        mBotonLoginText = layout.findViewById(R.id.boton_login_text);
        mBotonRegisterText = layout.findViewById(R.id.boton_registro);
        mBotonLoginFacebook = layout.findViewById(R.id.boton_login_fb);
        mBotonLoginMail = layout.findViewById(R.id.boton_login_mail);

        asignarFuncionalidadBotones();

        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mControllerOpciones.destroy();
    }

    private void asignarFuncionalidadBotones() {
        mBotonLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerOpciones.botonLoginTextPulsado();
            }
        });

        mBotonRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerOpciones.botonRegisterPulsado();
            }
        });

        mBotonLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerOpciones.botonLoginFacebookPulsado();
            }
        });

        mBotonLoginMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerOpciones.botonLoginMailPulsado();
            }
        });


    }
}
