package es.unex.giiis.asee.proyectoasee;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class AlimentActivity extends AppCompatActivity implements AlimentAdapter.OnListInteractionListener{

    // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;

    private static final String FILE_NAME = "AlimentActivity.txt";
    private static final String TAG = "AlimentActivity-UserInterface";





    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private AlimentAdapter mAdapter;

    public interface JsonPlaceHolderApi {
        @GET("1/{consulta}")
        Call<SchemaPosts> getPosts(@Path("consulta" ) String consulta, @Query("i") String i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments);

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


        List<Posts> myDataSet=new ArrayList<Posts>();
        // Creamos un adapatador para el RecyclerView
        mAdapter=new AlimentAdapter(myDataSet, this);

        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);

        getPosts();
    }

    public void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("FastCart","titleIngredient: DIMEEEE ALGO PRIMER ");
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<SchemaPosts> call= jsonPlaceHolderApi.getPosts("list.php", "list");

        Log.i("FastCart","titleIngredient: DIMEEEE ALGO  1");
        call.enqueue(new Callback<SchemaPosts>() {
            @Override
            public void onResponse(Call<SchemaPosts> call, Response<SchemaPosts> response) {

                Log.i("FastCartIF","titleIngredient: antes de IF");
                if(response.isSuccessful()){
                    Log.i("FastCart","titleIngredient: DIMEEEE ALGO2  ");

                    List<Posts> postsL= response.body().getListPosts();
                    Log.i("FastCart","titleIngredient: DIMEEEE ALGO 3 ");
                    mAdapter.swap(postsL);
                    for (Posts post: postsL){
                        String name= post.getStrIngredient();
                        Log.i("FastCart","titleIngredient: "+name);

                    }
                } else{
                    Log.i("FastCart","NO RESPONSE");
                }

            }

            @Override
            public void onFailure(Call<SchemaPosts> call, Throwable t) {
                Log.d("FastCart",t.getMessage());
            }
        });

        }

    @Override
    public void onListInteraction(String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}
