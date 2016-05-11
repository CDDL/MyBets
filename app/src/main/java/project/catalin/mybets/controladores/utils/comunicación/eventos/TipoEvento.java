package project.catalin.mybets.controladores.utils.comunicación.eventos;

public abstract class TipoEvento{
    public static final int PULSADO_BOTON_LOGIN_PANTALLA_INICIO = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAIL = 2;
    public static final int ERROR_INTERNO = 3;
    public static final int CAMPO_CONTRASEÑA_VACÍO = 4;
    public static final int CAMPO_EMAIL_VACIO = 5;
    public static final int CAMPO_EMAIL_MALFORMADO = 6;
    public static final int ERROR_SERVER = 7;
    public static final int AMIGO_NUEVO = 8;
    public static final int USUARIO_NO_IDENTIFICADO = 9;
    public static final int AMIGO_BORRADO = 10;
    public static final int BOTON_LOGIN_TEXT_PULSADO = 11;
    public static final int BOTON_LOGIN_FACEBOOK_PULSADO = 12;
    public static final int BOTON_LOGIN_EMAIL_PULSADO = 13;
    public static final int BOTON_REGISTER_TEXT_PULSADO = 14;
    public static final int USUARIO_IDENTIFICADO = 15;
    public static final int PARTIDA_CREADA = 16;
    public static final int DATOS_PROPIOS_ACTUALIZADOS = 17;
}