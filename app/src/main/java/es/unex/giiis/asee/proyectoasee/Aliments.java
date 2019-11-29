package es.unex.giiis.asee.proyectoasee;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Aliments implements Parcelable {

    private static final String TAG = "Aliments-UserInterface";

    public final static String SELECTED="selectedItem";
    @SerializedName("strIngredient")
    @Expose
    private String strIngredient;

    @SerializedName("selected")
    @Expose
    private boolean selected;

    public Aliments(String ingredient){
        this.strIngredient=ingredient;
        this.selected=false;
    }


    protected Aliments(Parcel in) {
        strIngredient = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<Aliments> CREATOR = new Creator<Aliments>() {
        @Override
        public Aliments createFromParcel(Parcel in) {
            return new Aliments(in);
        }

        @Override
        public Aliments[] newArray(int size) {
            return new Aliments[size];
        }
    };

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getStrIngredient() {
        return strIngredient;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strIngredient);
        dest.writeByte((byte) (selected ? 1 : 0));
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
