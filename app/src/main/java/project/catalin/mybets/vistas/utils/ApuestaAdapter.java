package project.catalin.mybets.vistas.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.catalin.mybets.datos.dataObjects.Partido;
import project.catalin.mybets.vistas.utils.customAndroidComponents.AdapterRecargable;

/**
 * Created by Trabajo on 04/05/2016.
 */
public abstract class ApuestaAdapter extends AdapterRecargable<Partido> {

    private Map<Integer, Integer> mApuestas;

    public ApuestaAdapter() {
        mApuestas = new HashMap<>();
    }

    public void addApuesta(int pos, int score) {
        mApuestas.put(pos, score);
    }

    public List<Integer> getApuestas() {
        ArrayList<Integer> listaResult = new ArrayList<>(mApuestas.size());
        Collections.fill(listaResult, null);

        for (Map.Entry<Integer, Integer> apuesta : mApuestas.entrySet())
            listaResult.set(apuesta.getKey(), apuesta.getValue());


        return listaResult;
    }
}
