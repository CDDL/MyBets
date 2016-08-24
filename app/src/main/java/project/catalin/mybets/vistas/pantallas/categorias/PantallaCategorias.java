package project.catalin.mybets.vistas.pantallas.categorias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewCategorias;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorCategorias;
import project.catalin.mybets.datos.dataObjects.Categoria;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerCategorias;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

public class PantallaCategorias extends AppCompatActivity implements ViewCategorias {

    private Toolbar mToolbar;
    private ListView mListaCategorias;
    private AdapterCategorias mAdapterCategorias;
    private ControllerCategorias mControladorCategorias;
    private ProgressDialog mDialogLoadingCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_categorias_lista);

        inicializarComponentes();
        inicializarToolbar();
        inicializarAdapter();
        inicializarControlador();
    }

    private void inicializarControlador() {
        mControladorCategorias = new ControladorCategorias(this);
        mControladorCategorias.inicializarVista();
    }

    private void inicializarAdapter() {
        mAdapterCategorias = new AdapterCategorias();
        mListaCategorias.setAdapter(mAdapterCategorias);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mybets_action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapterCategorias.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapterCategorias.filter(newText);
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

    private void inicializarToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.pantalla_categorias_text_titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void inicializarComponentes() {
        mToolbar = (Toolbar) findViewById(R.id.pantalla_categorias_toolbar);
        mListaCategorias = (ListView) findViewById(R.id.pantalla_categorias_list_categorias);
    }

    @Override
    public void showLoadingCategorias() {
        mDialogLoadingCategorias = ProgressDialog.show(this, "", "Cargando categorias...", false, false);
        mDialogLoadingCategorias.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingCategorias() {
        mDialogLoadingCategorias.dismiss();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setListaCategorias(List<Categoria> categorias) {
        mAdapterCategorias.recargarDatos(categorias);
    }

    private class AdapterCategorias extends AdapterRecargable<Categoria> {
        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LinearLayout layout = convertView == null ? (LinearLayout) View.inflate(PantallaCategorias.this, R.layout.item_categoria, null) : (LinearLayout) convertView;
            final Categoria categoria = getItem(position);
            ImageView iconoCategoria = (ImageView) layout.findViewById(R.id.item_categoria_icon_categoria);
            TextView textCategoria = (TextView) layout.findViewById(R.id.item_categoria_text_nombre_categoria);
            LinearLayout cuerpoCategoria = (LinearLayout) layout.findViewById(R.id.item_categoria_cuerpo);

            Picasso.with(PantallaCategorias.this).load(categoria.getUrlIcono()).into(iconoCategoria);
            textCategoria.setText(categoria.getNombre());
            cuerpoCategoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt(PantallaSubCategorias.TAG_ID_CATEGORIA, categoria.getId());
                    args.putString(PantallaSubCategorias.TAG_NOMBRE_CATEGORIA, categoria.getNombre());
                    startActivity(new Intent(PantallaCategorias.this, PantallaSubCategorias.class).putExtras(args));
                }
            });


            return layout;
        }

        @Override
        public boolean isFiltered(Categoria elemento, String query) {
            return elemento.getNombre().toLowerCase().contains(query.toLowerCase());
        }
    }
}
