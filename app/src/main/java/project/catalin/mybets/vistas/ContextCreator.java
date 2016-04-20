package project.catalin.mybets.vistas;

import android.app.Application;
import android.content.Context;

/**
 * Created by Trabajo on 13/04/2016.
 */
public class ContextCreator extends Application {


    private static Context context;


    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }


}
