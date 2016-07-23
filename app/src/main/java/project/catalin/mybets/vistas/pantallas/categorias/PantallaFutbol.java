package project.catalin.mybets.vistas.pantallas.categorias;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPartidasFutbol;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorPartidasFutbol;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerPartidasHoy;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentDialogJuegaYa;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.utils.customAndroidComponents.PartidaView;

/**
 * Created by CDD on 19/05/2016.
 */
public class PantallaFutbol extends AppCompatActivity implements ViewPantallaPartidasFutbol {

    public static final String TAG_ID_FUTBOL = "idfutbol";
    private Toolbar mToolbar;
    private ListView mListaPartidas;
    private AdapterPartidas mAdapterPartidas;
    private ControllerPartidasHoy mControladorPartidasHoy;
    private ProgressDialog mDialogLoadingPartidasPopulares;
    private int mIdSubcategoriaFutbol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_categoria_sin_subcategorias);

        inicializarComponentes();
        inicializarToolbar();
        inicializarAdapter();
        inicializarArgs();
        inicializarControlador();
    }

    private void inicializarArgs() {
        Bundle bundle = getIntent().getExtras();
        mIdSubcategoriaFutbol = bundle.getInt(TAG_ID_FUTBOL, -1);
    }

    private void inicializarControlador() {
        mControladorPartidasHoy = new ControladorPartidasFutbol(this, mIdSubcategoriaFutbol);
        mControladorPartidasHoy.inicializarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mybets_action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapterPartidas.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapterPartidas.filter(newText);
                return true;
            }
        });
        return true;
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



    private void inicializarAdapter() {
        mAdapterPartidas = new AdapterPartidas();
        mListaPartidas.setAdapter(mAdapterPartidas);
    }

    private void inicializarToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.pantalla_partidas_futbol_titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void inicializarComponentes() {
        mToolbar = (Toolbar) findViewById(R.id.pantalla_categoria_sin_subcategorias_toolbar);
        mListaPartidas = (ListView) findViewById(R.id.pantalla_categoria_sin_subcategorias_list);
    }

    @Override
    public void showLoadingPartidas() {
        mDialogLoadingPartidasPopulares = ProgressDialog.show(this, "", "Cargando partidas...", false, false);
        mDialogLoadingPartidasPopulares.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingPartidas() {
        mDialogLoadingPartidasPopulares.dismiss();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(List<Partida> partidas) {
        mAdapterPartidas.recargarDatos(partidas);
    }

    public class AdapterPartidas extends AdapterRecargable<Partida> {

        @Override
        public long getItemId(int position) {
            return getItem(position).getIdPartida();
        }

        @Override
        public boolean isFiltered(Partida elemento, String query) {
            return elemento.getTitulo().toLowerCase().contains(query.toLowerCase());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Partida partida = getItem(position);
            PartidaView partidaView = convertView == null? (PartidaView) LayoutInflater.from(PantallaFutbol.this).inflate(R.layout.item_partida, parent, false): (PartidaView) convertView;

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
                    dialogFramgnet.show(getSupportFragmentManager(), "DialogJuegaYa");
                }
            });


            return partidaView;

        }
    }
}
