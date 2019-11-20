package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

public class ShoppingItem implements Parcelable {
    public static final String ITEM_SEP= System.getProperty("line.separator");
    private static final String TAG = "ShoppingItem-UserInterface";

    private static final String COMMA_SEP = ",";

    public enum Status{
        PENDING, DONE
    };



    public final static String TITLE="title";
    public final static String STATUS="status";
    public final static String DATE="date";
    public final static String ALIMENTOS="alimentos";


    public final static String FILENAME="filename";


    private long id;
    private String fTitle = new String();
    private Status fStatus = Status.PENDING;
    private String fDate= new String();
    private ArrayList<Posts> fAlimentos=new ArrayList<Posts>();

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

    public ShoppingItem(long _id, String title, String status, String date, String alimentos) {
        id=_id;
        fTitle=title;
        fStatus=Status.valueOf(status);
        fDate=date;
        String ingrediente="";
        Posts post;
        for (int i = 0; i < alimentos.length(); i++) {
            if(alimentos.charAt(i)!=',')
                ingrediente+=alimentos.charAt(i);
            else {
                post = new Posts(ingrediente);
                post.setSelected(true);
                fAlimentos.add(post);
                ingrediente="";
            }
        }

        post = new Posts(ingrediente);
        post.setSelected(true);
        fAlimentos.add(post);




    }


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

    public String getSAlimentos() {
        String alimentos="";
        for(int i=0;i<fAlimentos.size();i++){
            if (i==fAlimentos.size()-1){
                alimentos+=fAlimentos.get(i).getStrIngredient();
            } else
                alimentos+=fAlimentos.get(i).getStrIngredient()+COMMA_SEP;
        }

        Log.i("LISTA ALIMENTOS", alimentos);

        return alimentos;
    }



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

    public void setID(long id) {
        this.id = id;
    }

    public long setID(){

        return id;
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


    public static void packageIntent(Intent intent, ArrayList<Posts> listaAlimentos) {

        intent.putParcelableArrayListExtra(ShoppingItem.ALIMENTOS,listaAlimentos);
        ArrayList<Posts> listPost = (ArrayList<Posts>) intent.getSerializableExtra("alimentos");

        for (int i=0;i<listPost.size();i++){
            log("LA LISTA DE ALIMENTOSSSSSSS ES: "+listPost.get(i).getStrIngredient());
        }

    }

    public String toString() {
        return fTitle + ITEM_SEP  + fStatus + ITEM_SEP
                + fDate;
    }

    public String toLog() {
        return "Title:" + fTitle + ITEM_SEP + "Status:" + fStatus + ITEM_SEP + "Date:"
                + fDate;
    }

    private static void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }


}
