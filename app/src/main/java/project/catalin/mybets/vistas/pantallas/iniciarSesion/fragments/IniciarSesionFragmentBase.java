package project.catalin.mybets.vistas.pantallas.iniciarSesion.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewFragmentsLogin;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorFragmentOpcionesLogin;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFragmentOpcionesLogin;
import project.catalin.mybets.vistas.utils.CustomViewPager;

/**
 * Created by CDD on 24/05/2016.
 */
public class IniciarSesionFragmentBase extends Fragment implements ViewFragmentsLogin {
    private CustomViewPager mPagerFragmentOpciones;
    private ImageView mLogo;
    private PageAdapterFragment mAdapterPager;
    private ControllerFragmentOpcionesLogin mControladorFragmentOpcionesLogin;
    private Stack<Integer> mBackStack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.iniciar_sesion_fragment_principal, container, false);

        inicializarComponentes(layout);
        inicializarAdapter();
        inicializarStack();
        inicializarControlador();

        return layout;
    }

    private void inicializarStack() {
        mBackStack = new Stack<>();
    }

    private void inicializarControlador() {
        mControladorFragmentOpcionesLogin = new ControladorFragmentOpcionesLogin(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPagerFragmentOpciones.setCurrentItem(0);
        }
        return true;
    }

    private void inicializarAdapter() {
        mAdapterPager = new PageAdapterFragment(getFragmentManager());
        mPagerFragmentOpciones.setAdapter(mAdapterPager);
        mPagerFragmentOpciones.setPagingEnabled(false);
        }


    private void inicializarComponentes(View layout) {
        mPagerFragmentOpciones = (CustomViewPager) layout.findViewById(R.id.iniciar_sesion_fragment_principal_fragment_opciones);
        mLogo = (ImageView) layout.findViewById(R.id.iniciar_sesion_fragment_principal_logo);
    }

    @Override
    public void cambiarFragmentALogin() {
        mBackStack.push(mPagerFragmentOpciones.getCurrentItem());
        mPagerFragmentOpciones.setCurrentItem(1);
    }


    public boolean hasStack() {
        return mBackStack.size() > 0;
    }

    public void popBackStack() {
        mPagerFragmentOpciones.setCurrentItem(mBackStack.pop());
    }

    private class PageAdapterFragment extends FragmentPagerAdapter{
        List<Fragment> mListaFragment;

        public PageAdapterFragment(FragmentManager fm) {
            super(fm);
            mListaFragment = new LinkedList<>();
            mListaFragment.add(new IniciarSesionFragmentOpcionesLogin());
            mListaFragment.add(new IniciarSesionFragmentFormularioLogin());
        }

        @Override
        public Fragment getItem(int position) {
            return mListaFragment.get(position);
        }

        @Override
        public int getCount() {
            return mListaFragment.size();
        }
    }
}
