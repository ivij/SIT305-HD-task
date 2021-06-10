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

public class DatabaseClass extends SQLiteOpenHelper {

    Context context;
    public static final String DatabaseName = "date1epyfss.db";
    public static final int DatabaseVersion = 1;

    public static final String TableName = "generalLists";
    public static final String ColumnId = "id";
    public static final String ColumnTitle = "title";
    public static final String ColumnDescription = "description";
    public static final String ColumnDate = "date";
    public static final String ColumnTime = "time";
    public static final String ColumnQuantity = "quantity";
    public static final String ColumnLocation = "location";

    public ByteArrayOutputStream objectByteArrayOutputStream;
    public byte[] imageInBytes;


    public DatabaseClass(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TableName +
                " (" + ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "image" + " BLOB, " +
                ColumnTitle + " TEXT, " +
                ColumnDescription + " TEXT, " +
                ColumnDate + " TEXT, " +
                ColumnTime + " TEXT, " +
                ColumnQuantity + " TEXT, " +
                ColumnLocation + " TEXT);";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void addNotes(Bitmap image,String title,String description,String date, String time, String quantity,String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = image;
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectByteArrayOutputStream);

        imageInBytes = objectByteArrayOutputStream.toByteArray();
        ContentValues cv = new ContentValues();
        cv.put("image",imageInBytes);
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);
        cv.put(ColumnDate, date);
        cv.put(ColumnTime, time);
        cv.put(ColumnQuantity, quantity);
        cv.put(ColumnLocation, location);

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

