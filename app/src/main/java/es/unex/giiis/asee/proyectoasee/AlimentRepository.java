package es.unex.giiis.asee.proyectoasee;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class AlimentRepository {


    AlimentRepository() {

    }

    public MutableLiveData<ArrayList<Aliments>> getAliments(int numAlimentos, RequestQueue queue) {

        Network network = new Network();
        network.setNumAlimentos(numAlimentos);
        network.getAlimentos(queue);

        return network.getListaItems();
    }

}





