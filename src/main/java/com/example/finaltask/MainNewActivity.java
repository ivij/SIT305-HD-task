package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

    }

    public void showList(View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void addNewNote(View view) {
        Intent intent1 = new Intent(MainNewActivity.this, AddNotesActivity.class);
        startActivity(intent1);
    }

    // private static String data = "Ishan_Notes";

    // public static String getData()
    // {
    //    return data;
    //}

}