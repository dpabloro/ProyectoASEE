package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;

public class AlimentItem {
    public static final String ITEM_SEP= System.getProperty("line.separator");

    public enum Status{
        SELECTED, NOTSELECTED
    };

    public final static String TITLE="title";
    public final static String STATUS="status";


    public final static String FILENAME="filename";


    private String fTitle = new String();
    private Status fStatus = Status.NOTSELECTED;



    AlimentItem(String t, Status s){
        this.fTitle=t;
        this.fStatus=s;


    }


    AlimentItem(Intent intent){
        fTitle= intent.getStringExtra(AlimentItem.TITLE);
        fStatus=Status.valueOf(intent.getStringExtra(AlimentItem.STATUS));
    }

    public Status getStatus(){

        return fStatus;
    }
    public void setStatus(Status status){

        fStatus=status;
    }

    public String getTitle(){

        return fTitle;
    }
    public void setTitle(String title){
        fTitle=title;
    }




    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String title,Status status) {

        intent.putExtra(AlimentItem.TITLE, title);
        intent.putExtra(ShoppingItem.STATUS, status.toString());


    }

    public String toString() {
        return fTitle + ITEM_SEP  + fStatus;
    }


    public String toLog() {
        return "Title:" + fTitle + ITEM_SEP;
    }


}
