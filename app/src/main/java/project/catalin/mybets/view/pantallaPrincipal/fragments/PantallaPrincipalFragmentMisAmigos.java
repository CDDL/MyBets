package project.catalin.mybets.view.pantallaPrincipal.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import project.catalin.mybets.R;
import project.catalin.mybets.controladores.pantallaPrincipal.fragments.ControladorMisAmigos;
import project.catalin.mybets.datos.objetosData.Persona;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentMisAmigos extends FragmentConTitulo {

    private AdaptadorEntradasLista adaptador;

    public PantallaPrincipalFragmentMisAmigos() {
        super();
        setIdTitulo(R.string.title_tab_amigos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.pantalla_principal_mis_amigos_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ControladorMisAmigos controladorMisAmigos = new ControladorMisAmigos(this);

        ListView listaElementos = (ListView) getActivity().findViewById(R.id.lista_contactos);
        adaptador = new AdaptadorEntradasLista(controladorMisAmigos.getContactos());
        listaElementos.setAdapter(adaptador);
    }


    public class AdaptadorEntradasLista extends BaseAdapter{


        private List<Persona> mListaEntradas;

        public AdaptadorEntradasLista(List<Persona> listaAmigos) {
            mListaEntradas = listaAmigos;
        }

        @Override
        public int getCount() {
            return mListaEntradas.size();
        }

        @Override
        public Object getItem(int position) {
            return mListaEntradas.get(position);
        }

        public void addItem(){
            mListaEntradas.add(null);
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {

            return 22;
//            return mListaEntradas.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View fila = convertView;

            if (fila == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                fila = inflater.inflate(R.layout.pantalla_principal_mis_amigos_fragment_list_item, parent, false);
            }

            return fila;
        }
    }
}
