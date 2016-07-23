package project.catalin.mybets.vistas.pantallas.clasificacion;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewFichaClasificacion;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFichaClasificacion;
import project.catalin.mybets.datos.dataObjects.EntradaClasificacion;
import project.catalin.mybets.datos.dataObjects.FichaClasificacion;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.utils.DateUtils;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFichaClasificacion;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

/**
 * Created by CDD on 16/05/2016.
 */
public class PantallaClasificacion extends AppCompatActivity implements ViewFichaClasificacion {

    public static final String TAG_ID_PARTIDA = "idpartida";
    public static final String TAG_COLOR = "colorpartida";
    private RelativeLayout mFondoCabecera;
    private ImageView mBotonCerrar;
    private TextView mNombrePartida;
    private TextView mFecha;
    private TextView mBote;
    private ImageView mIconoFondo;
    private ListView mListClasificacion;
    private AdapterRecargable<EntradaClasificacion> mAdapterClasificacion;
    private ControllerFichaClasificacion mControladorFichaClasificacion;
    private int mIdPartida;
    private ProgressDialog mDialogLoadingClasificacion;
    private String mColorIcono;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasificacion_en_partida);

        inicializarComponentes();
        inicializarArgumentos();
        inicializarBotones();
        inicializarAdapters();
        inicializarControladores();

    }

    private void inicializarArgumentos() {
        Bundle args = getIntent().getExtras();
        mIdPartida = args.getInt(TAG_ID_PARTIDA);
        mColorIcono = args.getString(TAG_COLOR);
        GradientDrawable fondo = (GradientDrawable) mFondoCabecera.getBackground();
        fondo.setColor(Color.parseColor(mColorIcono));
    }

    private void inicializarControladores() {
        mControladorFichaClasificacion = new ControladorFichaClasificacion(this, mIdPartida);
        mControladorFichaClasificacion.inicializarVista();
    }

    private void inicializarAdapters() {
        mAdapterClasificacion = new AdapterClasificacion();
        mListClasificacion.setAdapter(mAdapterClasificacion);
    }

    private void inicializarBotones() {
        mBotonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inicializarComponentes() {
        mFondoCabecera = (RelativeLayout) findViewById(R.id.clasificacion_en_partida_fondo_cabecera);
        mBotonCerrar = (ImageView) findViewById(R.id.clasificacion_enPartida_boton_cerrar);
        mNombrePartida = (TextView) findViewById(R.id.clasificacion_en_partida_text_titulo);
        mFecha = (TextView) findViewById(R.id.clasificacion_en_partida_text_fecha);
        mBote = (TextView) findViewById(R.id.clasificacion_en_partida_text_bote);
        mIconoFondo = (ImageView) findViewById(R.id.clasificacion_en_partida_icon_cabecera);
        mListClasificacion = (ListView) findViewById(R.id.clasificacion_en_partida_list_clasificaciones);
    }

    @Override
    public void showLoadingClasificacion() {
        mDialogLoadingClasificacion = ProgressDialog.show(this, "", "Cargando clasificacion...", false, false);
        mDialogLoadingClasificacion.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingClasificacion() {
        mDialogLoadingClasificacion.dismiss();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void setDatosClasificacion(FichaClasificacion fichaClasificacion) {
        mNombrePartida.setText(fichaClasificacion.getNombrePartida());
        mFecha.setText(new DateUtils().from(fichaClasificacion.getFecha()).to("dd/MM/yyyy"));
        mBote.setText(MessageFormat.format(getString(R.string.ficha_clasificacion_format_bote), fichaClasificacion.getBote()));
        Picasso.with(this).load(fichaClasificacion.getUrlIcono()).into(mIconoFondo);
        mAdapterClasificacion.recargarDatos(fichaClasificacion.getEntradas());

    }

    private class AdapterClasificacion extends AdapterRecargable<EntradaClasificacion> {
        @Override
        public long getItemId(int position) {
            return getItem(position).getPosicion();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout = (RelativeLayout) (convertView == null ? View.inflate(PantallaClasificacion.this, R.layout.item_clasificacion_en_partida, null) : convertView);
            EntradaClasificacion entradaClasificacion = getItem(position);
            Persona persona = entradaClasificacion.getPersona();

            TextView textClasificacion = (TextView) layout.findViewById(R.id.item_clasificacion_en_partida_text_clasificacion);
            TextView textNombre = (TextView) layout.findViewById(R.id.item_clasificacion_en_partida_text_nombre);
            TextView textPuntos = (TextView) layout.findViewById(R.id.item_clasificacion_en_partida_text_puntos);
            ImageView imageFotoPerfil = (ImageView) layout.findViewById(R.id.item_clasificacion_en_partida_icon_foto_placeholder);

            Picasso.with(PantallaClasificacion.this).load(persona.getImagen()).into(imageFotoPerfil);
            textClasificacion.setText(String.valueOf(entradaClasificacion.getPosicion()));
            textNombre.setText(persona.getNombre());
            textPuntos.setText(MessageFormat.format(getString(R.string.entrada_clasificacion_format_puntos), entradaClasificacion.getPuntos()));

            return layout;
        }

    }
}
