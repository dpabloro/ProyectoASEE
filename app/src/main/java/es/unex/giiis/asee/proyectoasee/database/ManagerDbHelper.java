package es.unex.giiis.asee.proyectoasee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shopping.db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TODOS =
            "CREATE TABLE " + DBContract.MainItem.TABLE_NAME + " (" +
                    DBContract.MainItem._ID + " INTEGER PRIMARY KEY," +
                    DBContract.MainItem.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DBContract.MainItem.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
                    DBContract.MainItem.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.MainItem.COLUMN_NAME_ALIMENTS + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_TODOS =
            "DROP TABLE IF EXISTS " + DBContract.MainItem.TABLE_NAME;

    public ManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TODOS);
        onCreate(db);
    }
}