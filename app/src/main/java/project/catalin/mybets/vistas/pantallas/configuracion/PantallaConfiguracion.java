package project.catalin.mybets.vistas.pantallas.configuracion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.Collections;

import project.catalin.mybets.R;
import project.catalin.mybets.datos.dataObjects.Configuracion;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

public class PantallaConfiguracion extends AppCompatActivity {

    private ListView mListaOpcionesConfiguracion;
    private Toolbar mToolbar;
    private AdapterRecargable<Configuracion> mAdapterConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_configuracion);

        inicializarComponentes();
        inicializarToolbar();
        inicializarAdapter();
    }

    private void inicializarAdapter() {
        mAdapterConfiguracion = new AdapterConfiguracion();
        mListaOpcionesConfiguracion.setAdapter(mAdapterConfiguracion);
        mAdapterConfiguracion.recargarDatos(Collections.<Configuracion>singletonList(null));
    }

    private void inicializarToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.pantalla_configuracion_title);
    }

    private void inicializarComponentes() {
        mListaOpcionesConfiguracion = (ListView) findViewById(R.id.pantalla_configuracion_lista_configuraciones);
        mToolbar = (Toolbar) findViewById(R.id.pantalla_configuracion_toolbar);
    }

    private class AdapterConfiguracion extends AdapterRecargable<Configuracion> {
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout = (RelativeLayout) (convertView == null ? View.inflate(PantallaConfiguracion.this, R.layout.item_configuracion, null) : convertView);

            return layout;
        }
    }
}
