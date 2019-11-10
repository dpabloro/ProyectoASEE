package es.unex.giiis.asee.proyectoasee;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import es.unex.giiis.asee.proyectoasee.ShoppingItem.Status;

import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {



    private static final String TAG = "Lab-UserInterface";

    private static String dateString;
    private static TextView dateView;



    private RadioGroup mStatusRadioGroup;
    private EditText mTitleText;
    private RadioButton mDefaultStatusButton;

    private static final int ADD_ALIMENT_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitleText = (EditText) findViewById(R.id.titleIntroducirId);
        mDefaultStatusButton = (RadioButton) findViewById(R.id.statusNotDone);


        mStatusRadioGroup = (RadioGroup) findViewById(R.id.statusGroup);
        dateView = (TextView) findViewById(R.id.date);



        // Set the default date
        setDefaultDate();

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

                //TODO - Implement onClick().
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
                Log.i("Delia","LLEGA AQUÍ2");

                mStatusRadioGroup.check(mDefaultStatusButton.getId());
                setDefaultDate();

            }
        });

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                // Gather ToDoItem data
                Log.i("Estela","LLEGA AQUÍ eee");
                // -  Title
                String title= mTitleText.getText().toString();

                //-  Get Status
                Status status = getStatus();


                // - Package ToDoItem data into an Intent
                Intent data = new Intent();
                dateString=dateView.getText().toString();
                ShoppingItem.packageIntent(data,title,status,dateString);

                // - return data Intent and finish
                setResult(RESULT_OK,data);
                finish();

            }
        });

        final Button AlimentsButton = (Button) findViewById(R.id.selectAliment);
        AlimentsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(AddActivity.this, AlimentActivity.class);
                startActivityForResult(intent, ADD_ALIMENT_REQUEST);
            }

        });

    }


    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");

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

