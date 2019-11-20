package es.unex.giiis.asee.proyectoasee;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;



import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyectoasee.database.ShoppingItemCrud;

public class MainActivity extends AppCompatActivity {
    // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;
    private static final int EDIT_SHOPPING_ITEM_REQUEST = 1;
    private static final int RESULT_EDIT = 2;

    private static final String FILE_NAME = "MainActivity.txt";
    private static final String TAG = "MainActivity-UserInterface";

    // IDs for menu items
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_SELECTED = Menu.FIRST+1;
    private static final int MENU_PREFERENCES = Menu.FIRST+1;

    //Lista de lista de la compra
    private ArrayList<ShoppingItem> listaItems = new ArrayList<ShoppingItem>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private ShoppingAdapter mAdapter;



    private static TextView dateView;



    private RadioGroup mStatusRadioGroup;
    private EditText mTitleText;

    EditText searchInput;

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


        //iniciar las preferencias
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Creamos un adapatador para el RecyclerView
         mAdapter = new ShoppingAdapter(new ShoppingAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ShoppingItem item){
                Intent intent= new Intent(MainActivity.this, EditActivity.class);

                long id= item.getID();

                String title= item.getTitle();

                //-  Get Status
                ShoppingItem.Status status = item.getStatus();

                String dateString= item.getDate();
                ArrayList<Posts> listaAlimentos=item.getfAlimentos();



                log("DA CLICK EN LISTA");
                ShoppingItem.packageIntent(intent, title,status,dateString,listaAlimentos);
                intent.putExtra(ShoppingItem.ID, id);



                startActivityForResult(intent, EDIT_SHOPPING_ITEM_REQUEST );

                log("DA CLICK EN LISTA 2");

            }
        });

        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);



        listaItems=mAdapter.getItems();
        searchInput=findViewById(R.id.search_input);



        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(listaItems,s.toString());
            }
        });



    }

    public void filtrar(ArrayList<ShoppingItem> shoppingItems,String texto) {

        mAdapter.filtrar(shoppingItems,texto);
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
    protected void onDestroy() {
        ShoppingItemCrud crud=ShoppingItemCrud.getInstance(this);
        crud.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("Entered onActivityResult()");

        if ((resultCode == RESULT_OK) & (requestCode == ADD_TODO_ITEM_REQUEST)) {
            ShoppingItem sItem = new ShoppingItem(data);
            mAdapter.add(sItem);
            ShoppingItemCrud crud = ShoppingItemCrud.getInstance(this);
            long id = crud.insert(sItem);
            sItem.setID(id);


        }
        else{
            if((resultCode== RESULT_EDIT) && (requestCode == EDIT_SHOPPING_ITEM_REQUEST)){
                ShoppingItem sItem = new ShoppingItem(data);
                sItem.setID(data.getLongExtra(ShoppingItem.ID, 0));


                ShoppingItemCrud crud = ShoppingItemCrud.getInstance(this);
               // crud.updateStatus(sItem.getID(), sItem.getTitle(), sItem.getStatus(), sItem.getDate());
                //Editando los alimentos
                crud.updateStatus(sItem.getID(), sItem.getTitle(), sItem.getStatus(), sItem.getDate(), sItem.getSAlimentos());
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);


        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
       // menu.add(Menu.NONE, MENU_SELECTED, Menu.NONE, "Delete selected");
        menu.add(Menu.NONE, MENU_PREFERENCES, Menu.NONE, "Preferences");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                deleteAll();
                return true;
            case MENU_PREFERENCES:
                openSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


   private void deleteAll(){
        ShoppingItemCrud shoppingItemCrud = ShoppingItemCrud.getInstance(this);
        shoppingItemCrud.deleteAll();
        mAdapter.clear();
   }


   public void openSettings(){
        Intent newActivity = new Intent(this, Settings.class);
        startActivity(newActivity);
   }


   public void loadItems() throws IOException {
 /*      BufferedReader reader=null;
       FileInputStream fileInputStream= openFileInput(FILE_NAME);
       reader = new BufferedReader(new InputStreamReader(fileInputStream));

       String title=null;
       String status= null;
       String date= null;

       while ((title = reader.readLine()) != null){
            status= reader.readLine();
            date= reader.readLine();
            mAdapter.add(new ShoppingItem(title, ShoppingItem.Status.valueOf(status), ShoppingItem.DATE.valueOf(date)));
       }

        if (reader != null){
            reader.close();
        }
        */

        ShoppingItemCrud crud = ShoppingItemCrud.getInstance(this);
        ArrayList<ShoppingItem> shoppingItems = crud.getAll();
        mAdapter.load(shoppingItems);


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
