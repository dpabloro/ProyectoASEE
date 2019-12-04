package es.unex.giiis.asee.proyectoasee;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.view.Window;


public class SettingFragments extends PreferenceFragment {

    private Window window;

    public static final String KEY_PREF_USERNAME = "pref_username";
    public static final String KEY_PREF_ALIMENT = "pref_aliment";

    public static final String KEY_PREF_COLOR = "pref_color";

    private static final String TAG = "settingfragments-UserInterface";

    private EditTextPreference editTextPreference;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource

        addPreferencesFromResource(R.xml.preferences);


        editTextPreference= (EditTextPreference) findPreference("pref_color");


        Preference prefDefault=findPreference("temadefault");
        prefDefault.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String tema="Default";


                editTextPreference.setText(tema);
                editTextPreference.setSummary("The theme selected is Default");


                String primaryDark="#00574B";
                String primary="#008577";
                String background="#FFFFFF";
                cambiarColor(primaryDark,primary,background);

                return true;
            }
        });

        Preference pref=findPreference("tema1");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String tema="Blue";


                editTextPreference.setText(tema);
                editTextPreference.setSummary("The theme selected is Blue");


                String primaryDark="#8F99B1";
                String primary="#2C3D65";
                String background="#E4E9F3"; //pink pastel
                cambiarColor(primaryDark,primary,background);

                return true;
            }
        });

        Preference pref2=findPreference("tema2");
        pref2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                String tema="Brown";


                editTextPreference.setText(tema);
                editTextPreference.setSummary("The theme selected is Brown");


                String primaryDark="#CE69A2";
                String primary="#4C3A1B";
                String background="#FFFFFF"; //pink pastel
                cambiarColor(primaryDark,primary,background);

                return true;
            }
        });

        Preference pref3=findPreference("tema3");
        pref3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                String tema="Purple";


                editTextPreference.setText(tema);
                editTextPreference.setSummary("The theme selected is Purple");


                String primaryDark="#F0B5D4";
                String primary="#631446";
                String background="#FFF6C1";
                cambiarColor(primaryDark,primary,background);

                return true;
            }
        });
    }

    public void onResume(){
        super.onResume();
        String temaElegido=editTextPreference.getText();

        if(temaElegido.equals("Default")){
            editTextPreference.setSummary("The theme selected is Default");
        }
        if(temaElegido.equals("Blue")){
            editTextPreference.setSummary("The theme selected is Blue");
        }

        if(temaElegido.equals("Brown")){
            editTextPreference.setSummary("The theme selected is Brown");
        }

        if(temaElegido.equals("Purple")){
            editTextPreference.setSummary("The theme selected is Purple");
        }

    }


    public void cambiarColor(String primaryDark, String primary, String background){
        this.window= getActivity().getWindow();

        //cambiarPrimaryDark
        window.setStatusBarColor(Color.parseColor(primaryDark));
        //colorPrimary
        ((Settings)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        //bg
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        //boton navigation
        window.setNavigationBarColor(Color.parseColor(primary));


    }

}
