package es.unex.giiis.asee.proyectoasee.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.giiis.asee.proyectoasee.ShoppingItem;

@Database(entities = {ShoppingItem.class}, version = 1)
public abstract class ShoppingItemDatabase extends RoomDatabase {
    private static ShoppingItemDatabase instance;

    public static ShoppingItemDatabase getDatabase(Context context) {

        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShoppingItemDatabase.class, "shopping.db")
                    .build();

        return instance;
    }

    public abstract ShoppingItemDao shoppingItemDao();
}

