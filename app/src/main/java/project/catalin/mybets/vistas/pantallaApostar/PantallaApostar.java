package project.catalin.mybets.vistas.pantallaApostar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.catalin.mybets.R;

public class PantallaApostar extends AppCompatActivity {

    public static final String TAG_TIPO_PARTIDA = "tipopartida";
    public static final String TAG_ID_PARTIDA = "idpartida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_apostar);
    }
}
