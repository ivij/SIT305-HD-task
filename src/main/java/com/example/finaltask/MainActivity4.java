package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    Adapter1 adapter;
    List<Model1> notesList1;
    DatabaseClass1 databaseClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        recyclerView = findViewById(R.id.recyclerView1);
        notesList1 = new ArrayList<>();
        databaseClass = new DatabaseClass1(this);
        fetchAllNotesFromDatabase1();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter1(this, MainActivity4.this,notesList1);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void fetchAllNotesFromDatabase1()
    {
        Cursor cursor = databaseClass.readAllData();
        if (cursor.getCount() == 0)
        {


        }

        else
        {
            while(cursor.moveToNext())
            {
                byte [] imageBytes = cursor.getBlob(1);
                Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                notesList1.add(new Model1(cursor.getString(0),objectBitmap,cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));

            }
        }
    }
    public void addNewNote1(View view) {
        Intent intent1 = new Intent(MainActivity4.this, AddNotesActivity1.class);
        startActivity(intent1);
    }
    public void showMenu1(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Intent intent = new Intent(MainActivity4.this,MainActivity2.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent1 = new Intent(MainActivity4.this,Account.class);
                startActivity(intent1);
                return true;

            case R.id.item3:
                Intent intent2 = new Intent(MainActivity4.this,MainActivity4.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent4 = new Intent(MainActivity4.this,cartActivity.class);
                startActivity(intent4);
                return true;

            case R.id.item5:
                Intent intent5 = new Intent(MainActivity4.this,SettingsActivity.class);
                startActivity(intent5);
                return true;


            default:
                return false;
        }
    }
}