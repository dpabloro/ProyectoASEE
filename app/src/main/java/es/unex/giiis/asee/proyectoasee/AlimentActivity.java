package es.unex.giiis.asee.proyectoasee;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class AlimentActivity extends AppCompatActivity implements AlimentAdapter.OnListInteractionListener{

    // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;
    private static final String TAG = "AlimentActivity-UserInterface";


    private static final String FILE_NAME = "AlimentActivity.txt";

    private RequestQueue queue;


    private ArrayList<Posts> listaItems = new ArrayList<Posts>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private AlimentAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments);

        queue = Volley.newRequestQueue(this);

        getPosts();

        //Establecemos la toolbar
        Toolbar Mytoolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Mytoolbar);

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

                Intent data= new Intent(AlimentActivity.this, EditActivity.class);

                ArrayList<Posts> listSeleccionado= new ArrayList<Posts>();
                // Gather ToDoItem data
                // -  Title
                listSeleccionado=mAdapter.getSelected();



                // - Package ToDoItem data into an Intent

                Posts.packageIntent(data, listSeleccionado);


                startActivityForResult(data, RESULT_OK );

            }
        });



    }

    private void getPosts(){
        String url = "https://www.themealdb.com/api/json/v1/1/list.php?i=list";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray mJsonArray = response.getJSONArray("meals");

                    for (int i=0; i<mJsonArray.length();i++){
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String name = mJsonObject.getString("strIngredient");
                        Posts post=new Posts(name);
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
