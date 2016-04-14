package project.catalin.mybets.comunicación.eventos;

public abstract class TipoEvento{
    public static final int PULSADO_BOTON_LOGIN_PANTALLA_INICIO = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAIL = 2;
    public static final int ERROR_INTERNO = 3;
    public static final int CAMPO_CONTRASEÑA_VACÍO = 4;
    public static final int CAMPO_EMAIL_VACIO = 5;
    public static final int CAMPO_EMAIL_MALFORMADO = 6;
    public static final int ERROR_SERVER = 7;
}