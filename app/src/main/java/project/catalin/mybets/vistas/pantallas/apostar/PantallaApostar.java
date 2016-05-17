package project.catalin.mybets.vistas.pantallas.apostar;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewApostar;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorApuesta;
import project.catalin.mybets.datos.dataObjects.Equipo;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerApuesta;
import project.catalin.mybets.vistas.utils.customAndroidComponents.Apuesta1x2View;
import project.catalin.mybets.vistas.utils.ApuestaAdapter;
import project.catalin.mybets.vistas.utils.customAndroidComponents.ApuestaExactaView;

public class PantallaApostar extends AppCompatActivity implements ViewApostar {

    public static final String TAG_TIPO_PARTIDA = "tipopartida";
    public static final String TAG_ID_PARTIDA = "idpartida";
    public static final String TAG_NOMBRE_PARTIDA = "nombrepartida";
    private int mTipoPartida;
    private int mIdPartida;
    private ControllerApuesta mControladorApuesta;
    private ApuestaAdapter mAdapter;
    private ProgressDialog mDialogLoadingPartidas;
    private String mNombrePartida;
    private LinearLayout mTecladoCustom;
    private TextView mBotonEnviar;
    private TextView mTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apostar);

        setUpToolBar();

        mTecladoCustom = (LinearLayout) findViewById(R.id.apostar_barra_apuestas);
        mBotonEnviar = (TextView) findViewById(R.id.apostar_boton_enviar);
        mTitulo = (TextView) findViewById(R.id.apostar_text_titulo);

        inicializarArgs();
        inicializarControladores();
        if(mTipoPartida == Partida.TIPO_PARTIDA_1x2) {
            inicializarAdapter1x2();
            mTecladoCustom.setVisibility(LinearLayout.GONE);
        }
        else {
            inicializarAdapterResultadoExacto();
            mTecladoCustom.setVisibility(LinearLayout.GONE);
        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.apostar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void inicializarAdapterResultadoExacto() {
        mAdapter = new AdapterResultadoExacto();
        ListView lista = (ListView) findViewById(R.id.apostar_list_formulario_apuestas);
        lista.setAdapter(mAdapter);
    }

    private void inicializarAdapter1x2() {
        mAdapter = new Adapter1x2();
        ListView lista = (ListView) findViewById(R.id.apostar_list_formulario_apuestas);
        lista.setAdapter(mAdapter);
    }

    private void inicializarControladores() {
        mControladorApuesta = new ControladorApuesta(this);
        mControladorApuesta.actualizarListaApuestas(mIdPartida);
    }

    private void inicializarArgs() {
        mTipoPartida = Partida.TIPO_PARTIDA_RESULT_EXACT;
        mIdPartida = 0;
        mTitulo.setText("FifaOlae");
//        mTipoPartida = getIntent().getIntExtra(TAG_TIPO_PARTIDA, -1);
//        mIdPartida = getIntent().getIntExtra(TAG_ID_PARTIDA, -1);
    }

    @Override
    public void setListaApuestas(List<Partido> datosJornada) {
        mAdapter.recargarDatos(datosJornada);
    }

    @Override
    public void dismissLoadingJornada() {
        mDialogLoadingPartidas.dismiss();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingJornada() {
        mDialogLoadingPartidas = ProgressDialog.show(this, "", "Cargando datos de la jornada...", false, false);
        mDialogLoadingPartidas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    private class AdapterResultadoExacto extends ApuestaAdapter {
        private ApuestaExactaView mFocus;

        @Override
        public void recargarDatos(List<Partido> listaNueva) {
            super.recargarDatos(listaNueva);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ApuestaExactaView apuesta = convertView == null ? (ApuestaExactaView) View.inflate(PantallaApostar.this, R.layout.item_apostarexact, null) : (ApuestaExactaView) convertView;
            Partido partido = getItem(position);
            Equipo equipoLocal = partido.getEquipos().get(0);
            Equipo equipoVisistante = partido.getEquipos().get(1);

            apuesta.setIconLocal(equipoLocal.getIcon());
            apuesta.setNombreLocal(equipoLocal.getNombre());
            apuesta.setIconVisitante(equipoVisistante.getIcon());
            apuesta.setNombreVisitante(equipoVisistante.getNombre());

            apuesta.setLocalClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mFocus!=null) mFocus.unFocus();
                    mFocus = apuesta;
                    apuesta.setFocusLocal();
                }
            });

            apuesta.setVisitanteClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mFocus!=null) mFocus.unFocus();
                    mFocus = apuesta;
                    apuesta.setFocusVisitante();
                }
            });


            return apuesta;
        }
    }

    private class Adapter1x2 extends ApuestaAdapter {
        private int mContadorVotados;

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Apuesta1x2View apuesta = convertView == null ? (Apuesta1x2View) View.inflate(PantallaApostar.this, R.layout.item_apostar1x2, null) : (Apuesta1x2View) convertView;
            Partido partido = getItem(position);
            Equipo equipoLocal = partido.getEquipos().get(0);
            Equipo equipoVisitante = partido.getEquipos().get(1);

            apuesta.setIconLocal(equipoLocal.getIcon());
            apuesta.setIconVisitante(equipoVisitante.getIcon());
            apuesta.setNombreLocal(equipoLocal.getNombre());
            apuesta.setNombreVisitante(equipoVisitante.getNombre());
            apuesta.setApuesta(Apuesta1x2View.APUESTA_SIN_APOSTAR);

            apuesta.set1OnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addApuesta(position, 1);
                    votoAdicional(apuesta);
                }
            });

            apuesta.setXOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addApuesta(position, 0);
                    votoAdicional(apuesta);
                }
            });

            apuesta.set2OnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addApuesta(position, 2);
                    votoAdicional(apuesta);
                }
            });

            return apuesta;
        }

        private void votoAdicional(Apuesta1x2View apuesta) {
            apuesta.setActivo(true);
            mContadorVotados++;
            if(mContadorVotados == getCount()) mBotonEnviar.setVisibility(View.VISIBLE);
        }
    }
}
