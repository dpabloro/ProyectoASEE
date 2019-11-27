package es.unex.giiis.asee.proyectoasee.roomdb;

import androidx.room.TypeConverter;

import es.unex.giiis.asee.proyectoasee.ShoppingItem;

public class StatusConverter {

    @TypeConverter
    public static ShoppingItem.Status toStatus (String status){
        return ShoppingItem.Status.valueOf(status);
    }
    @TypeConverter
    public static String toString(ShoppingItem.Status status){
        return status.name();
    }
}
