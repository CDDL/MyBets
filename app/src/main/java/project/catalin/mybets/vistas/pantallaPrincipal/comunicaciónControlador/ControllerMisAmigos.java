package project.catalin.mybets.vistas.pantallaPrincipal.comunicaciónControlador;

import java.util.List;

import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.vistas.iniciarSesion.comunicaciónControlador.ControllerDestructible;

/**
 * Created by Trabajo on 18/04/2016.
 */
public interface ControllerMisAmigos extends ControllerDestructible {
    List<Persona> getContactos();

}
