package project.catalin.mybets.vistas.pantallas.categorias;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewSubcategorias;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorSubcategorias;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Subcategoria;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerSubcategorias;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentDialogJuegaYa;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.utils.CustomPagerRecargable;
import project.catalin.mybets.vistas.utils.customAndroidComponents.PartidaView;

public class PantallaSubCategorias extends AppCompatActivity implements ViewSubcategorias {

    public static final String TAG_ID_CATEGORIA = "idcategoria";
    public static final String TAG_NOMBRE_CATEGORIA = "nombreCategoria";
    private Toolbar mToolbar;
    private RecyclerView mIndicator;
    private ViewPager mPagerView;
    private PagerAdapterCategorias mPagerAdapter;
    private IndicatorAdapterBotones mIndicatorAdapter;
    private ControllerSubcategorias mControladorSubcategorias;
    private ProgressDialog mDialogLoadingSubcategorias;
    private int mIdCategoria;
    private String mNombreCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_subcategoria);

        inicializarComponentes();
        inicializarParametros();
        inicializarToolbar();
        inicializarAdapter();
        inicializarIndicator();
        inicializarControlador();
    }

    private void inicializarParametros() {
        Bundle bundle = getIntent().getExtras();
        mIdCategoria = bundle.getInt(TAG_ID_CATEGORIA);
        mNombreCategoria = bundle.getString(TAG_NOMBRE_CATEGORIA);
    }

    private void inicializarControlador() {
        mControladorSubcategorias = new ControladorSubcategorias(this, mIdCategoria);
        mControladorSubcategorias.inicializarVista();
    }

    private void inicializarIndicator() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mIndicator.setLayoutManager(layoutManager);
        mIndicatorAdapter = new IndicatorAdapterBotones();
        mIndicator.setAdapter(mIndicatorAdapter);
        mIndicator.addItemDecoration(new SpaceItemDecoration(40));

    }

    private void inicializarAdapter() {
        mPagerAdapter = new PagerAdapterCategorias();
        mPagerView.setAdapter(mPagerAdapter);
    }

    private void inicializarToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mNombreCategoria);
    }

    private void inicializarComponentes() {
        mToolbar = (Toolbar) findViewById(R.id.pantalla_subcategoria_toolbar);
        mIndicator = (RecyclerView) findViewById(R.id.pantalla_subcategoria_pageindicator);
        mPagerView = (ViewPager) findViewById(R.id.pantalla_subcategoria_viewpager);
    }

    @Override
    public void dismissLoadingSubcategoria() {
        mDialogLoadingSubcategorias.dismiss();
    }

    @Override
    public void showLoadingSubcategoria() {
        mDialogLoadingSubcategorias = ProgressDialog.show(this, "", "Cargando subcategoria...", false, false);
        mDialogLoadingSubcategorias.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDatos(List<Subcategoria> subcategorias) {
        mPagerAdapter.recargarDatos(subcategorias);
        mIndicatorAdapter.recargarDatos(subcategorias);
        mPagerView.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mybets_action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPagerAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPagerAdapter.filter(newText);
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

    private class ListaPartidasAdapter extends AdapterRecargable<Partida> {
        @Override
        public long getItemId(int position) {
            return getItem(position).getIdPartida();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PartidaView partidaView = convertView == null ? (PartidaView) LayoutInflater.from(PantallaSubCategorias.this).inflate(R.layout.item_partida, parent, false) : (PartidaView) convertView;
            final Partida partida = getItem(position);

            partidaView.setColorFondoIcono(partida.getColorIcono());
            partidaView.setUrlImagenIcono(partida.getUrlIcono());
            partidaView.setMostrarBotonAcciones(false);
            partidaView.setTitulo(partida.getTitulo());
            partidaView.setFechaFin(partida.getFecha());
            partidaView.setBoteNum(partida.getBote());
            partidaView.setNumPersonas(partida.getNumPersonas());
            partidaView.setEstadoPartida(partida.getEstadoPartida());
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

        @Override
        public boolean isFiltered(Partida elemento, String query) {
            return elemento.getTitulo().toLowerCase().contains(query.toLowerCase());
        }
    }

    private class PagerAdapterCategorias extends CustomPagerRecargable<Subcategoria> {

        private List<AdapterRecargable<Partida>> mListaAdapters;

        public PagerAdapterCategorias() {
            this.mListaAdapters = new LinkedList<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ListView listView = (ListView) LayoutInflater.from(PantallaSubCategorias.this).inflate(R.layout.itempager_lista_partidas, container, false);
            AdapterRecargable<Partida> adapter = new ListaPartidasAdapter();
            adapter.recargarDatos(getItemPosition(position).getListPartidas());
            listView.setAdapter(adapter);
            mListaAdapters.add(adapter);
            container.addView(listView);

            return listView;
        }

        @Override
        public int getIconResId(int index) {
            return R.drawable.mybets_boton_categoria_azul_oscuro;
        }


        public void filter(String query) {
            for (AdapterRecargable<Partida> adapter:mListaAdapters) adapter.filter(query);
        }
    }


    private class IndicatorAdapterBotones extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<Subcategoria> mListaBotones ;
        private List<TextView> mListaTexts;

        public IndicatorAdapterBotones() {
            mListaBotones = Collections.emptyList();
            mListaTexts = new LinkedList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.itemindicator_boton_categoria, null);
            mListaTexts.add(v);
            return new ViewHolderBotonCategoria(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            mListaTexts.get(position).setText(getItem(position).getNombreSubcategoria().toUpperCase());
            mListaTexts.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPagerView.setCurrentItem(position);
                }
            });
        }

        private Subcategoria getItem(int position) {
            return mListaBotones.get(position);
        }

        @Override
        public int getItemCount() {
            return mListaBotones.size();
        }

        public void recargarDatos(List<Subcategoria> subcategorias) {
            mListaBotones = subcategorias;
            notifyDataSetChanged();
        }
    }

    private class ViewHolderBotonCategoria extends RecyclerView.ViewHolder {
        public TextView mText;

        public ViewHolderBotonCategoria(View itemView) {
            super(itemView);
            mText = (TextView) itemView;
        }
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mSpacing;

        public SpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mSpacing = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.right = mSpacing;
        }
    }

}
