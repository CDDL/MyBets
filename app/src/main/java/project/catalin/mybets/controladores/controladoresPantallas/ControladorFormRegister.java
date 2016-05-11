package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicaciónDatos.DataRegister;
import project.catalin.mybets.controladores.comunicaciónVista.ViewRegisterForm;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.vistas.comunicaciónControlador.ControllerRegister;

/**
 * Created by Trabajo on 18/04/2016.
 */
public class ControladorFormRegister implements ControllerRegister {

    private final DataRegister mDatosRegister;
    private final ViewRegisterForm mRegisterView;

    public ControladorFormRegister(ViewRegisterForm viewRegisterForm) {
        mRegisterView = viewRegisterForm;
        mDatosRegister = new GestorDataWebServices();
    }

    @Override
    public void botonEnviarRegistroClick() {
        try {
            mDatosRegister.registrarUsuario(new Persona(
            ), mRegisterView.getPassword());
        } catch (EmailMalFormadoException e) {
            e.printStackTrace();
        } catch (UsuarioRepetidoException e) {
            e.printStackTrace();
        } catch (TelefonoMalFormadoException e) {
            e.printStackTrace();
        } catch (EmailVacioException e) {
            e.printStackTrace();
        } catch (NombreVacioException e) {
            e.printStackTrace();
        } catch (ErrorInternoException e) {
            e.printStackTrace();
        } catch (ErrorServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {}
}
