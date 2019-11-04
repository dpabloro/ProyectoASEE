package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class ToDoItem {
    public static final String ITEM_SEP= System.getProperty("line.separator");

    public enum Status{
        PENDING, DONE
    };

    public enum Priority{
        LOW, MED, HIGH
    }

    public final static String TITLE="title";
    public final static String STATUS="status";
    public final static String DATE="date";
    public final static  String PRIORITY="priority";
    public final static String FILENAME="filename";

    //nuestra zona horaria es como la de italia
    public final static SimpleDateFormat FORMAT= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY );

    private String fTitle = new String();
    private Status fStatus = Status.PENDING;
    private Date fDate= new Date();
    private Priority fPriority= Priority.MED;

    ToDoItem(String t, Status s, Date d, Priority p){
        this.fTitle=t;
        this.fStatus=s;
        this.fDate=d;
        this.fPriority=p;

    }


    ToDoItem(Intent intent){
        fTitle= intent.getStringExtra(ToDoItem.TITLE);
        fStatus=Status.valueOf(intent.getStringExtra(ToDoItem.STATUS));
        try {
            fDate= ToDoItem.FORMAT.parse(intent.getStringExtra(ToDoItem.DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fPriority=Priority.valueOf(intent.getStringExtra(ToDoItem.PRIORITY));

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

    public void setPriority(Priority priority){
        fPriority=priority;
    }

    public Priority getPriority(){
        return fPriority;
    }


    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String title, Status status, String date, Priority priority) {

        intent.putExtra(ToDoItem.TITLE, title);
        intent.putExtra(ToDoItem.STATUS, status.toString());
        intent.putExtra(ToDoItem.DATE, date);
        intent.putExtra(ToDoItem.PRIORITY, priority.toString());

    }

    public String toString() {
        return fTitle + ITEM_SEP + fPriority + ITEM_SEP + fStatus + ITEM_SEP
                + FORMAT.format(fDate);
    }

    public String toLog() {
        return "Title:" + fTitle + ITEM_SEP +"Priority:"+fPriority+ITEM_SEP+ "Status:" + fStatus + ITEM_SEP + "Date:"
                + FORMAT.format(fDate);
    }


}
