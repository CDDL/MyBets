package project.catalin.mybets.vistas.pantallas.iniciarSesion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewFragmentLoginPrincipal;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFragmentLoginPrincipal;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFragmentLoginPrincipal;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments.IniciarSesionFragmentBase;
import project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments.IniciarSesionFragmentFormularioRegistrarse;
import project.catalin.mybets.vistas.pantallas.principal.PantallaPrincipal;
import project.catalin.mybets.vistas.utils.CustomViewPager;

public class IniciarSesionPantallaPrincipal extends AppCompatActivity implements ViewFragmentLoginPrincipal {

    private ControllerFragmentLoginPrincipal mControladorFragmentLoginPrincipal;
    private CustomViewPager mPagerFragmentos;
    private AdapterFragmentos mAdapterFragmentos;
    private Stack<Integer> mBackStack;
    private IniciarSesionFragmentBase mFragmentBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoged();
        setContentView(R.layout.iniciar_sesion_activity_principal);

        inicializarComponentes();
        inicializarBackStack();
        inicializarAdapter();
        inicializarControlador();
    }

    private void checkLoged() {
        if (SharedPreferencesUtils.isLogged()) {
            startActivity(new Intent(this, PantallaPrincipal.class));
            finish();
        }
    }

    private void inicializarBackStack() {
        mBackStack = new Stack<>();
    }

    private void inicializarAdapter() {
        mAdapterFragmentos = new AdapterFragmentos(getSupportFragmentManager());
        mPagerFragmentos.setAdapter(mAdapterFragmentos);
        mPagerFragmentos.setPagingEnabled(false);
        mFragmentBase = (IniciarSesionFragmentBase) mAdapterFragmentos.getItem(0);
    }

    private void inicializarComponentes() {
        mPagerFragmentos = (CustomViewPager) findViewById(R.id.iniciar_sesion_activity_princpal_pager_fragments);
    }

    private void inicializarControlador() {
        mControladorFragmentLoginPrincipal = new ControladorFragmentLoginPrincipal(this);
    }

    @Override
    public void onBackPressed() {
        if  ( mFragmentBase.hasStack()) mFragmentBase.popBackStack();
        else if (hasStack()) popBackStack();
        else super.onBackPressed();
    }

    private void popBackStack() {
        mPagerFragmentos.setCurrentItem(mBackStack.pop());
    }

    private boolean hasStack() {
        return mBackStack.size() > 0;
    }

    @Override
    public void cambiarFragmentARegister() {
        mBackStack.push(mPagerFragmentos.getCurrentItem());
        mPagerFragmentos.setCurrentItem(1);
    }

    private class AdapterFragmentos extends FragmentPagerAdapter {
        private final List<Fragment> mListaFragmentos;

        public AdapterFragmentos(FragmentManager fm) {
            super(fm);
            mListaFragmentos = new LinkedList<>();
            mListaFragmentos.add(new IniciarSesionFragmentBase());
            mListaFragmentos.add(new IniciarSesionFragmentFormularioRegistrarse());
        }

        @Override
        public Fragment getItem(int position) {
            return mListaFragmentos.get(position);
        }

        @Override
        public int getCount() {
            return mListaFragmentos.size();
        }
    }
}
