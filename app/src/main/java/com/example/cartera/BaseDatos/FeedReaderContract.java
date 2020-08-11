package com.example.cartera.BaseDatos;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "CLIENTE";
        public static final String COLUMN_NOMBRE = "NOMBRE";
        public static final String COLUMN_DIRECCION = "DIRECCION";
        public static final String COLUMN_EMAIL = "EMAIL";
        public static final String COLUMN_TELEFONO = "TELEFONO";
    }
}
