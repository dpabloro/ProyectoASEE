package es.unex.giiis.asee.proyectoasee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;
    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    //private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Establecemos la toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton floatingAButton= (FloatingActionButton) findViewById(R.id.fab_margin);
        floatingAButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_TODO_ITEM_REQUEST );
            }

        });

        //Obtenemos la referencia del RecyclerView
        rRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);


        //Usa esta configuracion para mejorar el rendimiento si sabes
        //que el contenido no cambia el tamaño del layout del RecyclerView
        rRecyclerView.setHasFixedSize(true);

        //Usamos un linear layout manager
        rLayoutManager = new LinearLayoutManager(this);
        //Ponemos el linear layout manager al Recycler View
        rRecyclerView.setLayoutManager(rLayoutManager);



        // Creamos un adapatador para el RecyclerView
        //FALTA POR HACER
        //rRecyclerView.setAdapter();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //log("Entered onActivityResult()");

        if( (resultCode == RESULT_OK) & (requestCode == ADD_TODO_ITEM_REQUEST) ){
           ToDoItem toDoItem = new ToDoItem(data);
           //mAdapter.add(toDoItem); hay que crearlo
        }
    }






}
