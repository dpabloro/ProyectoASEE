package es.unex.giiis.asee.proyectoasee;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Network {
    private int numAlimentos;
    private static final String TAG = "Network-UserInterface";

    private MutableLiveData<ArrayList<Aliments>> listaItems;

    public MutableLiveData<ArrayList<Aliments>> getListaItems() {

        return listaItems;
    }


    public Network(){
        ArrayList<Aliments> aliments = new ArrayList<>();
        listaItems = new MutableLiveData<ArrayList<Aliments>>();
        listaItems.setValue(aliments);
    }

    public void setNumAlimentos(int num) {
        this.numAlimentos = num;
    }

    public void getAlimentos(RequestQueue queue){
        String url = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray mJsonArray = response.getJSONArray("meals");

                    Log.i(TAG,"LA LISTA ONLINE TIENE "+ mJsonArray.length());

                    if( numAlimentos > mJsonArray.length() ) {
                        numAlimentos= mJsonArray.length();
                    }

                    for (int i = 0; i < numAlimentos; i++) {
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String name = mJsonObject.getString("strIngredient");
                        Aliments post = new Aliments(name);
                        Log.i("Prueba obtencion" , "Obtiene alimento "+ name);
                        listaItems.getValue().add(post);


                    }

                    Log.i(TAG,"LA LISTA TIENE "+listaItems.getValue().size());




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR CONEXION", error.toString());
                    }

                });

        queue.add(request);
    }

}
