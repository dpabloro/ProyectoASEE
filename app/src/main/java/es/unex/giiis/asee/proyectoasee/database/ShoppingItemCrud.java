package es.unex.giiis.asee.proyectoasee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import es.unex.giiis.asee.proyectoasee.ShoppingItem;

public class ShoppingItemCrud {
    private ManagerDbHelper mDbHelper;
    private static ShoppingItemCrud mInstance;

    private ShoppingItemCrud(Context context) {
        mDbHelper = new ManagerDbHelper(context);
    }

    public static ShoppingItemCrud getInstance(Context context){
        if (mInstance == null)
            mInstance = new ShoppingItemCrud(context);

        return mInstance;
    }

    public ArrayList<ShoppingItem> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.MainItem._ID,
                DBContract.MainItem.COLUMN_NAME_TITLE,
                DBContract.MainItem.COLUMN_NAME_STATUS,
                DBContract.MainItem.COLUMN_NAME_DATE,
                DBContract.MainItem.COLUMN_NAME_ALIMENTS
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.MainItem.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<ShoppingItem> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getToDoItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(ShoppingItem item){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.MainItem.COLUMN_NAME_TITLE, item.getTitle());
        values.put(DBContract.MainItem.COLUMN_NAME_STATUS, item.getStatus().name());
        values.put(DBContract.MainItem.COLUMN_NAME_DATE, item.getDate());
        values.put(DBContract.MainItem.COLUMN_NAME_ALIMENTS, item.getSAlimentos());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.MainItem.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteAll() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
            db.delete(DBContract.MainItem.TABLE_NAME, selection, selectionArgs);
    }

    public int updateStatus(long ID, String title, ShoppingItem.Status status, String date) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.d("ToDoItemCRUD","Item ID: "+ID);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBContract.MainItem.COLUMN_NAME_TITLE, title);
        values.put(DBContract.MainItem.COLUMN_NAME_STATUS, status.name());
        values.put(DBContract.MainItem.COLUMN_NAME_DATE, date);



        // Which row to update, based on the ID
        String selection = DBContract.MainItem._ID + " = ?";
        String[] selectionArgs = { Long.toString(ID) };

        int count = db.update(
                DBContract.MainItem.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static ShoppingItem getToDoItemFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.MainItem._ID));
        String title = cursor.getString(cursor.getColumnIndex(DBContract.MainItem.COLUMN_NAME_TITLE));
        String status = cursor.getString(cursor.getColumnIndex(DBContract.MainItem.COLUMN_NAME_STATUS));
        String date = cursor.getString(cursor.getColumnIndex(DBContract.MainItem.COLUMN_NAME_DATE));
        String alimentos = cursor.getString(cursor.getColumnIndex(DBContract.MainItem.COLUMN_NAME_ALIMENTS));

        ShoppingItem item = new ShoppingItem(ID,title,status,date,alimentos);

        Log.d("ShoppingItemCRUD",item.toLog());

        return item;
    }
}
