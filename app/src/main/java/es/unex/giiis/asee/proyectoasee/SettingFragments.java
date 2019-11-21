package es.unex.giiis.asee.proyectoasee;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingFragments extends PreferenceFragment {

    public static final String KEY_PREF_USERNAME = "pref_username";
    public static final String KEY_PREF_ALIMENT = "pref_aliment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource

        addPreferencesFromResource(R.xml.preferences);

    }

}
