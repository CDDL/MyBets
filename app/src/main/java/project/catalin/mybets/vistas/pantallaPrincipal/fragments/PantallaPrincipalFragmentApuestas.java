package project.catalin.mybets.vistas.pantallaPrincipal.fragments;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.pantallaPrincipal.fragments.ControllerFragmentApuestas;
import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.datos.objetosData.Partida;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador.ControllerPartidasPopulares;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentApuestas extends FragmentConTitulo {


    private BaseAdapter mAdaptadorContactos;
    private ControllerPartidasPopulares mController;

    public PantallaPrincipalFragmentApuestas() {
        super();
        setIdTitulo(R.string.title_tab_apuestas);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.pantalla_principal_fragment_apuestas, container, false);


        mController = new ControllerFragmentApuestas();
        mAdaptadorContactos = new AdaptadorEntradasLista(mController.getPartidasPopulares());
        ListView mListaElementos = (ListView) layout.findViewById(R.id.lista_apuestas);

        mListaElementos.setAdapter(mAdaptadorContactos);

        return layout;
    }

    public class AdaptadorEntradasLista extends BaseAdapter {


        private List<Partida> mListaEntradas;

        public AdaptadorEntradasLista(List<Partida> listaPartidas) {
            mListaEntradas = listaPartidas;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mListaEntradas.size();
        }

        @Override
        public Object getItem(int position) {
            return mListaEntradas.get(position);
        }


        @Override
        public long getItemId(int position) {
            return mListaEntradas.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView != null) return convertView;
            Partida partida = (Partida) getItem(position);
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.pantalla_principal_partida_publica_list_item, parent, false);

            GradientDrawable fondo = (GradientDrawable) layout.findViewById(R.id.partida_item_fondo_icon).getBackground();
            fondo.setColor(partida.getColorIcono());

            ImageView icono = (ImageView) layout.findViewById(R.id.partida_item_icon_placeholder);
            Picasso.with(getContext()).load(partida.getUrlIcono()).into(icono);

            TextView titulo = (TextView) layout.findViewById(R.id.partida_item_text_titulo);
            titulo.setText(partida.getTitulo());

            TextView bote = (TextView) layout.findViewById(R.id.partida_item_text_bote);
            bote.setText(MessageFormat.format("Bote: {0} puntos", numberFormat.format(partida.getBote())));

            TextView personas = (TextView) layout.findViewById(R.id.partida_item_text_num_personas);
            personas.setText(numberFormat.format(partida.getNumPersonas()));

            final Date fechaHoy = partida.getFecha();

            if (fechaHoy.getTime() < System.currentTimeMillis() + Constantes.TIEMPO_HORA_MS) {
                layout.findViewById(R.id.partida_item_icon_reloj).setVisibility(View.VISIBLE);

                final TextView countdown = (TextView) layout.findViewById(R.id.partida_item_text_countdown);
                countdown.setVisibility(View.VISIBLE);

                final SimpleDateFormat formatoCountdown = new SimpleDateFormat("HH : mm : ss", Locale.US);

                new CountDownTimer(fechaHoy.getTime(), System.currentTimeMillis()) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        countdown.setText(formatoCountdown.format(fechaHoy));
                    }

                    @Override
                    public void onFinish() {
                        countdown.setText("00 : 00 : 00");
                    }
                };
            } else {
                TextView fechaFin = (TextView) layout.findViewById(R.id.partida_item_text_fecha);
                fechaFin.setVisibility(View.VISIBLE);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.US);

                fechaFin.setText(MessageFormat.format("Fin {0}", formatoFecha.format(partida.getFecha())));
            }

            return layout;

        }

        public void addItem(Partida partida) {
            mListaEntradas.add(partida);
            notifyDataSetChanged();
        }

        public void removeItem(Partida persona) {
            mListaEntradas.remove(persona);
            notifyDataSetChanged();
        }

        public void recargarDatos(List<Partida> contactos) {
            mListaEntradas = contactos;
            notifyDataSetChanged();
        }
    }

}
