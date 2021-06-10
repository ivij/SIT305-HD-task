package com.example.finaltask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNotesActivity extends AppCompatActivity {


    EditText title, description,time,quantity,location;
    CalendarView calendarView;
    ImageView image;
    Button saveNote;
    String date;

    int PICK_IMAGE_REQUEST = 100;
    Uri imageFilePath;
    Bitmap imageToStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        calendarView = findViewById(R.id.calendarView);
        time = findViewById(R.id.time);
        quantity = findViewById(R.id.quantity);
        location = findViewById(R.id.location);
        saveNote = findViewById(R.id.saveNote);



        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month+1) + "/" + year;
                    }
                });

                if (TextUtils.isEmpty(title.getText().toString()))
                {
                    Toast.makeText(AddNotesActivity.this, "Title Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(description.getText().toString()))
                {
                    Toast.makeText(AddNotesActivity.this, "Description Field is Empty", Toast.LENGTH_SHORT).show();
                }
                else if (date == null)
                {
                    Toast.makeText(AddNotesActivity.this, "Date Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(time.getText().toString()))
                {
                    Toast.makeText(AddNotesActivity.this, "Time Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if ( TextUtils.isEmpty(quantity.getText().toString()))
                {
                    Toast.makeText(AddNotesActivity.this, "Quantity Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(location.getText().toString()))
                {
                    Toast.makeText(AddNotesActivity.this, "Location Field is Empty", Toast.LENGTH_SHORT).show();
                }


                else
                {
                    DatabaseClass db = new DatabaseClass(AddNotesActivity.this);
                    db.addNotes(imageToStore,title.getText().toString(),description.getText().toString(),date,time.getText().toString(),quantity.getText().toString(),location.getText().toString());


                    Intent intent = new Intent(AddNotesActivity.this, MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    public void chooseImage(View objectView)
    {
        try
        {
            confirmDialog();
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try
        {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null);
            {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                image.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Allow the app to access photos,media and files on your device?");
        builder.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent objectIntent = new Intent();
                objectIntent.setType("image/*");

                objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);


            }
        });

        builder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }



}