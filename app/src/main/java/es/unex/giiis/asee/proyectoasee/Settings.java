package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Settings extends AppCompatActivity{
    private static final String TAG = "Settings-UserInterface";

    private Window window;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_settingactivity);

        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        //Poner icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icono);

    }

    public void onResume(){
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String tema =sharedPref.getString(SettingFragments.KEY_PREF_COLOR, "");


        if(tema.equals("Blue")){
            String primaryDark="#8F99B1";
            String primary="#2C3D65";
            String background="#E4E9F3"; //pink pastel
            cambiarColor(primaryDark,primary,background);
        }

        if(tema.equals("Brown")){
            String primaryDark="#CE69A2";
            String primary="#4C3A1B";
            String background="#FFFFFF"; //pink pastel
            cambiarColor(primaryDark,primary,background);
        }

        if(tema.equals("Purple")){
            String primaryDark="#F0B5D4";
            String primary="#631446";
            String background="#FFF6C1";
            cambiarColor(primaryDark,primary,background);
        }

    }


    public void cambiarColor(String primaryDark, String primary, String background){
        this.window= getWindow();

        //cambiarPrimaryDark
        window.setStatusBarColor(Color.parseColor(primaryDark));
        //colorPrimary
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        //bg
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        //boton navigation
        window.setNavigationBarColor(Color.parseColor(primary));


    }

    private static void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}