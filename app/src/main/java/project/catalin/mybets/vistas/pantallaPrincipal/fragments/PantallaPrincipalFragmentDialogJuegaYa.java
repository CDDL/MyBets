package project.catalin.mybets.vistas.pantallaPrincipal.fragments;


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

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.Locale;

import project.catalin.mybets.R;
import project.catalin.mybets.datos.objetosData.Partida;
import project.catalin.mybets.vistas.pantallaApostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallaElegirOponentes.PantallaElegirOponentes;


/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentDialogJuegaYa extends DialogFragment {

    public static final String TAG_COLOR = "color";
    public static final String TAG_BOTE = "bote";
    public static final String TAG_ICON = "icon";
    public static final String TAG_TITULO = "titulo";

    private TextView mTitulo;
    private TextView mBoteText;
    private ImageView mIconTitulo;
    private ImageView mBotonCerrar;
    private TextView mBotonBuscarOponente;
    private TextView mBotonApostar;
    private GradientDrawable mFondoIcono;


    public PantallaPrincipalFragmentDialogJuegaYa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.pantalla_principal_fragment_dialog_juega_ya, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(100, 100);

        mTitulo = (TextView) layout.findViewById(R.id.dialog_juega_ya_text_titulo);
        mBoteText = (TextView) layout.findViewById(R.id.dialog_juega_ya_text_bote);
        mIconTitulo = (ImageView) layout.findViewById(R.id.dialog_juega_ya_icon_titulo);
        mBotonCerrar = (ImageView) layout.findViewById(R.id.dialog_juega_ya_boton_cerrar);
        mBotonBuscarOponente = (TextView) layout.findViewById(R.id.dialog_juega_ya_boton_buscar_oponente);
        mBotonApostar = (TextView) layout.findViewById(R.id.dialog_juega_ya_boton_apostar);
        mFondoIcono = (GradientDrawable) layout.findViewById(R.id.dialog_juega_ya_fondo_titulo).getBackground();

        initParams();
        initBotones();

        return layout;
    }

    private void initBotones() {
        final Bundle bundle = getArguments();

        mBotonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        mBotonBuscarOponente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityElegirOponentes = new Intent(getContext(), PantallaElegirOponentes.class).putExtras(bundle);
                startActivity(activityElegirOponentes);
                dismiss();
            }
        });


        mBotonApostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityApostar = new Intent(getContext(), PantallaApostar.class).putExtras(bundle);
                startActivity(activityApostar);
                dismiss();
            }
        });
    }

    private void initParams() {
        Bundle bundle = getArguments();

        mTitulo.setText(bundle.getString(TAG_TITULO));
        mBoteText.setText(MessageFormat.format(getString(R.string.dialog_juega_ya_bote_format), bundle.getInt(TAG_BOTE)));
        Picasso.with(getContext())
                .load(bundle.getString(TAG_ICON))
                .into(mIconTitulo);

    }


}
