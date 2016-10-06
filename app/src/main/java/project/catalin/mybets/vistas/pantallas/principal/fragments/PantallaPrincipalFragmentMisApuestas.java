package project.catalin.mybets.vistas.pantallas.principal.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPasadas;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaPartidasPendientes;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorApuestasPasadas;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorApuestasPendientes;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPasadas;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasPendientes;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallas.clasificacion.PantallaClasificacion;
import project.catalin.mybets.vistas.pantallas.historialJornada.PantallaHistorialJornada;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.utils.customAndroidComponents.PartidaView;

import static android.view.View.GONE;

/**
 * Created by Trabajo on 10/05/2016.
 */
public class PantallaPrincipalFragmentMisApuestas extends FragmentConTitulo implements ViewListaPartidasPendientes, ViewListaPartidasPasadas {

    private TextView mTextPendientes;
    private ListView mListaPendientes;
    private TextView mTextPasdas;
    private ListView mListaPasadas;
    private AdapterApuestasPendientes mAdapterApuestasPendientes;
    private AdapterApuestasPasadas mAdapterApuestasPasadas;
    private ControllerPartidasPendientes mControladorPartidasPendientes;
    private ControllerPartidasPasadas mControladorPartidasPasadas;
    private ProgressDialog mDialogLoadingPartidasPendientes;
    private ProgressDialog mDialogLoadingPartidasPasadas;
    private int mEdicionPartidaId;
    private String mEdicionPartidaTitulo;
    private TextView mTextNoTienesPasadas;
    private TextView mTextNoTienesPendientes;

    public PantallaPrincipalFragmentMisApuestas() {
        super();
        setIdTitulo(R.string.title_tab_mis_apuestas);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.pantalla_principal_fragment_mis_apuestas, container, false);

        inicializarComponentes(layout);
        inicializarAdapters();
        inicializarControladores();

