package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;

import es.unex.giiis.asee.proyectoasee.roomdb.AlimentConverter;
import es.unex.giiis.asee.proyectoasee.roomdb.StatusConverter;
import retrofit2.http.POST;

@Entity(tableName = "shopping")
public class ShoppingItem implements Parcelable {
    @Ignore
    public static final String ITEM_SEP= System.getProperty("line.separator");

    private static final String TAG = "ShoppingItem-UserInterface";

    private static final String COMMA_SEP = ",";

    public enum Status{
        PENDING, DONE
    };

    @Ignore
    public static final String ID = "id";
    @Ignore
    public final static String TITLE="title";
    @Ignore
    public final static String STATUS="status";
    @Ignore
    public final static String DATE="date";
    @Ignore
    public final static String ALIMENTOS="alimentos";

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title = new String();
    @TypeConverters(StatusConverter.class)
    private Status status = Status.PENDING;
    private String date= new String();
    @TypeConverters(AlimentConverter.class)
    private ArrayList<Posts> alimentos=new ArrayList<Posts>();

    @Ignore
    protected ShoppingItem(Parcel in) {
        this.title = in.readString();
        this.date = in.readString();
        this.alimentos = in.createTypedArrayList(Posts.CREATOR);
    }

    @Ignore
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

    @Ignore
    public ShoppingItem(long id, String title, String status, String date, String alimentos) {
        this.id=id;
        this.title=title;
        this.status=Status.valueOf(status);
        this.date=date;
        String ingrediente="";
        Posts post;
        for (int i = 0; i < alimentos.length(); i++) {
            if(alimentos.charAt(i)!=',')
                ingrediente+=alimentos.charAt(i);
            else {
                post = new Posts(ingrediente);
                post.setSelected(true);
                this.alimentos.add(post);
                ingrediente="";
            }

            if(i==alimentos.length()-1) {
                post = new Posts(ingrediente);
                post.setSelected(true);
                this.alimentos.add(post);
            }
        }

    }


    public ShoppingItem(long id, String title, Status status, String date, ArrayList<Posts> alimentos) {
        this.id=id;
        this.title=title;
        this.status=status;
        this.date=date;
        this.alimentos=alimentos;

    }




    @Ignore
    public String getSAlimentos() {
        String aliments="";
        for(int i=0;i<this.alimentos.size();i++){
            if (i==this.alimentos.size()-1){
                aliments+=this.alimentos.get(i).getStrIngredient();
            } else
                aliments+=this.alimentos.get(i).getStrIngredient()+COMMA_SEP;
        }

        Log.i("LISTA ALIMENTOS", aliments);

        return aliments;
    }



    @Ignore
    ShoppingItem(String t, Status s, String d){
        this.title=t;
        this.status=s;
        this.date=d;
        this.alimentos=new ArrayList<Posts>();


    }

    @Ignore
    ShoppingItem(Intent intent){
       this.title= intent.getStringExtra(ShoppingItem.TITLE);
        this.status=Status.valueOf(intent.getStringExtra(ShoppingItem.STATUS));
        this.date= intent.getStringExtra(ShoppingItem.DATE);
        this.alimentos= (ArrayList<Posts>) intent.getSerializableExtra (ShoppingItem.ALIMENTOS);
    }

    public String getTitle(){

        return this.title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public Status getStatus(){

        return status;
    }
    public void setStatus(Status status){

        this.status=status;
    }

    public ArrayList<Posts> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(ArrayList<Posts> fAlimentos) {
        this.alimentos = fAlimentos;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId(){

        return id;
    }

    public String getDate(){

        return date;
    }
    public void setDate(String date){

        this.date=date;
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
        return title + ITEM_SEP  + status + ITEM_SEP
                + date;
    }

    public String toLog() {
        return "Title:" + title + ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:"
                + date;
    }

    private static void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeTypedList(alimentos);
    }

}
