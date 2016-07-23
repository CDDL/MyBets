package project.catalin.mybets.vistas.utils.customAndroidComponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.List;

import project.catalin.mybets.R;

/**
 * Created by CDD on 27/05/2016.
 */
public class TecladoView extends RelativeLayout {
    private BotonPulsadoListener mBotonPulsadoListener;
    private RadioButton mBoton1;
    private RadioGroup mGrupoBotonesAbajo;
    private RadioGroup mGrupoBotonesArriba;
    private RadioButton mBoton2;
    private RadioButton mBoton3;
    private RadioButton mBoton4;
    private RadioButton mBoton5;
    private RadioButton mBoton6;
    private RadioButton mBoton7;
    private RadioButton mBoton8;
    private RadioButton mBoton9;
    private RadioButton mBoton0;
    private Button mBotonPuntos;
    private Button mBotonFlecha;
    private List<RadioButton> mListaBotones;

    public TecladoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_view_teclado, this);

        inicializarComponentes();
        inicializarBotones();

    }

    private void inicializarBotones() {
        mBotonPuntos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mGrupoBotonesAbajo.setVisibility(VISIBLE);
                mBotonFlecha.setVisibility(VISIBLE);
                mBotonPuntos.setVisibility(GONE);
            }
        });
        mBotonFlecha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (RadioButton boton : mListaBotones)
                    boton.setText(Integer.parseInt(boton.getText().toString()) + 1 + "");
            }
        });
    }

    private void inicializarComponentes() {
        mGrupoBotonesArriba = (RadioGroup) findViewById(R.id.custom_view_teclado_radiogroup_botones_arriba);
        mGrupoBotonesAbajo = (RadioGroup) findViewById(R.id.custom_view_teclado_radiogroup_botones_abajo);
        mBoton1 = (RadioButton) findViewById(R.id.custom_view_teclado_boton1);
        mBoton2 = (RadioButton) findViewById(R.id.custom_view_teclado_boton2);
        mBoton3 = (RadioButton) findViewById(R.id.custom_view_teclado_boton3);
        mBoton4 = (RadioButton) findViewById(R.id.custom_view_teclado_boton4);
        mBoton5 = (RadioButton) findViewById(R.id.custom_view_teclado_boton5);
        mBoton6 = (RadioButton) findViewById(R.id.custom_view_teclado_boton6);
        mBoton7 = (RadioButton) findViewById(R.id.custom_view_teclado_boton7);
        mBoton8 = (RadioButton) findViewById(R.id.custom_view_teclado_boton8);
        mBoton9 = (RadioButton) findViewById(R.id.custom_view_teclado_boton9);
        mBoton0 = (RadioButton) findViewById(R.id.custom_view_teclado_boton0);
        mBotonPuntos = (Button) findViewById(R.id.custom_view_teclado_boton_puntos);
        mBotonFlecha = (Button) findViewById(R.id.custom_view_teclado_boton_flecha);
        mListaBotones = Arrays.asList(mBoton1, mBoton2, mBoton3, mBoton4, mBoton5, mBoton6, mBoton7, mBoton8, mBoton9, mBoton0);
    }

    public void setBotonPulsadoListener(BotonPulsadoListener botonPulsadoListener) {
        mBotonPulsadoListener = botonPulsadoListener;
    }

    public interface BotonPulsadoListener {
        void onBotonPulsado(int numeroBoton);
    }
}
