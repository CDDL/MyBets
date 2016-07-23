package project.catalin.mybets.vistas.pantallas.principal.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.MessageFormat;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewDialogJuegaYa;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorDialogJuegaYa;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDialogJuegaYa;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallas.elegirOponentes.PantallaElegirOponentes;


/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentDialogJuegaYa extends DialogFragment implements ViewDialogJuegaYa {

    public static final String TAG_COLOR = "color";
    public static final String TAG_BOTE = "bote";
    public static final String TAG_TITULO = "titulo";

    private TextView mTitulo;
    private TextView mBoteText;
    private ImageView mBotonCerrar;
    private TextView mBotonBuscarOponente;
    private TextView mBotonApostar;
    private GradientDrawable mFondoIcono;
    private Bundle mParamsBundle;
    private ControllerDialogJuegaYa mControlador;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.pantalla_principal_fragment_dialog_juega_ya, container, false);

        inicializarComponentes(layout);
        inicializarDialog();
        inicializarParametros();
        inicializarBotones();
        inicializarControlador();

        return layout;
    }

    private void inicializarControlador() {
        mControlador = new ControladorDialogJuegaYa(this);
    }

    private void inicializarBotones() {
        mBotonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        mBotonBuscarOponente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mControlador.botonBuscarOponentePulsado();
            }
        });


        mBotonApostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControlador.botonApostarPulsado();

            }
        });
    }

    private void inicializarParametros() {
        mParamsBundle = getArguments();

        mTitulo.setText(mParamsBundle.getString(TAG_TITULO));
        mBoteText.setText(MessageFormat.format(getString(R.string.dialog_juega_ya_bote_format), mParamsBundle.getInt(TAG_BOTE)));
        mFondoIcono.setColor(Color.parseColor(mParamsBundle.getString(TAG_COLOR)));

    }

    private void inicializarComponentes(View layout) {
        mTitulo = (TextView) layout.findViewById(R.id.dialog_juega_ya_text_titulo);
        mBoteText = (TextView) layout.findViewById(R.id.dialog_juega_ya_text_bote);
        mBotonCerrar = (ImageView) layout.findViewById(R.id.dialog_juega_ya_boton_cerrar);
        mBotonBuscarOponente = (TextView) layout.findViewById(R.id.dialog_juega_ya_boton_buscar_oponente);
        mBotonApostar = (TextView) layout.findViewById(R.id.dialog_juega_ya_boton_apostar);
        mFondoIcono = (GradientDrawable) layout.findViewById(R.id.dialog_juega_ya_fondo_titulo).getBackground();
    }

    private void inicializarDialog() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(100, 100);
    }

    @Override
    public void iniciarPantallaApostar(Bundle args) {
        args.putString(PantallaApostar.TAG_NOMBRE_PARTIDA, args.getString(TAG_TITULO));
        Intent activityApostar = new Intent(getContext(), PantallaApostar.class).putExtras(args);
        startActivity(activityApostar);
    }

    @Override
    public void iniciarPantallaBuscarOponentes(Bundle args) {
        Intent activityBuscarOponentes = new Intent(getContext(), PantallaElegirOponentes.class).putExtras(args);
        startActivity(activityBuscarOponentes);
    }

    @Override
    public void cerrarDialog() {
        dismiss();
    }

    @Override
    public Bundle getBundleParametros() {
        return mParamsBundle;
    }
}
