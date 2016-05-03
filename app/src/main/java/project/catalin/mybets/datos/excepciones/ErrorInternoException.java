package project.catalin.mybets.datos.excepciones;

/**
 * Created by Catalin on 07/04/2016.
 */
public class ErrorInternoException extends Exception {
    public ErrorInternoException() {
        super("Ha ocurrido un error al intentar realizar la operación.");
    }
}
