package project.catalin.mybets.vistas.pantallaPrincipal.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.catalin.mybets.R;
import project.catalin.mybets.controladores.pantallaPrincipal.comunicaciónVista.MisAmigosView;
import project.catalin.mybets.controladores.pantallaPrincipal.fragments.ControladorMisAmigos;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador.ControllerMisAmigos;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentMisAmigos extends FragmentConTitulo implements MisAmigosView {

    private AdaptadorEntradasLista mAdaptadorContactos;
    private ControllerMisAmigos mControladorMisAmigos;
    private ListView mListaElementos;

    public PantallaPrincipalFragmentMisAmigos() {
        super();
        setIdTitulo(R.string.title_tab_amigos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pantalla_principal_fragment_mis_amigos, container, false);

        mControladorMisAmigos = new ControladorMisAmigos(this);
        mAdaptadorContactos = new AdaptadorEntradasLista(mControladorMisAmigos.getContactos());
        mListaElementos = (ListView) layout.findViewById(R.id.lista_contactos);

        mListaElementos.setAdapter(mAdaptadorContactos);

        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mControladorMisAmigos.destroy();
    }

    @Override
    public void add(Persona persona) {
        mAdaptadorContactos.addItem(persona);
    }

    @Override
    public void remove(Persona persona) {
        mAdaptadorContactos.removeItem(persona);
    }

    @Override
    public void recargarAmigos() {
        mAdaptadorContactos.recargarDatos(mControladorMisAmigos.getContactos());
    }

    @Override
    public void mensaje(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    public class AdaptadorEntradasLista extends BaseAdapter{


        private List<Persona> mListaEntradas;

        public AdaptadorEntradasLista(List<Persona> listaAmigos) {
            mListaEntradas = listaAmigos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mListaEntradas.size();
        }

        @Override
        public Object getItem(int position) {
            return mListaEntradas.get(position);
        }


        @Override
        public long getItemId(int position) {

            return mListaEntradas.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView != null) return convertView;

            LayoutInflater inflater = (LayoutInflater) getContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.pantalla_principal_contactos_list_item, parent, false);

            CircleImageView img = (CircleImageView) layout.findViewById(R.id.icono_foto_perfil);
            TextView nombre = (TextView) layout.findViewById(R.id.text_contacto_nombre);
            TextView desc = (TextView) layout.findViewById(R.id.text_contacto_desc);

            Persona persona = mListaEntradas.get(position);

            Picasso.with(getContext())
                    .load(persona.getImage())
                    .into(img);

            nombre.setText(persona.getUsername());
            desc.setText(persona.getEmail());

            return layout;

        }

        public void addItem(Persona persona) {
            mListaEntradas.add(persona);
            notifyDataSetChanged();
        }

        public void removeItem(Persona persona) {
            mListaEntradas.remove(persona);
            notifyDataSetChanged();
        }

        public void recargarDatos(List<Persona> contactos) {
            mListaEntradas = contactos;
            notifyDataSetChanged();
        }
    }
}
