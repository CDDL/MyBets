package project.catalin.mybets.vistas.pantallaPrincipal;

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

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.vistas.pantallaPrincipal.fragments.FragmentConTitulo;
import project.catalin.mybets.vistas.pantallaPrincipal.fragments.PantallaPrincipalFragmentApuestas;
import project.catalin.mybets.vistas.pantallaPrincipal.fragments.PantallaPrincipalFragmentListaAmigos;

public class PantallaPrincipal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.pantalla_principal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AdaptadorPestañas mSectionsPagerAdapter = new AdaptadorPestañas(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public class AdaptadorPestañas extends FragmentPagerAdapter {

        List<FragmentConTitulo> listaFragmentos;

        public AdaptadorPestañas(FragmentManager fm) {
            super(fm);
            listaFragmentos = new LinkedList<>();
            listaFragmentos.add(new PantallaPrincipalFragmentApuestas());
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
