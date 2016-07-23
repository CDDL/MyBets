package project.catalin.mybets.vistas.pantallas.historialJornada;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.IconPageIndicator;

import java.text.MessageFormat;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewHistorialJornada;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorDatosPartidaPasada;
import project.catalin.mybets.datos.dataObjects.Apuesta;
import project.catalin.mybets.datos.dataObjects.FichaHistorial;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.utils.DateUtils;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDatosPartidaPasada;
import project.catalin.mybets.vistas.pantallas.perfil.PantallaPerfilUsuario;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.utils.CustomPagerRecargable;

/**
 * Created by CDD on 10/05/2016.
 */
public class PantallaHistorialJornada extends AppCompatActivity implements ViewHistorialJornada {
    public static final String TAG_ID_PARTIDA = "idPartida";
    public static final String TAG_TIPO_PARTIDA = "tipoPartida";
    private ImageView mFotoPerfil;
    private TextView mTextFecha;
    private TextView mPuntosBote;
    private ImageView mIconCabecera;
    private ImageView mBotonCerrar;
    private ViewPager mPagerApuestas;
    private RelativeLayout mFondoCabecera;
    private int mIdPartida;
    private int mTipoPartida;
    private ControllerDatosPartidaPasada mControlador;
    private IconPageIndicator mTitlePager;
    private ProgressDialog mDialogLoadingPartidasPasadas;
    private CustomPagerRecargable<FichaHistorial> mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial_partida);

        inicializarComponentes();
        inicializarArgs();
        inicializarPager();
        inicializarControladores();
        inicializarBotones();
    }

    private void inicializarBotones() {
        mBotonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inicializarPager() {
        mPagerAdapter = new DotPageAdapter();
        mPagerApuestas.setAdapter(mPagerAdapter);
        mTitlePager.setViewPager(mPagerApuestas);
        mTitlePager.setOnPageChangeListener(mPagerAdapter);
    }

    private void inicializarControladores() {
        mControlador = new ControladorDatosPartidaPasada(this, mIdPartida);
        mControlador.inicializarVista();
    }

    private void inicializarArgs() {
        Bundle extras = getIntent().getExtras();
        mIdPartida = extras.getInt(TAG_ID_PARTIDA, -1);
        mTipoPartida = extras.getInt(TAG_TIPO_PARTIDA, -1);
    }

    private void inicializarComponentes() {
        mFotoPerfil = (ImageView) findViewById(R.id.historial_jornada_foto_placeholder);
        mTextFecha = (TextView) findViewById(R.id.historial_jornada_text_fecha);
        mPuntosBote = (TextView) findViewById(R.id.historial_jornada_text_bote);
        mIconCabecera = (ImageView) findViewById(R.id.historial_jornada_icon_cabecera);
        mBotonCerrar = (ImageView) findViewById(R.id.historial_jornada_boton_cerrar);
        mPagerApuestas = (ViewPager) findViewById(R.id.historial_jornada_viewpager_apuestas);
        mFondoCabecera = (RelativeLayout) findViewById(R.id.historial_jornada_fondo_cabecera);
        mTitlePager = (IconPageIndicator) findViewById(R.id.historial_jornada_viewpagerindicator);
    }

    @Override
    public void showLoadingPartidasPasadas() {
        mDialogLoadingPartidasPasadas = ProgressDialog.show(this, "", "Cargando datos de la jornada...", false, false);
        mDialogLoadingPartidasPasadas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissLoadingPartidasPasadas() {
        mDialogLoadingPartidasPasadas.dismiss();
    }

    @Override
    public void setHistorialPartidaPasada(List<FichaHistorial> historialApuestasPartida) {
        mPagerAdapter.recargarDatos(historialApuestasPartida);
        mTitlePager.notifyDataSetChanged();
        mPagerAdapter.onPageSelected(0);
    }


    private class AdapterPartida1x2 extends AdapterRecargable<Apuesta> {
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final RelativeLayout layout = convertView == null ? (RelativeLayout) LayoutInflater.from(PantallaHistorialJornada.this).inflate(R.layout.item_apuesta_pasada, parent, false) : (RelativeLayout) convertView;
            Apuesta apuesta = getItem(position);
            ImageView mIconoLocal = (ImageView) layout.findViewById(R.id.item_apuesta_pasada_icon_equipo_local);
            ImageView mIconVisitante = (ImageView) layout.findViewById(R.id.item_apuesta_pasada_icon_equipo_visitante);
            TextView mNombreEquipoLocal = (TextView) layout.findViewById(R.id.item_apuesta_pasada_text_equipo_local);
            TextView mNombreEqupoVisitante = (TextView) layout.findViewById(R.id.item_apuesta_pasada_text_equipo_visitante);
            TextView mBoton1 = (TextView) layout.findViewById(R.id.item_apuesta_pasada_icon1);
            TextView mBotonX = (TextView) layout.findViewById(R.id.item_apuesta_pasada_iconX);
            TextView mBoton2 = (TextView) layout.findViewById(R.id.item_apuesta_pasada_icon2);

            Picasso.with(PantallaHistorialJornada.this).load(apuesta.getIconLocal()).into(mIconoLocal);
            Picasso.with(PantallaHistorialJornada.this).load(apuesta.getIconVisitantes()).into(mIconVisitante);
            mNombreEquipoLocal.setText(apuesta.getNombreEquipoLocal());
            mNombreEqupoVisitante.setText(apuesta.getNombreEquipoVisitante());

            switch (apuesta.getApuesta()) {
                case Apuesta.APUESTA_LOCAL:
                    mBoton1.setEnabled(true);
                    break;
                case Apuesta.APUESTA_VISITANTE:
                    mBoton2.setEnabled(true);
                    break;
                case Apuesta.APUESTA_EMPATE:
                    mBotonX.setEnabled(true);
                    break;
            }

            return layout;
        }
    }

    private class AdapterPartidaResultadoExact extends AdapterRecargable<Apuesta> {
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private class DotPageAdapter extends CustomPagerRecargable<FichaHistorial> {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ListView listView = (ListView) LayoutInflater.from(PantallaHistorialJornada.this).inflate(R.layout.itempager_lista_partidos, container, false);
            FichaHistorial fichaHistorial = getItemPosition(position);
            AdapterRecargable<Apuesta> mListAdapter;
            switch (mTipoPartida) {
                case Partida.TIPO_PARTIDA_1x2:
                    mListAdapter = new AdapterPartida1x2();
                    break;
                case Partida.TIPO_PARTIDA_RESULT_EXACT:
                    mListAdapter = new AdapterPartidaResultadoExact();
                    break;
                default:
                    mListAdapter = new AdapterPartida1x2();
            }

            mListAdapter.recargarDatos(fichaHistorial.getApuestas());
            listView.setAdapter(mListAdapter);
            container.addView(listView);

            return listView;
        }

        @Override
        public int getIconResId(int index) {
            return R.drawable.mybets_icon_punto_selector;
        }


        @Override
        public void onPageSelected(int position) {
            final FichaHistorial fichaHistorial = getItemPosition(position);
            Picasso.with(PantallaHistorialJornada.this).load(fichaHistorial.getUrlImagen()).into(mFotoPerfil);
            Picasso.with(PantallaHistorialJornada.this).load(fichaHistorial.getUrlIcono()).into(mIconCabecera);
            mTextFecha.setText(new DateUtils().from(fichaHistorial.getData()).to(getString(R.string.pantalla_historial_jornada_format_fecha)));
            mPuntosBote.setText(MessageFormat.format(getString(R.string.pantalla_historial_jornada_format_puntos), fichaHistorial.getBote()));
            GradientDrawable fondoCabecera = (GradientDrawable) mFondoCabecera.getBackground();
            fondoCabecera.setColor(Color.parseColor(fichaHistorial.getColorCabecera()));
            mFotoPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirVentanaVerPerfil(fichaHistorial.getIdUsuario());
                }
            });
        }
    }

    private void abrirVentanaVerPerfil(int idUsuario) {
        Bundle bundle = new Bundle();
        bundle.putInt(PantallaPerfilUsuario.TAG_ID_USUARIO, idUsuario);
        startActivity(new Intent(this, PantallaPerfilUsuario.class).putExtras(bundle));
    }
}
