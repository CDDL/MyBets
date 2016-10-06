package project.catalin.mybets.vistas.pantallas.principal.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.catalin.mybets.R;
import project.catalin.mybets.controladores.comunicacionVista.ViewListaAmigos;
import project.catalin.mybets.controladores.controladoresPantallas.ControladorAmigos;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerListaAmigos;
import project.catalin.mybets.vistas.pantallas.invitarAmigo.PantallaInvitarAmigo;
import project.catalin.mybets.vistas.utils.AdapterRecargable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentListaAmigos extends FragmentConTitulo implements ViewListaAmigos {

    private AdaptadorPersonas mAdaptadorContactos;
    private ControllerListaAmigos mControladorMisAmigos;
    private ListView mListaElementos;
    private ProgressDialog mDialogLoadingPartidas;
    private FloatingActionButton mBotonNuevoAmigo;
    private TextView mTextNoTienesAmigos;

    public PantallaPrincipalFragmentListaAmigos() {
        super();
        setIdTitulo(R.string.title_tab_amigos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.pantalla_principal_fragment_mis_amigos, container, false);

        inicializarComponentes(layout);
        inicializarAdapter();
        inicializarBotones();
        inicializarControlador();

        return layout;
    }

    private void inicializarBotones() {
        mBotonNuevoAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PantallaInvitarAmigo.class));
            }
        });
    }

    private void inicializarControlador() {
        mControladorMisAmigos = new ControladorAmigos(this);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible)
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mControladorMisAmigos.inicializarVista();
                }
            });
    }

    private void inicializarAdapter() {
        mAdaptadorContactos = new AdaptadorPersonas();
        mListaElementos.setAdapter(mAdaptadorContactos);
    }

    private void inicializarComponentes(View layout) {
        mListaElementos = (ListView) layout.findViewById(R.id.lista_contactos);
        mBotonNuevoAmigo = (FloatingActionButton) layout.findViewById(R.id.fab);
        mTextNoTienesAmigos = (TextView) layout.findViewById(R.id.mis_amigos_text_no_tienes);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mControladorMisAmigos.destroy();
    }

    @Override
    public void setListaAmigos(List<Persona> personas) {
        mAdaptadorContactos.recargarDatos(personas);
    }

    @Override
    public void alert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadgingAmigos() {
        mDialogLoadingPartidas = ProgressDialog.show(getContext(), "", "Cargando contactos...", false, false);
        mDialogLoadingPartidas.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void dismissLoadingAmigos() {
        mDialogLoadingPartidas.dismiss();
    }


    public class AdaptadorPersonas extends AdapterRecargable<Persona>{
        @Override
        public void recargarDatos(List<Persona> listaElementos) {
            super.recargarDatos(listaElementos);
            if(listaElementos.isEmpty()) mTextNoTienesAmigos.setVisibility(View.VISIBLE);
            else mTextNoTienesAmigos.setVisibility(View.GONE);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView != null) return convertView;

            LayoutInflater inflater = (LayoutInflater) getContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.item_pantalla_principal_contactos_list, parent, false);

            CircleImageView img = (CircleImageView) layout.findViewById(R.id.icono_foto_perfil);
            TextView nombre = (TextView) layout.findViewById(R.id.text_contacto_nombre);
            TextView desc = (TextView) layout.findViewById(R.id.text_contacto_desc);

            Persona persona = getItem(position);

            Picasso.with(getContext())
                    .load(persona.getImagen())
                    .into(img);

            nombre.setText(persona.getUsername());
            desc.setText(persona.getEmail());

            return layout;

        }
    }
}
