package project.catalin.mybets.view.iniciarSesion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import project.catalin.mybets.R;
import project.catalin.mybets.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.comunicación.eventos.INotificable;
import project.catalin.mybets.comunicación.eventos.TipoEvento;
import project.catalin.mybets.controladores.iniciarSesion.fragments.ControladorIniciarSesionForm;
import project.catalin.mybets.controladores.iniciarSesion.comunicación.ViewLogin;
import project.catalin.mybets.view.iniciarSesion.comunicación.ControllerLogin;
import project.catalin.mybets.view.pantallaPrincipal.PantallaPrincipal;

public class IniciarSesionFragmentFormularioLogin extends Fragment implements INotificable, ViewLogin {
    private ControllerLogin controlador;
    private TextView campoEmail;
    private TextView campoPassword;

    private final int[] eventosSuscritos = {
            TipoEvento.CAMPO_EMAIL_VACIO,
            TipoEvento.CAMPO_CONTRASEÑA_VACÍO,
            TipoEvento.LOGIN_SUCCESS,
            TipoEvento.LOGIN_FAIL,
            TipoEvento.CAMPO_EMAIL_MALFORMADO};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.iniciar_sesion_fragment_formulario_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        suscribirseEventos();
        instanciarControlador();
        instanciarCampos();
    }

    @Override
    public void notificar(final int idEvento, final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (idEvento){
                    case (TipoEvento.CAMPO_EMAIL_VACIO):
                        campoEmail.setError("El campo del email esta vacío.");
                        break;

                    case (TipoEvento.CAMPO_CONTRASEÑA_VACÍO):
                        campoPassword.setError("El campo de la contraseña esta vacío.");
                        break;

                    case (TipoEvento.CAMPO_EMAIL_MALFORMADO):
                        campoEmail.setError("El email introducido no es válido.");
                        break;

                    case (TipoEvento.ERROR_SERVER):
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
                        break;

                    case (TipoEvento.LOGIN_SUCCESS):
                        Toast.makeText(getContext(), "Identificación válida.", Toast.LENGTH_SHORT).show();
                        pasarAPantallaPrincipal();
                        break;
                }
            }
        });
    }

    private void instanciarCampos() {
        campoEmail = (TextView) getView().findViewById(R.id.input_email);
        campoPassword = (TextView) getView().findViewById(R.id.input_password);
    }

    private void instanciarControlador() {
        controlador = new ControladorIniciarSesionForm(this);
    }

    private void suscribirseEventos() {
        GestorEventosUtil.suscribirseAEventos(this, eventosSuscritos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GestorEventosUtil.desuscribirseDeEventos(this, eventosSuscritos);
        controlador.destroy();
    }

    @Override
    public String getEmail() {
        return campoEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return campoPassword.getText().toString();
    }

    private void pasarAPantallaPrincipal() {
        startActivity(new Intent(getContext(), PantallaPrincipal.class));
    }
}
