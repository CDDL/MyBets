package project.catalin.mybets.datos.utils;

import java.util.regex.Pattern;

import project.catalin.mybets.datos.excepciones.ContraseñaVaciaException;
import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;

/**
 * Created by Catalin on 07/04/2016.
 */
public class UserInputValidationUtils {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_PATTERN = "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?";

    public static void validarEmail(String email) throws EmailVacioException, EmailMalFormadoException {
        if(email == null || email.isEmpty()) throw new EmailVacioException();
        if(!Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) throw new EmailMalFormadoException();
    }

    public static void validarNombre(String nombre) {
    }

    public static void validarUsername(String username) {

    }

    public static void validarTelefono(String telefono) throws TelefonoMalFormadoException {
        if(!Pattern.compile(PHONE_PATTERN).matcher(telefono).matches()) throw new TelefonoMalFormadoException();
    }

    public static void validarContraseña(String contraseña) throws ContraseñaVaciaException {
        if(contraseña == null || contraseña.isEmpty()) throw new ContraseñaVaciaException();
    }

    public static boolean isEmailValido(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isTelefonoValido(String telefono) {
        return Pattern.compile(PHONE_PATTERN).matcher(telefono).matches();
    }
}
