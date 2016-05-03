package project.catalin.mybets.vistas.pantallaElegirOponentes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.pantallaElegirOponentes.ControladorPantallaElegirOponentes;
import project.catalin.mybets.controladores.pantallaPrincipal.fragments.ControladorFragmentApuestas;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.vistas.pantallaElegirOponentes.comunicaciónControlador.ControllerListaAmigos;

public class PantallaElegirOponentes extends AppCompatActivity {

    private AdaptadorPersonas mAdaptadorPersonas;
    private ControllerListaAmigos mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_elegir_oponentes);

        setSupportActionBar((Toolbar) findViewById(R.id.elegir_oponentes_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdaptadorPersonas = new AdaptadorPersonas();
        mController = new ControladorPantallaElegirOponentes(this);
        mController.getListaAmigos();

        ListView mListContactos = (ListView) findViewById(R.id.elegir_oponentes_search_list);
        mListContactos.setAdapter(mAdaptadorPersonas);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.mybets_action_search).getActionView();
        searchView.setQueryHint(getString(R.string.text_cuadro_busqueda_hint));
        searchView.setIconified(false);

        return true;
    }


    public class AdaptadorPersonas extends BaseAdapter {

        private final List<Persona> mPersonas;

        public AdaptadorPersonas () {
            mPersonas = Collections.emptyList();
        }

        public int getCount() {
            return mPersonas.size();
        }

        public Persona getItem(int position) {
            return mPersonas.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout;
            Persona persona = mPersonas.get(position);

            if(convertView != null) layout = (RelativeLayout) convertView;
            else layout = (RelativeLayout) View.inflate(PantallaElegirOponentes.this, R.layout.pantalla_elegir_oponentes_item, null);

            ImageView mIcono = (ImageView) layout.findViewById(R.id.elegir_oponentes_icon_placeholder);
            Picasso.with(PantallaElegirOponentes.this)
                    .load(persona.getImage())
                    .into(mIcono);

            TextView nombre = (TextView) layout.findViewById(R.id.elegir_oponentes_text_nombre);
            nombre.setText(persona.getNombre());

            return layout;
        }

    }

}
