package es.unex.giiis.asee.proyectoasee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


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
import java.util.List;
import java.util.Locale;


public class AlimentActivity extends AppCompatActivity implements AlimentAdapter.OnListInteractionListener{

    // Add a ToDoItem Request Code
    private static final int ALIMENT_OK = 0;
    private static final int REQ_CODE_SPEECH_INPUT=100;
    private ImageButton mBotonHablar;
    private static final String TAG = "AlimentActivity-UserInterface";


    private static final String FILE_NAME = "AlimentActivity.txt";

    private RequestQueue queue;

    private int numAlimentos;

    private ArrayList<Aliments> listaItems = new ArrayList<Aliments>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private AlimentAdapter mAdapter;
    private Window window;

    private AlimentRepository alimentRepository;

    EditText searchInput;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliments);

        alimentRepository = new AlimentRepository();

        queue = Volley.newRequestQueue(this);

        //getPosts();
        //listaItems.addAll(alimentRepository.getAliments(numAlimentos,queue));
        //log("TAMANOOO LISTA INICIAL "+listaItems.size());

        //Establecemos la toolbar
        Toolbar Mytoolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Mytoolbar);

        //Poner icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icono);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


        searchInput=(EditText) findViewById(R.id.search_inputAliments);


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //ShoppingItemCrud crud = ShoppingItemCrud.getInstance(MainActivity.this);
                //listaItems= crud.getAll();
                filtrar(listaItems,s.toString());
            }
        });

        mBotonHablar=findViewById(R.id.microfono);
        mBotonHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntradadeVoz();
            }
        });


        //cargarAdapter();
        String num;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        num =  sharedPref.getString(SettingFragments.KEY_PREF_ALIMENT, "");
        numAlimentos = Integer.parseInt(num);

        alimentRepository.getAliments(numAlimentos,queue).observe(this, new Observer<ArrayList<Aliments>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Aliments> items) {
                // Update the cached copy of the items in the adapter.
                Log.i("Prueba Observer", "Cambios notificados init");
                listaItems= (ArrayList<Aliments>) items;
                Log.i("Prueba Observer", "Cambios notificados end");
                cargarAdapter();

            }
        });

    }

    public void filtrar(ArrayList<Aliments> alimentsItems,String texto) {

        mAdapter.filtrar(alimentsItems,texto);
    }

    @Override
    public void onResume() {
        super.onResume();

        String num;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        num =  sharedPref.getString(SettingFragments.KEY_PREF_ALIMENT, "");
        numAlimentos = Integer.parseInt(num);


        String tema =sharedPref.getString(SettingFragments.KEY_PREF_COLOR, "");

        if(tema.equals("Default")){
            String primaryDark="#00574B";
            String primary="#008577";
            String background="#FFFFFF";
            cambiarColor(primaryDark,primary,background);
        }
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

    private void iniciarEntradadeVoz(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Dime el alimento que quieres buscar");
        try{
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if (resultCode==RESULT_OK && null!=data){

                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchInput.setText(result.get(0));
                }
                break;
            }
        }

    }


    private void cargarAdapter() {
        // Creamos un adapatador para el RecyclerView
        mAdapter=new AlimentAdapter(listaItems, this);
        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);
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
