package project.catalin.mybets.view.iniciarSesion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import project.catalin.mybets.R;
import project.catalin.mybets.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.comunicación.eventos.INotificable;
import project.catalin.mybets.comunicación.eventos.TipoEvento;
import project.catalin.mybets.view.pantallaPrincipal.PantallaPrincipal;

public class IniciarSesionFragmentFormularioLogin extends Fragment implements INotificable {
    private RelativeLayout layoutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.CAMPO_EMAIL_VACIO);
        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.CAMPO_CONTRASEÑA_VACÍO);
        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.LOGIN_SUCCESS);
        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.LOGIN_FAIL);
        GestorEventosUtil.suscribirseAEvento(this, TipoEvento.CAMPO_EMAIL_MALFORMADO);


        layoutView = (RelativeLayout) inflater.inflate(R.layout.iniciar_sesion_fragment_formulario_login, container, false);
        return layoutView;
    }


    public String getEmail() {
        TextView email = (TextView) layoutView.findViewById(R.id.input_email);
        return email.getText().toString();
    }

    public String getPassword() {
        TextView password = (TextView) layoutView.findViewById((R.id.input_password));
        return password.getText().toString();
    }

    @Override
    public void notificar(final int idEvento, final Object msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (idEvento){
                    case (TipoEvento.CAMPO_EMAIL_VACIO):
                        EditText campoUsuario = (EditText) getView().findViewById(R.id.input_email);
                        campoUsuario.setError("El campo del email esta vacío.");
                        break;

                    case (TipoEvento.CAMPO_CONTRASEÑA_VACÍO):
                        EditText campoContraseña = (EditText) getView().findViewById(R.id.input_password);
                        campoContraseña.setError("El campo de la contraseña esta vacío.");
                        break;

                    case (TipoEvento.CAMPO_EMAIL_MALFORMADO):
                        EditText campoMail = (EditText) getView().findViewById(R.id.input_email);
                        campoMail.setError("El email introducido no es válido.");
                        break;

                    case (TipoEvento.ERROR_SERVER):
                        Toast.makeText(getContext(), msg.toString(), Toast.LENGTH_LONG);

                    case (TipoEvento.LOGIN_FAIL):
                        Toast.makeText(getContext(), "El usuario o contraseña son incorrectos.", Toast.LENGTH_LONG).show();
                        break;

                    case (TipoEvento.LOGIN_SUCCESS):
                        Toast.makeText(getContext(), "Login success.", Toast.LENGTH_SHORT).show();
                        pasarAPantallaPrincipal();
                        break;
                }
            }
        });
    }

    private void pasarAPantallaPrincipal() {
        startActivity(new Intent(getContext(), PantallaPrincipal.class));
    }
}
