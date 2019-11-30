package es.unex.giiis.asee.proyectoasee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;


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




public class AlimentActivity extends AppCompatActivity implements AlimentAdapter.OnListInteractionListener{

    // Add a ToDoItem Request Code
    private static final int ALIMENT_OK = 0;
    private static final String TAG = "AlimentActivity-UserInterface";


    private static final String FILE_NAME = "AlimentActivity.txt";

    private RequestQueue queue;

    private int numAlimentos;

    private ArrayList<Aliments> listaItems = new ArrayList<Aliments>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private AlimentAdapter mAdapter;
    private Window window;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments);

        queue = Volley.newRequestQueue(this);

        getPosts();

        //Establecemos la toolbar
        Toolbar Mytoolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Mytoolbar);

        //Poner icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icono);

        //Obtenemos la referencia del RecyclerView
        rRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_viewAliment);


        //Usa esta configuracion para mejorar el rendimiento si sabes
        //que el contenido no cambia el tamaño del layout del RecyclerView
        rRecyclerView.setHasFixedSize(true);

        //Usamos un linear layout manager
        rLayoutManager = new LinearLayoutManager(this);
        //Ponemos el linear layout manager al Recycler View
        rRecyclerView.setLayoutManager(rLayoutManager);


        final Button submitButton = (Button) findViewById(R.id.saveAlimentButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                Intent data= new Intent();

                ArrayList<Aliments> listSeleccionado= new ArrayList<Aliments>();
                // Gather ToDoItem data
                // -  Title
                listSeleccionado=mAdapter.getSelected();




                data.putParcelableArrayListExtra(ShoppingItem.ALIMENTOS,listSeleccionado);

                ShoppingItem.packageIntent(data, listSeleccionado);

                // - return data Intent and finish
                setResult(RESULT_OK,data);
                finish();

            }
        });



    }



    @Override
    public void onResume() {
        super.onResume();

        String num;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        num =  sharedPref.getString(SettingFragments.KEY_PREF_ALIMENT, "");
        numAlimentos = Integer.parseInt(num);


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

    private void getPosts(){
        String url = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray mJsonArray = response.getJSONArray("meals");
                    if( numAlimentos > mJsonArray.length() ) {
                        numAlimentos= mJsonArray.length();
                    }
                        for (int i = 0; i < numAlimentos; i++) {
                            JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                            String name = mJsonObject.getString("strIngredient");
                            Aliments post = new Aliments(name);
                            listaItems.add(post);

                        }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cargarAdapter();
            }

        },


                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });

        queue.add(request);
    }

    private void cargarAdapter() {

        // Creamos un adapatador para el RecyclerView
        mAdapter=new AlimentAdapter(listaItems, this);
        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);

        log("TAMANOOO LISTA" + listaItems.size());

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

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    @Override
    public void onListInteraction(String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }










}
