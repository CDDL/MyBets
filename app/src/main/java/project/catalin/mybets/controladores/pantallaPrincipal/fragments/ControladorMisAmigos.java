package project.catalin.mybets.controladores.pantallaPrincipal.fragments;

import java.util.ArrayList;
import java.util.List;

import project.catalin.mybets.datos.objetosData.Persona;

/**
 * Created by Catalin on 05/04/2016.
 */
public class ControladorMisAmigos {
    private List<Persona> listaAmigos;

    public ControladorMisAmigos(List<Persona> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public List<Persona> getListaAmigos() {
        listaAmigos = new ArrayList<>();
        listaAmigos.add(null);
        listaAmigos.add(null);

        return listaAmigos;
    }
}
