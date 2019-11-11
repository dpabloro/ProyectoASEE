package es.unex.giiis.asee.proyectoasee;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchemaPosts {

    @SerializedName("listPosts")
    @Expose
    private List<Posts> listPosts= null;
    public List<Posts> getListPosts(){
        return listPosts;
    }
    public void setListPosts(List<Posts> listPosts){
        this.listPosts= listPosts;
    }

}