        return layout;
    }

    private void inicializarControladores() {
        mControladorPartidasPendientes = new ControladorApuestasPendientes(this);
        mControladorPartidasPasadas = new ControladorApuestasPasadas(this);

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible)
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mControladorPartidasPasadas.inicializarVista();
                    mControladorPartidasPendientes.inicializarVista();
                }
            });
    }

    private void inicializarAdapters() {
        mAdapterApuestasPendientes = new AdapterApuestasPendientes();
        mAdapterApuestasPasadas = new AdapterApuestasPasadas();
        mListaPasadas.setAdapter(mAdapterApuestasPasadas);
        mListaPendientes.setAdapter(mAdapterApuestasPendientes);
    }

    private void inicializarComponentes(RelativeLayout layout) {
        mTextPendientes = (TextView) layout.findViewById(R.id.mis_apuestas_text_pendientes);
        mTextNoTienesPendientes = (TextView) layout.findViewById(R.id.mis_apuestas_text_pendientes_no_tienes);
        mTextNoTienesPasadas = (TextView) layout.findViewById(R.id.mis_apuestas_text_pasadas_no_tienes);
        mListaPendientes = (ListView) layout.findViewById(R.id.mis_apuestas_list_pendientes);
        mTextPasdas = (TextView) layout.findViewById(R.id.mis_apuestas_text_pasadas);
        mListaPasadas = (ListView) layout.findViewById(R.id.mis_apuestas_list_pasadas);
    }

    @Override
    public void showLoadingPartidasPendientes() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mDialogLoadingPartidasPendientes != null)
                        mDialogLoadingPartidasPendientes.dismiss();
                    mDialogLoadingPartidasPendientes = ProgressDialog.show(getActivity(), "", "Cargando partidas pendientes...", false, false);
                    mDialogLoadingPartidasPendientes.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },500);

    }

    @Override
    public void dismissLoadingPartidasPendientes() {
        if(mDialogLoadingPartidasPendientes != null) mDialogLoadingPartidasPendientes.dismiss();
    }

    @Override
    public void showLoadingPartidasPasadas() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mDialogLoadingPartidasPasadas != null) mDialogLoadingPartidasPasadas.dismiss();
                    if (mDialogLoadingPartidasPasadas == null)
                        mDialogLoadingPartidasPasadas = ProgressDialog.show(getActivity(), "", "Cargando partidas pasadas...", false, false);
                    mDialogLoadingPartidasPasadas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 500);

    }

    @Override
    public void dismissLoadingPartidasPasadas() {
        if(mDialogLoadingPartidasPasadas != null) mDialogLoadingPartidasPasadas.dismiss();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setListaPartidasPasadas(List<Partida> partidas) {
        mAdapterApuestasPasadas.recargarDatos(partidas);
    }

    @Override
    public void setListaPartidasPendientes(List<Partida> partidas) {
        mAdapterApuestasPendientes.recargarDatos(partidas);
    }

    @Override
    public int getIdPartidaRechazar() {
        return mEdicionPartidaId;
    }

    @Override
    public int getIdPartidaActualizar() {
        return mEdicionPartidaId;
    }

    @Override
    public String getNuevoNombrePartida() {
        return mEdicionPartidaTitulo;
    }

    private void startDialogEditarNombre(final int idPartida) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Editar titulo de la partida");
        final EditText nuevoNombre = new EditText(getContext());
        nuevoNombre.setInputType(InputType.TYPE_CLASS_TEXT);
        alertBuilder.setView(nuevoNombre);

        alertBuilder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEdicionPartidaId = idPartida;
                mEdicionPartidaTitulo = nuevoNombre.getText().toString();
                mControladorPartidasPendientes.botonActualizarNombrePulsado();
            }
        });
        alertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertBuilder.show();

    }

    private void startJuegaActivity(int idPartida, int tipoPartida, String nombrePartida) {
        Bundle bundle = new Bundle();
        bundle.putInt(PantallaApostar.TAG_ID_PARTIDA, idPartida);
        bundle.putString(PantallaApostar.TAG_NOMBRE_PARTIDA, nombrePartida);
        bundle.putInt(PantallaApostar.TAG_TIPO_PARTIDA, tipoPartida);
        startActivity(new Intent(getContext(), PantallaApostar.class).putExtras(bundle));
    }

    private void startHistorialActivity(int idPartida, int tipoPartida) {
        Bundle bundle = new Bundle();
        bundle.putInt(PantallaHistorialJornada.TAG_ID_PARTIDA, idPartida);
        bundle.putInt(PantallaHistorialJornada.TAG_TIPO_PARTIDA, tipoPartida);

        startActivity(new Intent(getContext(), PantallaHistorialJornada.class).putExtras(bundle));
    }

    private class AdapterApuestasPendientes extends AdapterRecargable<Partida> {
        @Override
        public long getItemId(int position) {
            return getItem(position).getIdPartida();
        }

        @Override
        public void recargarDatos(List<Partida> listaElementos) {
            super.recargarDatos(listaElementos);
            if (listaElementos.isEmpty()) mTextNoTienesPendientes.setVisibility(View.VISIBLE);
            else mTextNoTienesPendientes.setVisibility(GONE);
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            PartidaView partidaView = convertView == null ? (PartidaView) LayoutInflater.from(getContext()).inflate(R.layout.item_partida, parent, false) : (PartidaView) convertView;
            final Partida partida = getItem(position);

            partidaView.setTitulo(partida.getTitulo());
            partidaView.setFechaFin(partida.getFecha());
            partidaView.setBoteNum(partida.getBote());
            partidaView.setColorFondoIcono(partida.getColorIcono());
            partidaView.setUrlImagenIcono(partida.getUrlIcono());
            partidaView.setNumPersonas(partida.getNumPersonas());
            partidaView.setEstadoPartida(partida.getEstadoPartida());
            partidaView.setColorFecha(R.color.gris_medio);
            partidaView.setMostrarBotonAcciones(true);
            partidaView.setAccionesItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.mybets_action_editar_nombre_partida:
                            startDialogEditarNombre(partida.getIdPartida());
                            break;
                        case R.id.mybets_action_rechazar_partida:
                            mEdicionPartidaId = partida.getIdPartida();
                            mControladorPartidasPendientes.botonRechazarPartidaPulsado();
                            break;
                        case R.id.mybets_action_ver_clasificacion:
                            Bundle args = new Bundle();
                            args.putInt(PantallaClasificacion.TAG_ID_PARTIDA, partida.getIdPartida());
                            args.putString(PantallaClasificacion.TAG_COLOR, partida.getColorIcono());
                            startActivity(new Intent(getContext(), PantallaClasificacion.class).putExtras(args));
                    }
                    return true;
                }

            });
            partidaView.setBotonJuegaYaClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startJuegaActivity(partida.getIdPartida(), partida.getTipoPartida(), partida.getTitulo());
                }
            });

            if (partida.getEstadoPartida() == Partida.ESTADO_ESPERANDO_RESULTADOS)
                partidaView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startHistorialActivity(partida.getIdPartida(), partida.getTipoPartida());
                    }
                });

            return partidaView;
        }
    }

    private class AdapterApuestasPasadas extends AdapterRecargable<Partida> {
        @Override
        public long getItemId(int position) {
            return getItem(position).getIdPartida();
        }

        @Override
        public void recargarDatos(List<Partida> listaElementos) {
            super.recargarDatos(listaElementos);
            if (listaElementos.isEmpty()) mTextNoTienesPasadas.setVisibility(View.VISIBLE);
            else mTextNoTienesPasadas.setVisibility(GONE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PartidaView partidaView = convertView == null ? (PartidaView) LayoutInflater.from(getContext()).inflate(R.layout.item_partida, parent, false) : (PartidaView) convertView;
            final Partida partida = getItem(position);

            partidaView.setTitulo(partida.getTitulo());
            partidaView.setFechaFin(partida.getFecha());
            partidaView.setBoteNum(partida.getBote());
            partidaView.setNumPersonas(partida.getNumPersonas());
            partidaView.setPuntosGanados(partida.getPuntosGanados());
            partidaView.setColorFondoIcono(partida.getColorIcono());
            partidaView.setUrlImagenIcono(partida.getUrlIcono());
            partidaView.setColorFecha(R.color.gris_medio);
            partidaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startHistorialActivity(partida.getIdPartida(), partida.getTipoPartida());
                    Bundle args = new Bundle();
                    args.putInt(PantallaClasificacion.TAG_ID_PARTIDA, partida.getIdPartida());
                    args.putString(PantallaClasificacion.TAG_COLOR, partida.getColorIcono());
                    startActivity(new Intent(getContext(), PantallaClasificacion.class).putExtras(args));
                }
            });


            return partidaView;
        }
    }
}





















