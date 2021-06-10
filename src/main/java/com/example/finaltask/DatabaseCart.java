package com.example.finaltask;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseCart extends SQLiteOpenHelper {

    Context context;
    public static final String DatabaseName = "ca1rtDatas.db";
    public static final int DatabaseVersion = 1;

    public static final String TableName = "cart";
    public static final String ColumnId = "id";
    public static final String ColumnTitle = "title";


    public DatabaseCart(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TableName +
                " (" + ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnTitle + " TEXT);";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void addToCart(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(ColumnTitle, title);


        long resultValue = db.insert(TableName,null,cv);
        if (resultValue == -1)
        {

        }

        else
        {
        }
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if(database!= null)
        {
            cursor = database.rawQuery(query,null);
        }
        return cursor;
    }



}

