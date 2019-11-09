package es.unex.giiis.asee.proyectoasee.Interface;

import java.util.List;

import es.unex.giiis.asee.proyectoasee.Model.Posts;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("list.php?i=list")
    Call<List<Posts>> getPosts();


}
