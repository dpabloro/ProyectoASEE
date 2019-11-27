package es.unex.giiis.asee.proyectoasee.roomdb;

import androidx.room.TypeConverter;

import java.util.ArrayList;

import es.unex.giiis.asee.proyectoasee.Posts;
import es.unex.giiis.asee.proyectoasee.ShoppingItem;

public class AlimentConverter {
    private static final String COMMA_SEP = ",";

    @TypeConverter
    public static ArrayList<Posts> toAliments (String alimentos){
        ArrayList<Posts> fAlimentos=new ArrayList<Posts>();
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

            if(i==alimentos.length()-1) {
                post = new Posts(ingrediente);
                post.setSelected(true);
                fAlimentos.add(post);
            }
        }

        return fAlimentos;
    }
    @TypeConverter
    public static String toString(ArrayList<Posts> aliments){
        String alimentos="";
        for(int i=0;i<aliments.size();i++){
            if (i==aliments.size()-1){
                alimentos+=aliments.get(i).getStrIngredient();
            } else
                alimentos+=aliments.get(i).getStrIngredient()+COMMA_SEP;
        }
        return alimentos;
    }
}
