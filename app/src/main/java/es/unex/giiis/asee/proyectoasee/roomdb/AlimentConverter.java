package es.unex.giiis.asee.proyectoasee.roomdb;

import androidx.room.TypeConverter;

import java.util.ArrayList;

import es.unex.giiis.asee.proyectoasee.Aliments;

public class AlimentConverter {
    private static final String COMMA_SEP = ",";

    @TypeConverter
    public static ArrayList<Aliments> toAliments (String alimentos){
        ArrayList<Aliments> fAlimentos=new ArrayList<Aliments>();
        String ingrediente="";
        Aliments post;

        for (int i = 0; i < alimentos.length(); i++) {
            if(alimentos.charAt(i)!=',')
                ingrediente+=alimentos.charAt(i);
            else {
                post = new Aliments(ingrediente);
                post.setSelected(true);
                fAlimentos.add(post);
                ingrediente="";
            }

            if(i==alimentos.length()-1) {
                post = new Aliments(ingrediente);
                post.setSelected(true);
                fAlimentos.add(post);
            }
        }

        return fAlimentos;
    }
    @TypeConverter
    public static String toString(ArrayList<Aliments> aliments){
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
