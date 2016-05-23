package project.catalin.mybets.vistas.pantallas.principal.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewMuro;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorMuro;
import project.catalin.mybets.datos.dataObjects.EntradaMuro;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerMuro;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentMuro extends FragmentConTitulo implements ViewMuro {


    private ListView mListEntradasMuro;
    private AdapterEntradasMuro mAdapterEntradas;
    private ControllerMuro mControllerMuro;
    private ProgressDialog mDialogLoadingEntradas;

    public PantallaPrincipalFragmentMuro() {
        super();
        setIdTitulo(R.string.fragment_muro_titulo);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pantalla_principal_fragment_muro, container, false);

        inicializarComponentes(layout);
        inicializarAdapter();
        inicializarController();

        return layout;
    }

    private void inicializarController() {
        mControllerMuro = new ControladorMuro(this);
        mControllerMuro.inicializarVista();
    }

    private void inicializarAdapter() {
        mAdapterEntradas = new AdapterEntradasMuro();
        mListEntradasMuro.setAdapter(mAdapterEntradas);
    }

    private void inicializarComponentes(View layout) {
        mListEntradasMuro = (ListView) layout.findViewById(R.id.muro_list_entradas);
    }

    @Override
    public void alert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingEntradas() {
        mDialogLoadingEntradas = ProgressDialog.show(getContext(), "", "Cargando muro...", false, false);
        mDialogLoadingEntradas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingEntradas() {
        mDialogLoadingEntradas.dismiss();
    }

    @Override
    public void setData(List<EntradaMuro> entradasMuro) {
        mAdapterEntradas.recargarDatos(entradasMuro);
    }

    public class AdapterEntradasMuro extends AdapterRecargable<EntradaMuro> {

        private List<String> mListaCabeceras;

        public AdapterEntradasMuro() {
            mListaCabeceras = new ArrayList<>();
        }

        @Override

        public long getItemId(int position) {
            return getItem(position).getFecha().getTime();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EntradaMuro entrada = getItem(position);
            Persona persona = entrada.getPersona();

            RelativeLayout layout = convertView == null ? (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_muro_entrada, parent, false) : (RelativeLayout) convertView;
            CircleImageView fotoPerfil = (CircleImageView) layout.findViewById(R.id.item_muro_entrada_icon_foto_placeholder);
            TextView nombreUsuario = (TextView) layout.findViewById(R.id.item_muro_entrada_text_nombre_usuario);
            TextView textoLabel = (TextView) layout.findViewById(R.id.item_muro_entrada_text_puntos_1);
            TextView textoPuntos = (TextView) layout.findViewById(R.id.item_muro_entrada_text_puntos_2);
            TextView nombrePartida = (TextView) layout.findViewById(R.id.item_muro_entrada_text_nombre_partida);

            Picasso.with(getContext()).load(persona.getImagen()).into(fotoPerfil);
            nombreUsuario.setText(persona.getNombre());
            textoLabel.setText(MessageFormat.format(getContext().getString(R.string.fragment_muro_formato_text_1), persona.getNombre()));
            textoPuntos.setText(MessageFormat.format(getContext().getString(R.string.fragment_muro_format_text_2), entrada.getPuntos()));
            nombrePartida.setText(entrada.getNombrePartida());

            if (!existeCabeceraFecha(entrada.getFecha())) {
                TextView fecha = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_list_header_fecha, parent, false);
                fecha.setText(new SimpleDateFormat("EEEE dd 'de' MMMM", Locale.ENGLISH).format(entrada.getFecha()));
                layout.addView(fecha);
                añadirCabeceraFecha(entrada.getFecha());
            }

            return layout;

        }

        private void añadirCabeceraFecha(Date fecha) {
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM", Locale.UK);
            String identificadorFecha = formater.format(fecha);
            mListaCabeceras.add(identificadorFecha);
        }

        private boolean existeCabeceraFecha(Date fecha) {
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM", Locale.UK);
            String identificadorFecha = formater.format(fecha);
            return mListaCabeceras.contains(identificadorFecha);
        }
    }
}
