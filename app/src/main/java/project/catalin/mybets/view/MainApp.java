package project.catalin.mybets.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import project.catalin.mybets.view.iniciarSesion.IniciarSesionPantallaPrincipal;

/**
 * Created by Trabajo on 13/04/2016.
 */
public class MainApp extends Application {


    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        startActivity(new Intent(context, IniciarSesionPantallaPrincipal.class).putExtras());
    }

    public static Context getAppContext() {
        return context;
    }


}
