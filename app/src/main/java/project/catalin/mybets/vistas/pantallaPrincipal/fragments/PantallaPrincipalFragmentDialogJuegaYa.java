package project.catalin.mybets.vistas.pantallaPrincipal.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.catalin.mybets.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PantallaPrincipalFragmentDialogJuegaYa extends DialogFragment {


    public PantallaPrincipalFragmentDialogJuegaYa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pantalla_principal_fragment_dialog_juega_ya, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(100, 100);
        return view;
    }

}
