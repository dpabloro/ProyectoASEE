package es.unex.giiis.asee.proyectoasee;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Posts {

    public final static String SELECTED="selected";
    @SerializedName("strIngredient")
    @Expose
    private String strIngredient;

    @SerializedName("selected")
    @Expose
    private boolean selected;

    Posts(String ingredient){
        this.strIngredient=ingredient;
        this.selected=false;
    }

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




    public static void packageIntent(Intent intent, ArrayList<Posts> listSelected) {

        intent.putExtra(Posts.SELECTED, listSelected);



    }
}
