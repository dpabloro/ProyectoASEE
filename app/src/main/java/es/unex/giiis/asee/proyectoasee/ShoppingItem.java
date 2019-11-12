package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

public class ShoppingItem implements Parcelable {
    public static final String ITEM_SEP= System.getProperty("line.separator");

    protected ShoppingItem(Parcel in) {
        fTitle = in.readString();
        fDate = in.readString();
        fAlimentos = in.createTypedArrayList(Posts.CREATOR);
    }

    public static final Creator<ShoppingItem> CREATOR = new Creator<ShoppingItem>() {
        @Override
        public ShoppingItem createFromParcel(Parcel in) {
            return new ShoppingItem(in);
        }

        @Override
        public ShoppingItem[] newArray(int size) {
            return new ShoppingItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fTitle);
        dest.writeString(fDate);
        dest.writeTypedList(fAlimentos);
    }

    public enum Status{
        PENDING, DONE
    };



    public final static String TITLE="title";
    public final static String STATUS="status";
    public final static String DATE="date";
    public final static String ALIMENTOS="alimentos";


    public final static String FILENAME="filename";


    private String fTitle = new String();
    private Status fStatus = Status.PENDING;
    private String fDate= new String();
    private ArrayList<Posts> fAlimentos=new ArrayList<Posts>();

    ShoppingItem(String t, Status s, String d){
        this.fTitle=t;
        this.fStatus=s;
        this.fDate=d;
        this.fAlimentos=new ArrayList<Posts>();


    }


    ShoppingItem(Intent intent){
        fTitle= intent.getStringExtra(ShoppingItem.TITLE);
        fStatus=Status.valueOf(intent.getStringExtra(ShoppingItem.STATUS));
        fDate= intent.getStringExtra(ShoppingItem.DATE);
        fAlimentos= (ArrayList<Posts>) intent.getSerializableExtra (ShoppingItem.ALIMENTOS);


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

    public ArrayList<Posts> getfAlimentos() {
        return fAlimentos;
    }

    public void setfAlimentos(ArrayList<Posts> fAlimentos) {
        this.fAlimentos = fAlimentos;
    }

    public String getDate(){

        return fDate;
    }
    public void setDate(String date){

        fDate=date;
    }



    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String title, Status status, String date,ArrayList<Posts> listaAlimentos) {

        intent.putExtra(ShoppingItem.TITLE, title);
        intent.putExtra(ShoppingItem.STATUS, status.toString());
        intent.putExtra(ShoppingItem.DATE, date);
        intent.putParcelableArrayListExtra(ShoppingItem.ALIMENTOS,listaAlimentos);


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
