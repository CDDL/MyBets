package project.catalin.mybets.controladores.utils.comunicación.eventos;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Demils on 31/03/2016.
 */
public class GestorEventosUtil {

    private static Map<Integer, List<INotificable>> mapaEventos = new HashMap<>();

    public static void notificarEvento(int idEvento) {
        notificarEvento(idEvento, null);
    }

    public static void notificarEvento(final int idEvento, final Object info) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                List<INotificable> listaANotificar = mapaEventos.get(idEvento);
                if(listaANotificar == null ) return null;
                for (INotificable interesado:listaANotificar) interesado.notificar(idEvento, info);
                return null;
            }
        }.execute();
    }

    public static void suscribirseAEvento(final INotificable listener, final int idEvento) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                List<INotificable> listaParaNotificar = mapaEventos.get(idEvento);
                if(listaParaNotificar == null) {
                    listaParaNotificar = Collections.synchronizedList(new CopyOnWriteArrayList<INotificable>());
                    mapaEventos.put(idEvento, listaParaNotificar);
                }
                listaParaNotificar.add(listener);
                return null;
            }
        }.execute();
    }

    public static void desuscribirseDeEvento(final INotificable listener, final int idEvento) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                List<INotificable> listaParaNotificar = mapaEventos.get(idEvento);
                if(listaParaNotificar == null) return null;
                listaParaNotificar.remove(listener);
                return null;
            }
        }.execute();
    }

    public static void suscribirseAEventos(INotificable notificable, int[] eventos) {
        for(int evento:eventos) suscribirseAEvento(notificable, evento);
    }

    public static void desuscribirseDeEventos(INotificable notificable, int[] eventos) {
        for(int evento:eventos) desuscribirseDeEvento(notificable, evento);
    }
}
