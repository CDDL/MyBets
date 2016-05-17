package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.dataObjects.EntradaHistorial;

/**
 * Created by Trabajo on 06/05/2016.
 */
public interface DataHistorialUsuario {
    List<EntradaHistorial> getHistorialUsuario(int idUsuario) throws ErrorServerException, ErrorInternoException;
}
