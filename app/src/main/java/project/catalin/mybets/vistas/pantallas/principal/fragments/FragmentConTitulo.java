package project.catalin.mybets.vistas.pantallas.principal.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by Catalin on 05/04/2016.
 */
public abstract class FragmentConTitulo extends Fragment{
    int mTitulo;

    public void setIdTitulo(int titulo) {
        this.mTitulo = titulo;
    }

    public int getTituloId() {
        return mTitulo;
    }
}
