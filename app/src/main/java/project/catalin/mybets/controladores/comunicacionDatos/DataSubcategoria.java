package project.catalin.mybets.controladores.comunicacionDatos;

import java.util.List;

import project.catalin.mybets.datos.dataObjects.FichaSubcategoria;
import project.catalin.mybets.datos.dataObjects.Partida;
import project.catalin.mybets.datos.dataObjects.Subcategoria;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;

/**
 * Created by CDD on 17/05/2016.
 */
public interface DataSubcategoria {
    List<Partida> getListPartidosSubcategoriaId(int id) throws ErrorInternoException, ErrorServerException;
}
