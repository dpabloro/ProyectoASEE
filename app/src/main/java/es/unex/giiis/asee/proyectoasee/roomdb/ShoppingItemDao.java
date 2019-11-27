package es.unex.giiis.asee.proyectoasee.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

import es.unex.giiis.asee.proyectoasee.ShoppingItem;


@Dao
public interface ShoppingItemDao {

    @Query("SELECT * FROM shopping")
    public LiveData<ArrayList<ShoppingItem>> getAll();

    @Insert
    public long insert(ShoppingItem item);

    @Update
    public int updateStatus(ShoppingItem item);

    @Query("DELETE FROM shopping")
    public void deleteAll();
}
