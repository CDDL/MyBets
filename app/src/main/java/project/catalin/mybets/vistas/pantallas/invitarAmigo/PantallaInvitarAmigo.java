package project.catalin.mybets.vistas.pantallas.invitarAmigo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andraskindler.quickscroll.QuickScroll;
import com.andraskindler.quickscroll.Scrollable;
import com.squareup.picasso.Picasso;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaAmigos;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorInvitarAmigos;
import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerElegirOponentesBotones;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerInvitarAmigos;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerListaAmigos;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

public class PantallaInvitarAmigo extends AppCompatActivity implements ViewPantallaAmigos {

    private ControllerElegirOponentesBotones mControllerElegirOponentesBotones;
    private ControllerInvitarAmigos mControladorInvitarAmigos;
    private ControllerListaAmigos mControllerAmigos;
    private ProgressDialog mDialogLoadingAmigos;
    private AdapterPersonas mAdapterPersonas;
    private RelativeLayout mBotonFacebook;
    private Scrollable mAdapterScroll;
    private QuickScroll mQuickScroll;
    private TextView mBotonAceptar;
    private ListView mListaAmigos;
    private TextView mTextHeader;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_invitar_amigo);

        if (hayPermisos()) {
            inicializarComponentes();
            inicializarBotones();
            inicializarToolBar();
            inicializarAdapter();
            inicializarControlador();
        }
    }

    private boolean hayPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, Constantes.REQUEST_PERMISSION_CODE_READ_CONTACTS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) finish();
        else {
            inicializarComponentes();
            inicializarBotones();
            inicializarToolBar();
            inicializarAdapter();
            inicializarControlador();
        }
    }


    private void inicializarControlador() {
        mControladorInvitarAmigos = new ControladorInvitarAmigos(this);
        mControladorInvitarAmigos.inicializarVista();
    }

    private void inicializarBotones() {
        mBotonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBotonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PantallaInvitarAmigo.this, "TODO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarAdapter() {
        mAdapterPersonas = new AdapterPersonas();
        mListaAmigos.setAdapter(mAdapterPersonas);
        mQuickScroll.init(QuickScroll.TYPE_INDICATOR_WITH_HANDLE, mListaAmigos, mAdapterPersonas, QuickScroll.STYLE_HOLO);
        mQuickScroll.setFadeDuration(500);
        mQuickScroll.setHandlebarColor(Color.BLACK, Color.BLACK, Color.BLACK);
    }

    private void inicializarToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void inicializarComponentes() {
        mToolbar = (Toolbar) findViewById(R.id.pantalla_invitar_amigo_toolbar);
        mBotonFacebook = (RelativeLayout) findViewById(R.id.pantalla_invitar_amigo_boton_conectarme_facebook);
        mTextHeader = (TextView) findViewById(R.id.pantalla_invitar_amigo_text_header);
        mQuickScroll = (QuickScroll) findViewById(R.id.pantalla_invitar_amigo_quickscroll);
        mListaAmigos = (ListView) findViewById(R.id.pantalla_invitar_amigo_listview);
        mBotonAceptar = (TextView) findViewById(R.id.pantalla_invitar_amigo_boton_aceptar);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
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
                mAdapterPersonas.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return onQueryTextSubmit(newText);
            }
        });

        return true;
    }


    public void alert(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setData(List<Persona> personas) {
        mAdapterPersonas.recargarDatos(personas);
        if(personas.size()>0) mTextHeader.setText(personas.get(0).getNombre().substring(0, 1).toUpperCase());
    }


    @Override
    public void showLoadingAmigos() {
        mDialogLoadingAmigos = ProgressDialog.show(this, "", "Cargando contactos...", false, false);
        mDialogLoadingAmigos.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingAmigos() {
        mDialogLoadingAmigos.dismiss();
    }


    public class AdapterPersonas extends AdapterRecargable<Persona> implements Scrollable {


        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public boolean isFiltered(Persona elemento, String query) {
            return elemento.getNombre().toLowerCase().contains(query.toLowerCase());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layout = (RelativeLayout) (convertView == null ? View.inflate(PantallaInvitarAmigo.this, R.layout.item_amigo_invitable, null) : convertView);
            Persona persona = getItem(position);

            ImageView imagen = (ImageView) layout.findViewById(R.id.item_amigo_invitable_icon_imagen_placeholder);
            TextView nombre = (TextView) layout.findViewById(R.id.item_amigo_invitable_text_nombre);
            View botonAnadir = layout.findViewById(R.id.item_amigo_invitable_boton_añadir_amigo);
            View botonInvitar = layout.findViewById(R.id.item_amigo_invitable_boton_mandar_mensaje_amigo);

            Picasso.with(PantallaInvitarAmigo.this).load(persona.getImagen()).placeholder(R.drawable.mybets_icon_personas).into(imagen);
            nombre.setText(persona.getNombre());
            if(persona.estaEnElsistema()) botonAnadir.setVisibility(View.VISIBLE);
            else botonInvitar.setVisibility(View.VISIBLE);

            return layout;
        }

        @Override
        public String getIndicatorForPosition(int childposition, int groupposition) {
            return getItem(childposition).getNombre().substring(0, 1).toUpperCase();
        }

        @Override
        public int getScrollPosition(int childposition, int groupposition) {
            mTextHeader.setText(getItem(childposition).getNombre().substring(0, 1).toUpperCase());
            return childposition;
        }
    }
}
