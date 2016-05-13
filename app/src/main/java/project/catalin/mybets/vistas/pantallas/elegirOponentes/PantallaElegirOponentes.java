package project.catalin.mybets.vistas.pantallas.elegirOponentes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicaciónVista.ViewListaAmigos;
import project.catalin.mybets.controladores.comunicaciónVista.ViewOponentes;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorAmigos;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorElegirOponentesBotones;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerElegirOponentesBotones;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerListaAmigos;
import project.catalin.mybets.vistas.utils.AdapterRecargable;
import project.catalin.mybets.vistas.pantallas.apostar.PantallaApostar;

public class PantallaElegirOponentes extends AppCompatActivity implements ViewListaAmigos, ViewOponentes {

    private AdaptadorPersonas mAdaptadorPersonas;
    private ControllerListaAmigos mControllerAmigos;
    private ControllerElegirOponentesBotones mControllerElegirOponentesBotones;
    private ProgressDialog mDialogLoadingPartidas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elegir_oponentes);

        crearToolBar();
        crearAdapter();
        crearControladores();
        iniciarlizarBotonJuegaYa();

    }

    private void iniciarlizarBotonJuegaYa() {
        View botonJuegaYa = findViewById(R.id.elegir_oponentes_boton_juega_ya);
        botonJuegaYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mControllerElegirOponentesBotones.botonJuegaYaPulsado();
            }
        });
    }

    private void crearControladores() {
        mControllerElegirOponentesBotones = new ControladorElegirOponentesBotones(this);
        mControllerAmigos = new ControladorAmigos(this);
        mControllerAmigos.getContactos();

    }

    private void crearAdapter() {
        mAdaptadorPersonas = new AdaptadorPersonas();

        ListView mListContactos = (ListView) findViewById(R.id.elegir_oponentes_search_list);
        mListContactos.setAdapter(mAdaptadorPersonas);
    }

    private void crearToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.elegir_oponentes_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mybets_action_search).getActionView();
        searchView.setQueryHint(getString(R.string.text_cuadro_busqueda_hint));
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdaptadorPersonas.filtrar(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return onQueryTextSubmit(newText);
            }
        });

        return true;
    }

    @Override
    public void setListaAmigos(List<Persona> contactos) {
        mAdaptadorPersonas.recargarDatos(contactos);
    }

    @Override
    public int getIdJuego() {
        return getIntent().getIntExtra(PantallaApostar.TAG_TIPO_PARTIDA, -1);
    }

    @Override
    public List<Persona> getListaOponentes() {
        return mAdaptadorPersonas.getListaChecked();
    }

    @Override
    public void cerrar() {
        finish();
    }

    @Override
    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadgingAmigos() {
        mDialogLoadingPartidas = ProgressDialog.show(this, "", "Cargando contactos...", false, false);
        mDialogLoadingPartidas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingAmigos() {
        mDialogLoadingPartidas.dismiss();
    }


    public class AdaptadorPersonas extends AdapterRecargable<Persona> {

        private List<Persona> mDatosReales;
        private List<Persona> oponentes = new LinkedList<>();

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout;
            final Persona persona = getItem(position);

            if (convertView != null) layout = (RelativeLayout) convertView;
            else
                layout = (RelativeLayout) View.inflate(PantallaElegirOponentes.this, R.layout.elegir_oponentes_item, null);

            ImageView mIcono = (ImageView) layout.findViewById(R.id.elegir_oponentes_icon_placeholder);
            Picasso.with(PantallaElegirOponentes.this)
                    .load(persona.getImagen())
                    .into(mIcono);

            TextView nombre = (TextView) layout.findViewById(R.id.elegir_oponentes_text_nombre);
            nombre.setText(persona.getNombre());

            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.elegir_oponentes_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) oponentes.add(persona);
                    else oponentes.remove(persona);
                }
            });

            return layout;
        }

        @Override
        public void recargarDatos(List<Persona> listaNueva) {
            super.recargarDatos(listaNueva);
            mDatosReales = listaNueva;
        }


        public void filtrar(String query) {
            List<Persona> nuevaLista = new LinkedList<>();
            for (Persona persona: mDatosReales) if(persona.getNombre().toLowerCase().contains(query.toLowerCase()))
                nuevaLista.add(persona);
            setListaElementos(nuevaLista);
        }

        public List<Persona> getListaChecked() {
            return oponentes;
        }
    }
}
