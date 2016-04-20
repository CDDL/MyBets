package project.catalin.mybets.controladores.iniciarSesion.fragments;

import project.catalin.mybets.controladores.iniciarSesion.comunicaciónDatos.DataRegister;
import project.catalin.mybets.controladores.iniciarSesion.comunicaciónVista.ViewRegisterForm;
import project.catalin.mybets.datos.GestorDataWebServices;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.ErrorInternoException;
import project.catalin.mybets.datos.excepciones.ErrorServerException;
import project.catalin.mybets.datos.excepciones.NombreVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;
import project.catalin.mybets.datos.excepciones.UsuarioRepetidoException;
import project.catalin.mybets.datos.objetosData.Persona;
import project.catalin.mybets.vistas.iniciarSesion.comunicaciónControlador.ControllerRegister;

/**
 * Created by Trabajo on 18/04/2016.
 */
public class ControladorIniciarSesionFormRegister implements ControllerRegister {

    private final DataRegister mDatosRegister;
    private final ViewRegisterForm mRegisterView;

    public ControladorIniciarSesionFormRegister(ViewRegisterForm viewRegisterForm) {
        mRegisterView = viewRegisterForm;
        mDatosRegister = new GestorDataWebServices();
    }

    @Override
    public void botonEnviarRegistroClick() {
        try {
            mDatosRegister.registrarUsuario(new Persona(
                    mRegisterView.getEmail(),
                    mRegisterView.getNombre(),
                    mRegisterView.getUsername(),
                    mRegisterView.getTelefono()
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
