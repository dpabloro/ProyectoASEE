package es.unex.giiis.asee.proyectoasee;

import android.os.AsyncTask;

import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConexionAPI {



    class AsyncConnect extends AsyncTask<String,Void,Void>  {

        @Override
        protected Void doInBackground(String... strings) {
            ManejadorConexion mConexion = new ManejadorConexion();
            String url="https://www.themealdb.com/api/json/v1/1/list.php?i=list";

            String jsonStr= null;
            try {
                jsonStr = mConexion.llamadaAlServicio(url);
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(jsonStr!=null){
                JSONObject jsonObject=null;
                try {
                   jsonObject = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray arrayList=null;
                try {
                    arrayList =jsonObject.getJSONArray("arrayItems");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ShoppingItem shoppingItem;
                for (int i=0; i< 50; i++){
                    JSONObject item=null;
                    try {
                         item = arrayList.getJSONObject(i);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    try {
                        String strIngredient = item.getString("strIngredient");
                    } catch (JSONException e){
                        e.printStackTrace();

                    }



                }


            }


            return null;
        }
    }

    }
