package project.catalin.mybets.vistas.pantallas.principal.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidas;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPopulares;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPrincipalApuestas;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorPantallaPrincipalApuestas;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorPartidasPopulares;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPantallaPrincipalApuestas;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPopulares;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallas.categorias.PantallaCategorias;
import project.catalin.mybets.vistas.pantallas.categorias.PantallaFutbol;
import project.catalin.mybets.vistas.pantallas.categorias.PantallaPopulares;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.utils.customAndroidComponents.PartidaView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentApuestas extends FragmentConTitulo implements ViewPantallaPrincipalApuestas, ViewListaPartidasPopulares {


    private AdapterPartidas mAdapterPartidas;
    private ProgressDialog mDialogLoadingPartidas;
    private ControllerPartidasPopulares mControllerPartidasPopulares;
    private ListView mListaPartidasHoy;
    private Button mBotonCategorias;
    private Button mBotonPopulares;
    private Button mBotonFutbol;
    private TextView mBotonMas;
    private ControllerPantallaPrincipalApuestas mControllerPantallaPrincipalApuestas;
    private int mIdFutbol;

    public PantallaPrincipalFragmentApuestas() {
        super();
        setIdTitulo(R.string.title_tab_apuestas);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pantalla_principal_fragment_apuestas, container, false);

        inicializarComponentes(layout);
        inicializarAdapter();
        inicializarControladores();
        inicializarBotones();

        return layout;
    }

    private void inicializarBotones() {
        mBotonCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerPantallaPrincipalApuestas.botonCategoriaPulsado();
            }
        });
        mBotonPopulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerPantallaPrincipalApuestas.botonPopularesPulsado();
            }
        });
        mBotonFutbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerPantallaPrincipalApuestas.botonFutbolPulsado();
            }
        });
    }

    private void inicializarControladores() {
        mControllerPantallaPrincipalApuestas = new ControladorPantallaPrincipalApuestas(this);
        mControllerPartidasPopulares = new ControladorPartidasPopulares(this);
        mControllerPartidasPopulares.inicializarVista();
    }

    private void inicializarAdapter() {
        mAdapterPartidas = new AdapterPartidas();
        mListaPartidasHoy.setAdapter(mAdapterPartidas);

    }

    private void inicializarComponentes(View layout) {
        mListaPartidasHoy = (ListView) layout.findViewById(R.id.pantalla_principal_fragment_apuestas_apuestas);
        mBotonCategorias = (Button) layout.findViewById(R.id.pantalla_principal_fragment_apuestas_boton_categoria);
        mBotonPopulares = (Button) layout.findViewById(R.id.pantalla_principal_fragment_apuestas_boton_populares);
        mBotonFutbol = (Button) layout.findViewById(R.id.pantalla_principal_fragment_apuestas_boton_futbol);
        mBotonMas = (TextView) layout.findViewById(R.id.pantalla_principal_fragment_apuestas_boton_mas);
    }

    @Override
    public void alert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setListaPartidasPopulares(List<Partida> partidas) {
        mAdapterPartidas.recargarDatos(partidas);
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
    public void iniciarPantallaCategorias() {
        startActivity(new Intent(getActivity(), PantallaCategorias.class));
    }

    @Override
    public void iniciarPantallaPartidasPopulares() {
        startActivity(new Intent(getActivity(), PantallaPopulares.class));
    }

    @Override
    public void iniciarPantallaCategoriaFutbol() {
        Bundle args = new Bundle();
        args.putInt(PantallaFutbol.TAG_ID_FUTBOL, mIdFutbol);
        startActivity(new Intent(getActivity(), PantallaFutbol.class).putExtras(args));
    }

    @Override
    public void setIdFutbol(int idFutbol) {
        mIdFutbol = idFutbol;
    }

    public class AdapterPartidas extends AdapterRecargable<Partida> {

        @Override
        public long getItemId(int position) {
            return getItem(position).getIdPartida();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Partida partida = getItem(position);
            PartidaView partidaView = convertView == null ? (PartidaView) LayoutInflater.from(getContext()).inflate(R.layout.item_partida, parent, false) : (PartidaView) convertView;

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
