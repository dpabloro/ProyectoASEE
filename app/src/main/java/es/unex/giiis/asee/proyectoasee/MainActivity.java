package es.unex.giiis.asee.proyectoasee;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;



import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

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
    private static final int MENU_COLOR = Menu.FIRST+2;

    //Lista de lista de la compra
    private ArrayList<ShoppingItem> listaItems = new ArrayList<ShoppingItem>();

    private RecyclerView rRecyclerView; //(lista de las listas/elementos que tenemos en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private ShoppingAdapter mAdapter;
    private Window window;



    private static TextView dateView;



    private RadioGroup mStatusRadioGroup;
    private EditText mTitleText;
    private TextView nameView;
    private LinearLayout fondo;
    private Typeface font;

    EditText searchInput;
    private boolean cargar = false;


    private ShoppingViewModel mShoppingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Establecemos la toolbar
        Toolbar Mytoolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Mytoolbar);

        //Poner icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icono);

        FloatingActionButton floatingAddButton= (FloatingActionButton) findViewById(R.id.add_button);
        floatingAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_TODO_ITEM_REQUEST );
            }

        });


        nameView = (TextView) findViewById(R.id.name_label);
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


        mShoppingViewModel = ViewModelProviders.of(this).get(ShoppingViewModel.class);
        //se añade el observador
        mShoppingViewModel.getAllItems().observe(this, new Observer<List<ShoppingItem>>() {
            @Override
            public void onChanged(@Nullable final List<ShoppingItem> items) {
                // Update the cached copy of the items in the adapter.
                Log.i("Prueba ", "Cambios notificados init");
                listaItems= (ArrayList<ShoppingItem>) items;
                mAdapter.setShoppingItems(listaItems);
                Log.i("Prueba ", "Cambios notificados end");
            }
        });





        // Creamos un adapatador para el RecyclerView
         mAdapter = new ShoppingAdapter(new ShoppingAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ShoppingItem item){
                Intent intent= new Intent(MainActivity.this, EditActivity.class);

                long id= item.getId();

                String title= item.getTitle();

                //-  Get Status
                ShoppingItem.Status status = item.getStatus();

                String dateString= item.getDate();
                ArrayList<Aliments> listaAlimentos=item.getAlimentos();



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
                //ShoppingItemCrud crud = ShoppingItemCrud.getInstance(MainActivity.this);
                //listaItems= crud.getAll();
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
        String username="Welcome, ";
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        username +=  sharedPref.getString(SettingFragments.KEY_PREF_USERNAME, "");

        String fuente= "fuentes/fontname.ttf";
        this.font = Typeface.createFromAsset(getAssets(),fuente);
        nameView.setTypeface(font);
        nameView.setText(username);

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

        // Load saved ShoppinItems, if necessary
        if (mAdapter.getItemCount() == 0 || cargar) {

                cargar=false;
               // loadItems();

        }
    }


    @Override
    protected void onPause(){
        super.onPause();


            //saveItems();

    }

    @Override
    protected void onDestroy() {
        //ShoppingItemCrud crud=ShoppingItemCrud.getInstance(this);
        //crud.close();
        super.onDestroy();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("Entered onActivityResult()");

        if ((resultCode == RESULT_OK) & (requestCode == ADD_TODO_ITEM_REQUEST)) {
            ShoppingItem sItem = new ShoppingItem(data);
            //mAdapter.add(sItem);
            //ShoppingItemCrud crud = ShoppingItemCrud.getInstance(this);
            //long id = crud.insert(sItem);
            //sItem.setID(id);
            mShoppingViewModel.insert(sItem);


        }
        else{
            if((resultCode== RESULT_EDIT) && (requestCode == EDIT_SHOPPING_ITEM_REQUEST)){
                ShoppingItem sItem = new ShoppingItem(data);
                sItem.setId(data.getLongExtra(ShoppingItem.ID, 0));


               // ShoppingItemCrud crud = ShoppingItemCrud.getInstance(this);
               // crud.updateStatus(sItem.getID(), sItem.getTitle(), sItem.getStatus(), sItem.getDate());
                //Editando los alimentos
               // crud.updateStatus(sItem.getID(), sItem.getTitle(), sItem.getStatus(), sItem.getDate(), sItem.getSAlimentos());
                //mAdapter.notifyDataSetChanged();
                mShoppingViewModel.update(sItem);
                cargar=true;

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);


        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
       // menu.add(Menu.NONE, MENU_SELECTED, Menu.NONE, "Delete selected");
        menu.add(Menu.NONE, MENU_PREFERENCES, Menu.NONE, "Preferences");
       // menu.add(Menu.NONE, MENU_COLOR, Menu.NONE, "Change the color in FastCart");
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
        //ShoppingItemCrud shoppingItemCrud = ShoppingItemCrud.getInstance(this);
       // shoppingItemCrud.deleteAll();
        //mAdapter.clear();
       mShoppingViewModel.deleteAll();
   }


   public void openSettings(){
        Intent newActivity = new Intent(this, Settings.class);
        startActivity(newActivity);
   }



   /*
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

   /*     ShoppingItemCrud crud = ShoppingItemCrud.getInstance(this);
        ArrayList<ShoppingItem> shoppingItems = crud.getAll();
        mAdapter.load(shoppingItems);


   }
*/



    /*public void saveItems() throws IOException{
        PrintWriter printWrite= null;

        FileOutputStream fileOutputStream= openFileOutput(FILE_NAME,MODE_PRIVATE);
        printWrite= new PrintWriter(new BufferedWriter(new OutputStreamWriter(fileOutputStream)));

        for(int i=0; i< mAdapter.getItemCount(); i++ ){
            printWrite.println(mAdapter.getItem(i));
        }

        printWrite.close();
    }*/



    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }


}
