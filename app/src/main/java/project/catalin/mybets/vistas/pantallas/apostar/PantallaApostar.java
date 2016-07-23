package project.catalin.mybets.vistas.pantallas.apostar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import project.catalin.mybets.datos.dataObjects.FichaJornada;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerApuesta;
import project.catalin.mybets.vistas.utils.ApuestaAdapter;
import project.catalin.mybets.vistas.utils.customAndroidComponents.Apuesta1x2View;
import project.catalin.mybets.vistas.utils.customAndroidComponents.ApuestaExactaView;
import project.catalin.mybets.vistas.utils.customAndroidComponents.TecladoView;

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
    private TecladoView mTecladoCustom;
    private TextView mBotonEnviar;
    private TextView mTitulo;
    private ListView mListaApuestas;
    private int mIdJornada;
    private Dialog mPopUpEnviada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apostar);

        inicializarComponentes();
        inicializarArgs();
        inicializarToolbar();
        inicializarControladores();
        inicializarAdapter();
        inicializarBotones();
        inicializarPopUp();
    }

    private void inicializarPopUp() {
        mPopUpEnviada = new Dialog(this);
        mPopUpEnviada.setContentView(R.layout.apostar_dialog_enviado);
        mPopUpEnviada.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        mPopUpEnviada.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

    private void inicializarBotones() {
        mBotonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControladorApuesta.botonEnviarPulsado();
            }
        });
    }

    private void inicializarAdapter() {
        if (mTipoPartida == Partida.TIPO_PARTIDA_1x2) {
            mAdapter = new Adapter1x2();
            mTecladoCustom.setVisibility(LinearLayout.GONE);
        } else {
            mAdapter = new AdapterResultadoExacto();
            mTecladoCustom.setVisibility(LinearLayout.VISIBLE);
        }
        mListaApuestas.setAdapter(mAdapter);
    }

    private void inicializarComponentes() {
        mTecladoCustom = (TecladoView) findViewById(R.id.apostar_barra_apuestas);
        mBotonEnviar = (TextView) findViewById(R.id.apostar_boton_enviar);
        mTitulo = (TextView) findViewById(R.id.apostar_text_titulo);
        mListaApuestas = (ListView) findViewById(R.id.apostar_list_formulario_apuestas);
    }

    private void inicializarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.apostar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(mNombrePartida);
    }

    private void inicializarControladores() {
        mControladorApuesta = new ControladorApuesta(this, mIdPartida);
        mControladorApuesta.inicializarVista();
    }

    private void inicializarArgs() {
        Bundle args = getIntent().getExtras();
        mTipoPartida = args.getInt(TAG_TIPO_PARTIDA, -1);
        mIdPartida = args.getInt(TAG_ID_PARTIDA, -1);
        mTitulo.setText(args.getString(TAG_NOMBRE_PARTIDA, ""));
    }

    @Override
    public void setListaApuestas(FichaJornada datosJornada) {
        mIdJornada = datosJornada.getIdJornada();
        mAdapter.recargarDatos(datosJornada.getListaPartidos());
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

    @Override
    public List<Integer> getListApuestas() {
        return mAdapter.getApuestas();
    }

    @Override
    public int getIdJornada() {
        return mIdJornada;
    }

    @Override
    public int getIdPartida() {
        return mIdPartida;
    }

    @Override
    public void mostrarPopUpEnviado() {
        mPopUpEnviada.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private class AdapterResultadoExacto extends ApuestaAdapter {
        private ApuestaExactaView mFocus;

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                    if (mFocus != null) mFocus.unFocus();
                    mFocus = apuesta;
                    apuesta.setFocusLocal();
                }
            });

            apuesta.setVisitanteClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFocus != null) mFocus.unFocus();
                    mFocus = apuesta;
                    apuesta.setFocusVisitante();
                }
            });

            mTecladoCustom.setBotonPulsadoListener(new TecladoView.BotonPulsadoListener() {
                @Override
                public void onBotonPulsado(int numeroBoton) {
                    switch (mFocus.getFocus()) {
                        case ApuestaExactaView.FOCUS_LOCAL:
                            addApuesta(2 * position, numeroBoton);
                            apuesta.apuestaLocal(numeroBoton);
                            break;
                        case ApuestaExactaView.FOCUS_VISITANTE:
                            addApuesta(2 * position, numeroBoton);
                            apuesta.apuestaVisitante(numeroBoton);
                    }
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
                    votoAdicional();
                    apuesta.setActivo(true);
                }
            });

            apuesta.setXOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addApuesta(position, 0);
                    votoAdicional();
                    apuesta.setActivo(true);
                }
            });

            apuesta.set2OnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addApuesta(position, 2);
                    votoAdicional();
                    apuesta.setActivo(true);
                }
            });

            return apuesta;
        }

        private void votoAdicional() {
            mContadorVotados++;
            if (mContadorVotados == getCount()) mBotonEnviar.setVisibility(View.VISIBLE);
        }
    }
}
