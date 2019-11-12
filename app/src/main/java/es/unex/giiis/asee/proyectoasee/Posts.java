package es.unex.giiis.asee.proyectoasee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts {

    @SerializedName("strIngredient")
    @Expose
    private String strIngredient;


    Posts(String ingredient){
        this.strIngredient=ingredient;

    }


    public String getStrIngredient() {
        return strIngredient;
    }





    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }



}
