package es.unex.giiis.asee.proyectoasee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import es.unex.giiis.asee.proyectoasee.ShoppingItem.Status;

public class EditActivity extends AppCompatActivity  implements AlimentAdapter.OnListInteractionListener {



    private static final String TAG = "Lab-UserInterface";

    private static String dateString;
    private static TextView dateView;



    private RadioGroup mStatusRadioGroup;
    private EditText mTitleText;
    private RadioButton mDefaultStatusButton;

    private static final int RESULT_EDIT_OK = 2;
    private RecyclerView rRecyclerView; //(lista de las listas/elementos seleccionados en la aplicacion)
    private RecyclerView.LayoutManager rLayoutManager;
    private AlimentAdapter mAdapter;

    private Window window;


    private ArrayList<Aliments> listPost=new ArrayList<Aliments>();

    Bundle datos;
    String datosobtenidos;
    String fechaobtenida;
    String estadoobtenido;
    Long idobtenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Poner icono
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icono);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Obtenemos la referencia del RecyclerView
        rRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_viewSelected);


        //Usa esta configuracion para mejorar el rendimiento si sabes
        //que el contenido no cambia el tamaño del layout del RecyclerView
        rRecyclerView.setHasFixedSize(true);

        //Usamos un linear layout manager
        rLayoutManager = new LinearLayoutManager(this);
        //Ponemos el linear layout manager al Recycler View
        rRecyclerView.setLayoutManager(rLayoutManager);



        Intent intent= getIntent();
       listPost = (ArrayList<Aliments>) intent.getSerializableExtra("alimentos");
        //ArrayList<Aliments> listPost=new ArrayList<Aliments>();
      // Aliments postsPrueba=new Aliments("Pollo");

      // listPost.add(postsPrueba);
        // Creamos un adapatador para el RecyclerView
        mAdapter=new AlimentAdapter(listPost, this);
        // Attach the adapter to the RecyclerView
        rRecyclerView.setAdapter(mAdapter);

        datos= getIntent().getExtras();
        idobtenido= datos.getLong("id");
        datosobtenidos= datos.getString("title");
        mTitleText = (EditText) findViewById(R.id.titleIntroducirId);
        mTitleText.setText(datosobtenidos);

        fechaobtenida=datos.getString("date");
        dateView = (TextView) findViewById(R.id.date);
        dateView.setText(fechaobtenida);

        estadoobtenido=datos.getString("status".toString());
        log("el estado de la lista es "+estadoobtenido);


        mStatusRadioGroup = (RadioGroup) findViewById(R.id.statusGroup);

        //String radiosobtenidos= datos.getString("status");
        if (estadoobtenido.equals("DONE")) {
            mDefaultStatusButton = (RadioButton) findViewById(R.id.statusDone);
            mStatusRadioGroup.check(mDefaultStatusButton.getId());
        }
        else {
            mDefaultStatusButton = (RadioButton) findViewById(R.id.statusNotDone);
            mStatusRadioGroup.check(mDefaultStatusButton.getId());

        }





        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog

        final Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        // OnClickListener for the Cancel Button,
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered cancelButton.OnClickListener.onClick()");

                //Implement onClick().
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();


            }
        });

        //OnClickListener for the Reset Button

        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered resetButton.OnClickListener.onClick()");

                // - Reset data fields to default values
                mTitleText.setText("");

                mDefaultStatusButton = (RadioButton) findViewById(R.id.statusNotDone);

                mStatusRadioGroup.check(mDefaultStatusButton.getId());
                setDefaultDate();

                listPost.clear();
                mAdapter=new AlimentAdapter(listPost, EditActivity.this);
                // Attach the adapter to the RecyclerView
                rRecyclerView.setAdapter(mAdapter);

            }
        });

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                // Gather ToDoItem data
                // -  Title
                String title= mTitleText.getText().toString();
                Intent data= getIntent();


                //-  Get Status
                Status status = getStatus();



                // - Package ToDoItem data into an Intent
                dateString= (String) dateView.getText();
                ShoppingItem.packageIntent(data,title,status,dateString,listPost);
                data.putExtra(ShoppingItem.ID, idobtenido);

                // - return data Intent and finish
                setResult(RESULT_EDIT_OK,data);
                Log.i("EDIT", " SHOPPING"  );
                finish();

            }
        });



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
    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");

    }

    @Override
    public void onListInteraction(String url) {

    }


    public void onResume(){
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
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


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            dateView.setText(dateString);
        }

    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;

        dateString = year + "-" + monthOfYear + "-" + dayOfMonth;

    }

    private void setDefaultDate() {
        // Default is current time + 7 days

        Calendar c = Calendar.getInstance();

        //Fecha

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateView.setText(dateString);


    }

    private Status getStatus() {

        switch (mStatusRadioGroup.getCheckedRadioButtonId()) {
            case R.id.statusDone: {
                return Status.DONE;
            }
            default: {
                return Status.PENDING;
            }
        }
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

