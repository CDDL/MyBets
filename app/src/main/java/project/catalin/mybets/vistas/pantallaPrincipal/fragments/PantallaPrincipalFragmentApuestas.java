package project.catalin.mybets.vistas.pantallaPrincipal.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónVista.ViewListaPartidas;
import project.catalin.mybets.controladores.pantallaPrincipal.fragments.ControladorFragmentApuestas;
import project.catalin.mybets.datos.objetosData.Partida;
import project.catalin.mybets.vistas.customAndroidComponents.PartidaView;
import project.catalin.mybets.vistas.pantallaApostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador.ControllerPartidasPopulares;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentApuestas extends FragmentConTitulo implements ViewListaPartidas {


    private AdaptadorEntradasLista mAdaptadorContactos;
    private ControllerPartidasPopulares mController;
    private ProgressDialog mDialogLoadingPartidas;

    public PantallaPrincipalFragmentApuestas() {
        super();
        setIdTitulo(R.string.title_tab_apuestas);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pantalla_principal_fragment_apuestas, container, false);

        mAdaptadorContactos = new AdaptadorEntradasLista();
        mController = new ControladorFragmentApuestas(this);
        mController.getPartidasPopulares();

        ListView mListaElementos = (ListView) layout.findViewById(R.id.lista_apuestas);
        mListaElementos.setAdapter(mAdaptadorContactos);

        return layout;
    }

    @Override
    public void alert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingPartidas() {
        mDialogLoadingPartidas = ProgressDialog.show(getContext(), "", "Cargando partidas...", false, false);
        mDialogLoadingPartidas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

    }

    @Override
    public void dismissLoadingPartidas() {
        mDialogLoadingPartidas.dismiss();
    }

    @Override
    public void setListaPartidas(List<Partida> partidas) {
        mAdaptadorContactos.recargarDatos(partidas);
    }

    public class AdaptadorEntradasLista extends BaseAdapter {


        private List<Partida> mListaEntradas;

        public AdaptadorEntradasLista() {
            mListaEntradas = Collections.emptyList();
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
            return mListaEntradas.get(position).getIdPartida();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Partida partida = (Partida) getItem(position);

            PartidaView partidaView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                partidaView = (PartidaView) inflater.inflate(R.layout.pantalla_principal_fragment_apuestas_item, parent, false);
            } else partidaView = (PartidaView) convertView;

            partidaView.setColorFondoIcono(partida.getColorIcono());

            partidaView.setUrlImagenIcono(partida.getUrlIcono());

            partidaView.setTitulo(partida.getTitulo());

            partidaView.setBoteNum(partida.getBote());

            partidaView.setNumPersonas(partida.getNumPersonas());

            partidaView.setFechaFin(partida.getFecha());

            partidaView.setJuegaYaButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PantallaPrincipalFragmentDialogJuegaYa.TAG_COLOR, partida.getColorIcono());
                    bundle.putString(PantallaPrincipalFragmentDialogJuegaYa.TAG_TITULO, partida.getTitulo());
                    bundle.putString(PantallaPrincipalFragmentDialogJuegaYa.TAG_ICON, partida.getUrlIcono());
                    bundle.putInt(PantallaPrincipalFragmentDialogJuegaYa.TAG_BOTE, partida.getBote());
                    bundle.putInt(PantallaApostar.TAG_TIPO_PARTIDA, partida.getTipoPartida());
                    bundle.putInt(PantallaApostar.TAG_ID_PARTIDA, partida.getIdPartida());

                    PantallaPrincipalFragmentDialogJuegaYa dialogFramgnet = new PantallaPrincipalFragmentDialogJuegaYa();
                    dialogFramgnet.setArguments(bundle);
                    dialogFramgnet.show(getFragmentManager(), "DialogJuegaYa");
                }
            });


            return partidaView;

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
