package es.unex.giiis.asee.proyectoasee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class ManejadorConexion {


    public ManejadorConexion(){

    }



    public String llamadaAlServicio(String urlEntrada) throws IOException {
        String response=null;
        //Lee la Url que viene de entrada
        URL url = new URL(urlEntrada);
        //Estableces la conexion
        HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

        BufferedReader bufferedReader;
        //conecta la conexion
        urlConnection.connect();

        InputStream inputStream= urlConnection.getInputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuffer sBuffer= new StringBuffer();
        String line="";

        while(((line= bufferedReader.readLine()) != null)){
            sBuffer.append(line +"\n");
            Log.d("Respuesta: " , ">" + line);
        }

        return  sBuffer.toString();

    }


    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');

                    is.close();
            }

        return sb.toString();
    }
}
