package com.example.cartera.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cartera.BaseDatos.FeedReaderContract.*;

import androidx.annotation.Nullable;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME = "Datos.db";
    public static final int  DATABASE_VERSION = 1;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " +
                FeedEntry.TABLE_NAME + " (" +
                FeedEntry._ID + " INTEGER PRIMARY KEY, " +
                FeedEntry.COLUMN_NOMBRE + " TEXT, " +
                FeedEntry.COLUMN_DIRECCION + " TEXT," +
                FeedEntry.COLUMN_EMAIL + " TEXT," +
                FeedEntry.COLUMN_TELEFONO + " TEXT)";
        db.execSQL(SQL_CREATE_LIST_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        onCreate(db);
    }
}
