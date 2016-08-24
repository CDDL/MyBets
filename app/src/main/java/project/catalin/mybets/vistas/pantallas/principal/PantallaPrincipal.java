package project.catalin.mybets.vistas.pantallas.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewPantallaPrincipalPantallas;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorPantallaPrincipalPantallas;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.vistas.pantallas.configuracion.PantallaConfiguracion;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.IniciarSesionPantallaPrincipal;
import project.catalin.mybets.vistas.pantallas.perfil.PantallaPerfilUsuario;
import project.catalin.mybets.vistas.pantallas.principal.fragments.FragmentConTitulo;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentApuestas;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentListaAmigos;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentMisApuestas;
import project.catalin.mybets.vistas.pantallas.principal.fragments.PantallaPrincipalFragmentMuro;

public class PantallaPrincipal extends AppCompatActivity implements ViewPantallaPrincipalPantallas {


    private ViewPager mViewPager;
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private AdaptadorPestanas mAdaptadorPestañas;
    private ControladorPantallaPrincipalPantallas mControllerPantallas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        inicializarComponentes();
        inicializarToolBar();
        inicializarAdapters();
        inicializarControladores();
    }

    private void inicializarControladores()  {
        mControllerPantallas = new ControladorPantallaPrincipalPantallas(this);
    }

    private void inicializarAdapters() {
        mAdaptadorPestañas = new AdaptadorPestanas(getSupportFragmentManager());
        mViewPager.setAdapter(mAdaptadorPestañas);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void inicializarComponentes() {
        mToolBar = (Toolbar) findViewById(R.id.pantalla_principal_toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void inicializarToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mybets_action_configuracion:
                startActivity(new Intent(this, PantallaConfiguracion.class));
                break;
            case R.id.mybets_action_perfil:
                Bundle args = new Bundle();
                args.putInt(PantallaPerfilUsuario.TAG_ID_USUARIO, SharedPreferencesUtils.getMiId());
                startActivity(new Intent(this, PantallaPerfilUsuario.class).putExtras(args));
                break;
            case R.id.mybets_action_cerrar_sesion:
                startActivity(new Intent(this, IniciarSesionPantallaPrincipal.class));
                SharedPreferencesUtils.logOut();
                finish();
                break;
        }
        return true;
    }

    @Override
    public void cambiarAPantallaMisApuestas() {
        mViewPager.setCurrentItem(1);
    }

    public class AdaptadorPestanas extends FragmentPagerAdapter {

        List<FragmentConTitulo> listaFragmentos;

        public AdaptadorPestanas(FragmentManager fm) {
            super(fm);
            listaFragmentos = new LinkedList<>();
            listaFragmentos.add(new PantallaPrincipalFragmentApuestas());
            listaFragmentos.add(new PantallaPrincipalFragmentMisApuestas());
            listaFragmentos.add(new PantallaPrincipalFragmentMuro());
            listaFragmentos.add(new PantallaPrincipalFragmentListaAmigos());

        }

        @Override
        public Fragment getItem(int position) {
            return listaFragmentos.get(position);
        }

        @Override
        public int getCount() {
            return listaFragmentos.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(listaFragmentos.get(position).getTituloId());
        }
    }
}
