package es.unex.giiis.asee.proyectoasee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts {

    @SerializedName("idIngredient")
    @Expose
    private int idIngredient;
    @SerializedName("strIngredient")
    @Expose
    private String strIngredient;
    @SerializedName("strDescription")
    @Expose
    private String strDescription;
    @SerializedName("strType")
    @Expose
    private String strType;


    public int getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}
