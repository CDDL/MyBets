package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorIniciarSesionOpciones;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerOpcionesLogin;

public class IniciarSesionFragmentOpcionesLogin extends Fragment {

    private ControllerOpcionesLogin mControllerOpciones;
    private View mBotonLoginText;
    private View mBotonRegisterText;
    private View mBotonLoginFacebook;
    private View mBotonLoginMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_opciones_login, container, false);

        inicializarComponentes(layout);
        inicializarBotones();
        inicializarControlador();

        return layout;
    }

    private void inicializarControlador() {
        mControllerOpciones = new ControladorIniciarSesionOpciones();
    }

    private void inicializarBotones() {
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

    private void inicializarComponentes(View layout) {
        mBotonLoginText = layout.findViewById(R.id.iniciar_sesion_fragment_opciones_login_boton_login);
        mBotonRegisterText = layout.findViewById(R.id.iniciar_sesion_fragment_opciones_login_boton_register);
        mBotonLoginFacebook = layout.findViewById(R.id.iniciar_sesion_fragment_opciones_login_boton_facebook);
        mBotonLoginMail = layout.findViewById(R.id.iniciar_sesion_fragment_opciones_login_boton_mail);
    }
}
