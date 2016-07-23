package project.catalin.mybets.controladores.controladoresPantallas;

import project.catalin.mybets.controladores.comunicacionDatos.DataIdentificación;
import project.catalin.mybets.controladores.comunicacionVista.ViewLoginForm;
import project.catalin.mybets.controladores.utils.ExceptionHandlingAsyncTask;
import project.catalin.mybets.datos.dataObjects.LoginData;
import project.catalin.mybets.datos.dataObjects.Persona;
import project.catalin.mybets.datos.datosWebService.DatosIdentificación;
import project.catalin.mybets.datos.utils.JsonParserUtils;
import project.catalin.mybets.datos.utils.SharedPreferencesUtils;
import project.catalin.mybets.datos.utils.UserInputValidationUtils;
import project.catalin.mybets.vistas.comunicacionControlador.ControllerFormLogin;

/**
 * Created by Catalin on 31/03/2016.
 */
public class ControladorFormLogin implements ControllerFormLogin {

    private DataIdentificación mDatosCredenciales;
    private ViewLoginForm mViewLogin;

    public ControladorFormLogin(ViewLoginForm loginView) {
        mViewLogin = loginView;
        mDatosCredenciales = new DatosIdentificación();
    }


    @Override
    public void botonLoginPulsado() {
        new TascaIdentificarUsuario().execute();
    }

    private class TascaIdentificarUsuario extends ExceptionHandlingAsyncTask<Void, Void, Persona>{
        private final LoginData mLoginData;

        public TascaIdentificarUsuario() {
            mLoginData = new LoginData();
            mLoginData.setEmail(mViewLogin.getEmail());
            mLoginData.setPassword(mViewLogin.getPassword());
        }

        @Override
        protected void onPreExecute() {
            if (!UserInputValidationUtils.isEmailValido(mLoginData.getEmail())) {
                mViewLogin.setEmailError("El formato del email no es válido");
                cancel(true);
            }
        }

        @Override
        protected Persona executeTask(Void... params) throws Exception {
            Persona result = mDatosCredenciales.validarIdentificación(mLoginData);
            SharedPreferencesUtils.guardarJsonLogin(JsonParserUtils.loginToJson(mLoginData));
            return result;
        }

        @Override
        protected void onTaskFailture(Exception e) {
            mViewLogin.alert(e.getMessage());

        }

        @Override
        protected void onTaskSuccess(Persona datosUsuario) {
            SharedPreferencesUtils.guardarIdUsuario(datosUsuario.getId());
            mViewLogin.abrirPantallaPrincipal();
        }
    }
}
