package project.catalin.mybets.vistas.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Trabajo on 03/05/2016.
 */
public abstract class AdapterRecargable<T> extends BaseAdapter {
    private List<T> mListaElementos;

    public AdapterRecargable() {
        mListaElementos = Collections.emptyList();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListaElementos.size();
    }

    @Override
    public T getItem(int position) {
        return mListaElementos.get(position);
    }

    @Override
    public abstract long getItemId(int position);

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    protected List<T> getListaElementos() {
        return mListaElementos;
    }

    protected void setListaElementos(List<T> listaElementos) {
        mListaElementos = listaElementos;
        notifyDataSetChanged();
    }

    public void recargarDatos(List<T> listaNueva) {
        mListaElementos = listaNueva;
        notifyDataSetChanged();
    }
}
