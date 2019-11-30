package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.Preference;
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
        this.window= getWindow();


        setContentView(R.layout.layout_settingactivity);
        Log.i(TAG, "pref");
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        Log.i(TAG, "PRIMER BUTTON");


        setContentView(R.layout.preference_buttons);
        final Button button1 = (Button) findViewById(R.id.buttonC1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered Button.OnClickListener.onClick()");

                // - Implement onClick().
                String primaryDark="##00CCFF";
                String primary="#66CCFF";
                String background="##99CCFF"; //azul pastel
                cambiarColor(primaryDark,primary,background);


            }
        });
/*

        final Button button2 = (Button) findViewById(R.id.buttonC2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered cancelButton.OnClickListener.onClick()");

                // - Implement onClick().
                String primaryDark="#FF69B4";
                String primary="#FFB6C1";
                String background="#FFC0CB"; //pink pastel
                cambiarColor(primaryDark,primary,background);


            }
        });


        final Button button3 = (Button) findViewById(R.id.buttonC3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered cancelButton.OnClickListener.onClick()");

                // - Implement onClick().
                String primaryDark="#32CD32";
                String primary="#98FB98";
                String background="#90EE90"; //verde
                cambiarColor(primaryDark,primary,background);


            }
        });
        */

    }


    public void cambiarColor(String primaryDark, String primary, String background){
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