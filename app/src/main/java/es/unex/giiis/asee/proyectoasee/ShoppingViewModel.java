package es.unex.giiis.asee.proyectoasee;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;


public class ShoppingViewModel extends AndroidViewModel {

    private ShoppingRepository mRepository;
    private LiveData<List<ShoppingItem>> mAllItems;

    public ShoppingViewModel (Application application) {
        super(application);
        mRepository = new ShoppingRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<ShoppingItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(ShoppingItem shoppingItem) {
        mRepository.insert(shoppingItem);
    }

    public void update(ShoppingItem shoppingItem) {
        mRepository.update(shoppingItem);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }




}
