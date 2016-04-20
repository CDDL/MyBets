package project.catalin.mybets.controladores.utils.comunicación;

import org.junit.Test;

import project.catalin.mybets.controladores.utils.comunicación.eventos.GestorEventosUtil;
import project.catalin.mybets.controladores.utils.comunicación.eventos.INotificable;

import static org.junit.Assert.*;

/**
 * Created by Demils on 31/03/2016.
 */
public class GestorEventosUtilTest {


    @Test
    public void testEnviarReicibir(){
        GestorEventosUtil.suscribirseAEvento(new INotificable() {
            @Override
            public void notificar(int idEvento, Object info) {
                assertTrue(idEvento == 0);
            }
        }, 0);

        GestorEventosUtil.notificarEvento(0);
    }

    @Test
    public void testRegistradoEventoEspecifico(){
        GestorEventosUtil.suscribirseAEvento(new INotificable() {
            @Override
            public void notificar(int idEvento, Object info) {
                fail("Se ha notificado un evento al que no estaba suscrito.");
            }
        }, 1);

        GestorEventosUtil.notificarEvento(2);
    }

    @Test
    public void testDesuscribirseEvento(){
        INotificable notificable = new INotificable() {
            @Override
            public void notificar(int idEvento, Object info) {
                fail("Se ha notificado un evento del que se ha desuscrito.");
            }
        };

        GestorEventosUtil.suscribirseAEvento(notificable,0);
        GestorEventosUtil.desuscribirseDeEvento(notificable, 0);
        GestorEventosUtil.notificarEvento(0);
    }
}
