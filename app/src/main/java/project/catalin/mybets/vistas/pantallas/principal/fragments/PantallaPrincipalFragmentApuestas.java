package project.catalin.mybets.vistas.pantallas.principal.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicaciónVista.ViewListaPartidas;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorApuestas;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerPartidasPopulares;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.utils.customAndroidComponents.PartidaView;

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
        mController = new ControladorApuestas(this);
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

    public class AdaptadorEntradasLista extends AdapterRecargable<Partida> {

        @Override
        public long getItemId(int position) {
            return getItem(position).getIdPartida();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Partida partida = getItem(position);
            PartidaView partidaView = convertView == null? (PartidaView) LayoutInflater.from(getContext()).inflate(R.layout.item_partida, parent, false): (PartidaView) convertView;

            partidaView.setUrlImagenIcono(partida.getUrlIcono());
            partidaView.setColorFondoIcono(partida.getColorIcono());
            partidaView.setTitulo(partida.getTitulo());
            partidaView.setBoteNum(partida.getBote());
            partidaView.setNumPersonas(partida.getNumPersonas());
            partidaView.setFechaFin(partida.getFecha());
            partidaView.setBotonJuegaYaClickListener(new View.OnClickListener() {
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
    }
}
