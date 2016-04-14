package project.catalin.mybets.datos.utils;

import org.junit.Test;

import project.catalin.mybets.datos.excepciones.EmailMalFormadoException;
import project.catalin.mybets.datos.excepciones.EmailVacioException;
import project.catalin.mybets.datos.excepciones.TelefonoMalFormadoException;

import static org.junit.Assert.fail;

/**
 * Created by Catalin on 07/04/2016.
 */
public class UserInputValidationUtilsTest {
    @Test
    public void validarEmail_emailValido(){
        String email = "email@email.com";
        try {
            UserInputValidationUtils.validarEmail(email);
        } catch (Exception e) {
            fail("La validación ha dado negativo.");
        }
    }

    @Test
    public void validarEmail_emailInvalido_sinArroba(){
        String email = "emailemail.com";
        try {
            UserInputValidationUtils.validarEmail(email);
            fail("La validación ha dado positivo.");
        } catch (EmailMalFormadoException e) {
        } catch (Exception e) {
            fail("La validación ha fallado donde no debia.");
        }
    }

    @Test
    public void validarEmail_emailInvalido_sinExtensiónFinal(){
        String email = "email@email";
        try {
            UserInputValidationUtils.validarEmail(email);
            fail("La validación ha dado positivo.");
        } catch (EmailMalFormadoException e) {
        } catch (Exception e) {
            fail("La validación ha fallado donde no debia.");
        }
    }

    @Test
    public void validarEmail_emailInvalido_vacío() throws EmailMalFormadoException {
        String email = "";
        try {
            UserInputValidationUtils.validarEmail(email);
            fail("La validación ha dado positivo.");
        } catch (EmailVacioException e) {}
    }

    @Test
    public void validarTeléfono_teléfonoValido(){
        String telefono = "+678892214";
        try {
            UserInputValidationUtils.validarTelefono(telefono);
        } catch (TelefonoMalFormadoException e) {
            fail("La validación ha dado negativo.");
        }
    }

    @Test
    public void validarTeléfono_teléfonoInvalido_longitudTeléfono(){
        String telefono = "+424213";
        try {
            UserInputValidationUtils.validarTelefono(telefono);
            fail("La validación ha dado positivo.");
        } catch (TelefonoMalFormadoException e) {
        }
    }
}
