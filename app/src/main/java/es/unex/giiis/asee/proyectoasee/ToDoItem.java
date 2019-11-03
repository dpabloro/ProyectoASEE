package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class ToDoItem {
    public static final String ITEM_SEP= System.getProperty("line.separator");

    public enum Status{
        PENDING, DONE
    };

    public final static String TITLE="title";
    public final static String STATUS="status";
    public final static String DATE="date";
    public final static String FILENAME="filename";

    //nuestra zona horaria es como la de italia
    public final static SimpleDateFormat FORMAT= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY );

    private String fTitle = new String();
    private Status fStatus = Status.PENDING;
    private Date fDate= new Date();

    ToDoItem(String t, Status s, Date d){
        this.fTitle=t;
        this.fStatus=s;
        this.fDate=d;

    }


    ToDoItem(Intent intent){
        fTitle= intent.getStringExtra(ToDoItem.TITLE);
        fStatus=Status.valueOf(intent.getStringExtra(ToDoItem.STATUS));
        try {
            fDate= ToDoItem.FORMAT.parse(intent.getStringExtra(ToDoItem.DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    public Date getDate(){
        return fDate;
    }
    public void setDate(Date date){
        fDate=date;
    }



    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String title, Status status, String date) {

        intent.putExtra(ToDoItem.TITLE, title);
        intent.putExtra(ToDoItem.STATUS, status.toString());
        intent.putExtra(ToDoItem.DATE, date);

    }

    public String toString() {
        return fTitle + ITEM_SEP  + fStatus + ITEM_SEP
                + FORMAT.format(fDate);
    }

    public String toLog() {
        return "Title:" + fTitle + ITEM_SEP + "Status:" + fStatus + ITEM_SEP + "Date:"
                + FORMAT.format(fDate);
    }


}
