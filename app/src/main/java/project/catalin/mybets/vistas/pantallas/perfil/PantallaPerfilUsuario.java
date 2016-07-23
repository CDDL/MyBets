package project.catalin.mybets.vistas.pantallas.perfil;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewDatosUsuario;
import project.catalin.mybets.controladores.comunicacionVista.ViewHistorial;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorDatosUsuario;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorHistorial;
import project.catalin.mybets.datos.dataObjects.EntradaHistorial;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerDatosUsuario;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerHistorial;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

public class PantallaPerfilUsuario extends AppCompatActivity implements ViewHistorial, ViewDatosUsuario {

    public static final String TAG_ID_USUARIO = "idUsuario";
    private HistorialAdapter mAdapter;
    private ListView mListaHistorial;
    private ControllerHistorial mControladorHistorial;
    private ControllerDatosUsuario mControladorDatosUsuario;
    private TextView mNombreUsuario;
    private TextView mPuntosUsuario;
    private CircleImageView mFotoPerfil;
    private ImageView mBotonCerrar;
    private ImageView mBotonEditar;
    private ProgressDialog mDialogLoadingPartidas;
    private ProgressDialog mDialogLoadingDatosUsuario;
    private int mIdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_usuario);

        inicializarComponentes();
        inicializarArgs();
        inicializarAdapter();
        inicializarBotones();
        inicializarControladores();
    }

    private void inicializarArgs() {
        mIdUsuario = getIntent().getIntExtra(TAG_ID_USUARIO, SharedPreferencesUtils.getMiId());
        if(mIdUsuario != SharedPreferencesUtils.getMiId()) mBotonEditar.setVisibility(View.GONE);
    }

    private void inicializarBotones() {
        mBotonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBotonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt(PantallaPerfilUsuario.TAG_ID_USUARIO, SharedPreferencesUtils.getMiId());
                startActivity(new Intent(PantallaPerfilUsuario.this, EditarPerfil.class));
            }
        });
    }

    private void inicializarComponentes() {
        mNombreUsuario = (TextView) findViewById(R.id.perfil_usuario_text_nombre);
        mPuntosUsuario = (TextView) findViewById(R.id.perfil_usuario_text_puntos);
        mListaHistorial = (ListView) findViewById(R.id.perfil_usuario_lista_historial);
        mFotoPerfil = (CircleImageView) findViewById(R.id.perfil_usuario_icon_foto_placeholder);
        mBotonCerrar = (ImageView) findViewById(R.id.perfil_usuario_boton_cerrar);
        mBotonEditar = (ImageView) findViewById(R.id.perfil_usuario_boton_editar);
    }

    private void inicializarControladores() {
        mControladorHistorial = new ControladorHistorial(this, mIdUsuario);
        mControladorDatosUsuario = new ControladorDatosUsuario(this, mIdUsuario);
        mControladorDatosUsuario.initVista();
        mControladorHistorial.initVista();
    }

    private void inicializarAdapter() {
        mAdapter = new HistorialAdapter();
        mListaHistorial.setAdapter(mAdapter);
    }

    @Override
    public void showLoadingHistorial() {
        mDialogLoadingPartidas = ProgressDialog.show(this, "", "Cargando historial...", false, false);
        mDialogLoadingPartidas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingHistorial() {
        mDialogLoadingPartidas.dismiss();
    }

    @Override
    public void showLoadingDatosUsuario() {
        if(mDialogLoadingDatosUsuario == null){
            mDialogLoadingDatosUsuario = ProgressDialog.show(this, "", "Cargando datos...", false, false);
            mDialogLoadingDatosUsuario.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        else
            mDialogLoadingDatosUsuario.show();
    }

    @Override
    public void dismissLoadingDatosUsuario() {
        mDialogLoadingDatosUsuario.dismiss();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDatosUsuario(Persona persona) {
        mNombreUsuario.setText(persona.getNombre());
        mPuntosUsuario.setText(MessageFormat.format(getString(R.string.perfil_format_puntos), persona.getPuntos()));
        Picasso.with(this)
                .load(persona.getImagen())
                .into(mFotoPerfil);
    }

    @Override
    public void setListaHistorial(List<EntradaHistorial> listaHistoriales) {
        mAdapter.recargarDatos(listaHistoriales);
    }

    private class HistorialAdapter extends AdapterRecargable<EntradaHistorial> {

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout = (RelativeLayout) (convertView == null ? View.inflate(PantallaPerfilUsuario.this, R.layout.item_historial, null) : convertView);
            EntradaHistorial entradaHistorial = getItem(position);

            TextView mTituloEntrada = (TextView) layout.findViewById(R.id.historial_text_titulo);
            TextView mPuntosModificados = (TextView) layout.findViewById(R.id.historial_text_puntos);
            TextView mLabelPuntosModificados = (TextView) layout.findViewById(R.id.historial_text_estadofinal);
            TextView mFecha = (TextView) layout.findViewById(R.id.historial_text_fecha);


            mTituloEntrada.setText(entradaHistorial.getTitulo());
            int puntos = entradaHistorial.getPuntos();
            mPuntosModificados.setText(puntos >= 0 ?
                    MessageFormat.format(getString(R.string.mi_perfil_formato_puntos_historial_ganados), puntos) :
                    MessageFormat.format(getString(R.string.mi_perfil_formato_puntos_historial_perdidos), puntos));

            mLabelPuntosModificados.setText(puntos >= 0 ?
                    getString(R.string.mi_perfil_text_label_puntos_ganados) :
                    getString(R.string.mi_perfil_text_label_puntos_perdidos)
            );

            mFecha.setText(new SimpleDateFormat(getString(R.string.mi_perfil_formato_fecha_entrada_historial), Locale.UK).format(entradaHistorial.getFecha()));

            return layout;
        }

    }
}
