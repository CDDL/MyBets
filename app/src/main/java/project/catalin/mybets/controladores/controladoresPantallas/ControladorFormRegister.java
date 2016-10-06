package project.catalin.mybets.controladores.controladoresPantallas;

import org.json.JSONException;

import project.catalin.mybets.controladores.comunicacionDatos.DataRegister;
import project.catalin.mybets.controladores.comunicacionVista.ViewRegisterForm;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.controladores.utils.comunicación.Constantes;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.datosWebService.DatosUsuario;
import project.catalin.mybets.datos.utils.EncryptionUtils;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.datos.utils.UserInputValidationUtils;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerRegister;

/**
 * Created by Trabajo on 18/04/2016.
 */
public class ControladorFormRegister implements ControllerRegister {

    private ViewRegisterForm mViewRegister;
    private DataRegister mDataUsuario;

    public ControladorFormRegister(ViewRegisterForm viewRegisterForm) {
        mViewRegister = viewRegisterForm;
        mDataUsuario = new DatosUsuario();
    }

    @Override
    public void botonEnviarRegistroClick() {
        new TascaRegistrarUsuario().execute();
    }

    private class TascaRegistrarUsuario extends ExceptionHandlingAsyncTask<Void, Void, Void>{

        private Persona mUsuario;

        @Override
        protected void onPreExecute() {
            comprobarDatosCorrectos();
        }

        @Override
        protected Void executeTask(Void... params) throws Exception {
            Persona usuario = new Persona();
            usuario.setTelefono(mViewRegister.getTelefono());
            usuario.setUsername(mViewRegister.getUsername());
            usuario.setEmail(mViewRegister.getEmail());
            usuario.setNombre(mViewRegister.getNombre());
            usuario.setImagen(Constantes.BAS64_IMAGEN_DEFAULTO);
            mDataUsuario.registrarUsuario(usuario, EncryptionUtils.toMD5(mViewRegister.getPassword()));
            return null;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewRegister.alert(e.getMessage());
        }

        @Override
        protected void onTaskSuccess(Void aVoid) {
            mViewRegister.abrirPantallaPrincipal();
            LoginData loginData = new LoginData();
            loginData.setEmail(mViewRegister.getEmail());
            loginData.setPassword(mViewRegister.getPassword());
            try {
                SharedPreferencesUtils.guardarJsonLogin(JsonParserUtils.loginToJson(loginData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void comprobarDatosCorrectos() {
            if (mViewRegister.getEmail().equals("")) {
                mViewRegister.errorEmail("El email no puede estar vacío");
                cancel(true);
            }
            if (!UserInputValidationUtils.isEmailValido(mViewRegister.getEmail())) {
                mViewRegister.errorEmail("El email introducido no es válido");
                cancel(true);
            }
            if (mViewRegister.getPassword().equals("")) {
                mViewRegister.errorPassword("La contraseña no puede estar vacía");
                cancel(true);
            }
            if (!mViewRegister.getPassword().equals(mViewRegister.getComprobacionPassword())) {
                mViewRegister.errorComprobacionPassword("La contraseña y la comprobación de contraseña deben coincidir");
                cancel(true);
            }
            if (!UserInputValidationUtils.isTelefonoValido(mViewRegister.getTelefono())) {
                mViewRegister.errorTelefono("El telefono introducido no es válido");
                cancel(true);
            }
        }
    }
}
