package es.unex.giiis.asee.proyectoasee;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyectoasee.database.DBContract;
import es.unex.giiis.asee.proyectoasee.roomdb.ShoppingItemDao;
import es.unex.giiis.asee.proyectoasee.roomdb.ShoppingItemDatabase;

public class ShoppingRepository {

    private ShoppingItemDao shoppingItemDao;

    private LiveData<List<ShoppingItem>> shoppingItemList;

    ShoppingRepository(Application application) {

        ShoppingItemDatabase db = ShoppingItemDatabase.getDatabase(application);
        shoppingItemDao = db.shoppingItemDao();
        shoppingItemList = shoppingItemDao.getAll();
    }


    LiveData<List<ShoppingItem>> getAllItems() {
        return shoppingItemList;
    }

    public void insert(ShoppingItem shoppingItem) {
        new AsyncInsert(shoppingItemDao).execute(shoppingItem);
    }

    public void deleteAll() {
        new AsyncDeleteAll(shoppingItemDao).execute();
    }

    public void update(ShoppingItem shoppingItem) {
        new AsyncUpdate(shoppingItemDao).execute(shoppingItem);

    }


    class AsyncInsert extends AsyncTask<ShoppingItem,Void,Void> {

        private ShoppingItemDao mAsyncTaskDao;

        AsyncInsert(ShoppingItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ShoppingItem... items) {
            mAsyncTaskDao.insert(items[0]);

            // Log.i(TAGJ, "Guardado item en base de datos con id:"+id);
            return null;
        }
    }


    class AsyncUpdate extends AsyncTask<ShoppingItem,Void,Void> {

        private ShoppingItemDao mAsyncTaskDao;

        AsyncUpdate(ShoppingItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ShoppingItem... items) {
            mAsyncTaskDao.updateStatus(items[0]);

            // Log.i(TAGJ, "Guardado item en base de datos con id:"+id);
            return null;
        }
    }


    class AsyncDeleteAll extends AsyncTask<Void,Void,Void>{

        private ShoppingItemDao mAsyncTaskDao;

        AsyncDeleteAll(ShoppingItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void ... voids){

            mAsyncTaskDao.deleteAll();
            return null;
        }
    }




}





