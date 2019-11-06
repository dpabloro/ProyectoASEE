package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class ShoppingItem {
    public static final String ITEM_SEP= System.getProperty("line.separator");

    public enum Status{
        PENDING, DONE
    };



    public final static String TITLE="title";
    public final static String STATUS="status";
    public final static String DATE="date";
    //public final static String HOUR="hour";

    public final static String FILENAME="filename";

    //nuestra zona horaria es como la de italia
    public final static SimpleDateFormat FORMAT= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY );


    private String fTitle = new String();
    private Status fStatus = Status.PENDING;
    private String fDate= new String();
   // private String fHour= new String();

    ShoppingItem(String t, Status s, String d){
        this.fTitle=t;
        this.fStatus=s;
        this.fDate=d;
        //this.fHour=h;


    }


    ShoppingItem(Intent intent){
        fTitle= intent.getStringExtra(ShoppingItem.TITLE);
        fStatus=Status.valueOf(intent.getStringExtra(ShoppingItem.STATUS));
        fDate= intent.getStringExtra(ShoppingItem.DATE);



    }

    public String getTitle(){

        return fTitle;
    }
    public void setTitle(String title){
        fTitle=title;
    }
    public Status getStatus(){

        return fStatus;
    }
    public void setStatus(Status status){

        fStatus=status;
    }

    public String getDate(){

        return fDate;
    }
    public void setDate(String date){

        fDate=date;
    }



    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String title, Status status, String date) {

        intent.putExtra(ShoppingItem.TITLE, title);
        intent.putExtra(ShoppingItem.STATUS, status.toString());
        intent.putExtra(ShoppingItem.DATE, date);


    }

    public String toString() {
        return fTitle + ITEM_SEP  + fStatus + ITEM_SEP
                + fDate;
    }

    public String toLog() {
        return "Title:" + fTitle + ITEM_SEP + "Status:" + fStatus + ITEM_SEP + "Date:"
                + fDate;
    }


}
