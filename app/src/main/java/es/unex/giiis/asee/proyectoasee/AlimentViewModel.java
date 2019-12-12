package es.unex.giiis.asee.proyectoasee;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;


public class AlimentViewModel extends AndroidViewModel {

    private AlimentRepository mRepository;
    private MutableLiveData<ArrayList<Aliments>> mAllItems;


    public AlimentViewModel(Application application) {
        super(application);

        mRepository = new AlimentRepository();
        mAllItems = new MutableLiveData<ArrayList<Aliments>>();
    }

    MutableLiveData<ArrayList<Aliments>> getAliments(int numAlimentos, RequestQueue queue ) {
        mAllItems=mRepository.getAliments(numAlimentos,queue);
        return mAllItems ;
    }



}
