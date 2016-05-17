package project.catalin.mybets.vistas.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Trabajo on 03/05/2016.
 */
public abstract class AdapterRecargable<T> extends BaseAdapter {
    private List<T> mListaElementosFiltrados;
    private List<T> mListaElementos;

    public AdapterRecargable() {
        mListaElementosFiltrados = Collections.emptyList();
        mListaElementos = Collections.emptyList();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListaElementosFiltrados.size();
    }

    @Override
    public T getItem(int position) {
        return mListaElementosFiltrados.get(position);
    }

    @Override
    public abstract long getItemId(int position);

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    protected List<T> getListaElementos() {
        return mListaElementosFiltrados;
    }

    protected void setListaElementos(List<T> listaElementos) {
        mListaElementosFiltrados = listaElementos;
        notifyDataSetChanged();
    }

    public void recargarDatos(List<T> listaNueva) {
        mListaElementosFiltrados = listaNueva;
        mListaElementos = listaNueva;
        notifyDataSetChanged();
    }

    public final void filter(String query){
        List<T> nuevaLista = new LinkedList<>();
        for (T elemento: mListaElementos) if(isFiltered(elemento, query))
            nuevaLista.add(elemento);
        setListaElementos(nuevaLista);
    }

    public boolean isFiltered(T elemento, String query) {
        return true;
    }

}
