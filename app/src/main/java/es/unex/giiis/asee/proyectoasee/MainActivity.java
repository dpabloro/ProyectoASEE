package es.unex.giiis.asee.proyectoasee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;

    private static final String FILE_NAME = "TodoManagerActivityData.txt";
    private static final String TAG = "MainActivity-UserInterface";

    // IDs for menu items
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_SELECTED = Menu.FIRST+1;

    //Lista de lista de la compra
    private final List<ShoppingItem> listaItems = new ArrayList<ShoppingItem>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private ShoppingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Establecemos la toolbar
        Toolbar Mytoolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Mytoolbar);

        FloatingActionButton floatingAddButton= (FloatingActionButton) findViewById(R.id.add_button);
        floatingAddButton.setOnClickListener(new View.OnClickListener(){
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
         mAdapter = new ShoppingAdapter(new ShoppingAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ShoppingItem item){
                Snackbar.make(MainActivity.this.getCurrentFocus(),"Item" + item.getTitle()+" clicked", Snackbar.LENGTH_LONG);
            }
        });

        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);




    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //log("Entered onActivityResult()");

        if( (resultCode == RESULT_OK) & (requestCode == ADD_TODO_ITEM_REQUEST) ){
           ToDoItem toDoItem = new ToDoItem(data);
           //mAdapter.add(toDoItem); hay que crearlo
        }
    }
     */

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        //if (mAdapter.getItemCount() == 0)
         //  loadItems();
    }


    @Override
    protected void onPause(){
        super.onPause();

        //saveItems();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
        menu.add(Menu.NONE, MENU_SELECTED, Menu.NONE, "Delete selected");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                mAdapter.clear();
                return true;
            case MENU_SELECTED:
                deleteSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


   private void deleteSelected(){

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
