package com.homeland.android.homeland.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Muhammad on 4/27/2017
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "data.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_PROPERTY_TABLE = "CREATE TABLE " + DataContract.DataEntry.TABLE_PROPERTY + " (" +
                DataContract.DataEntry.COLUMN_PROPERTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.DataEntry.COLUMN_TITLE + " TEXT, " +
                DataContract.DataEntry.COLUMN_CODE + " INTEGER, " +
                DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT, " +
                DataContract.DataEntry.COLUMN_FACEBOOK + " TEXT, " +
                DataContract.DataEntry.COLUMN_TWITTER + " TEXT, " +
                DataContract.DataEntry.COLUMN_INSTAGRAM + " TEXT, " +
                DataContract.DataEntry.COLUMN_PRICE + " INTEGER);";


        final String SQL_CREATE_CARS_TABLE = "CREATE TABLE " + DataContract.DataEntry.TABLE_CARS + " (" +
                DataContract.DataEntry.COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT);";


        db.execSQL(SQL_CREATE_PROPERTY_TABLE);
        db.execSQL(SQL_CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.DataEntry.TABLE_PROPERTY);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.DataEntry.TABLE_CARS);
        onCreate(db);
    }
}
