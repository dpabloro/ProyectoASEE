package es.unex.giiis.asee.proyectoasee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyectoasee.Interface.JsonPlaceHolderApi;
import es.unex.giiis.asee.proyectoasee.Model.Posts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AlimentActivity extends AppCompatActivity {

    // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;

    private static final String FILE_NAME = "AlimentActivity.txt";
    private static final String TAG = "AlimentActivity-UserInterface";



    //Lista de lista de la compra
    private final List<AlimentItem> listaItems = new ArrayList<AlimentItem>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private AlimentAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments);

        //Establecemos la toolbar
        Toolbar Mytoolbar= (Toolbar) findViewById(R.id.toolbarAliments);
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



        // Creamos un adapatador para el RecyclerView
        mAdapter = new AlimentAdapter(new AlimentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AlimentItem item) {
                Snackbar.make(AlimentActivity.this.getCurrentFocus(),"Item" + item.getTitle()+" clicked", Snackbar.LENGTH_LONG);

            }
        });

        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);

        getPosts();
    }

    public void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Posts>> call= jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if(! response.isSuccessful()){
                    log("Codigo" + response.code());
                    return;
                }

                List<Posts> postsList= response.body();
                mAdapter.swap(postsList);
                for (Posts post: postsList){

                    String name= post.getStrIngredient();
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                log( t.getMessage());
            }
        });

        }



    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0) {
            try {
                loadItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onPause(){
        super.onPause();

        try {
            saveItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("Entered onActivityResult()");

        if ((resultCode == RESULT_OK) & (requestCode == ADD_TODO_ITEM_REQUEST)) {
            AlimentItem aItem = new AlimentItem(data);
            mAdapter.add(aItem);
        }
    }


    public void loadItems() throws IOException {
        BufferedReader reader=null;
        FileInputStream fileInputStream= openFileInput(FILE_NAME);
        reader = new BufferedReader(new InputStreamReader(fileInputStream));

        String title=null;
        String status= null;

        while ((title = reader.readLine()) != null){
            status= reader.readLine();
            mAdapter.add(new AlimentItem(title, AlimentItem.Status.valueOf(status)));
        }

        if (reader != null){
            reader.close();
        }

    }



    public void saveItems() throws IOException{
        PrintWriter printWrite= null;

        FileOutputStream fileOutputStream= openFileOutput(FILE_NAME,MODE_PRIVATE);
        printWrite= new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));

        for(int i=0; i< mAdapter.getItemCount(); i++ ){
            printWrite.println(mAdapter.getItem(i));
        }

        printWrite.close();
    }



    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}
