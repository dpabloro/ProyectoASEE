package es.unex.giiis.asee.proyectoasee.database;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class MainItem implements BaseColumns {
        public static final String TABLE_NAME = "shopping";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ALIMENTS = "aliments";

    }

}
