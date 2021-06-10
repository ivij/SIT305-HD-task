package com.example.finaltask;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class AddNotesActivity1 extends AppCompatActivity {


    EditText title, description, time, quantity, location;
    CalendarView calendarView;
    ImageView image;
    Button saveNote;
    String date;
    List<Address> addresses;
    double lat,log;
    String name;
    LocationManager locationManager;
    LocationListener locationListener;

    int PICK_IMAGE_REQUEST = 200;
    Uri imageFilePath;
    Bitmap imageToStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes1);

        image = findViewById(R.id.image1);
        title = findViewById(R.id.title1);
        description = findViewById(R.id.count);
        calendarView = findViewById(R.id.calendarView1);
        time = findViewById(R.id.time1);
        quantity = findViewById(R.id.quantity1);
        location = findViewById(R.id.location1);
        saveNote = findViewById(R.id.saveNote1);

        Places.initialize(getApplicationContext(),"AIzaSyAzIN2sKtGOYtxJ8QdClj09Cyez3otNE_s");

        location.setFocusable(false);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent1 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(AddNotesActivity1.this);
                startActivityForResult(intent1,100);
            }
        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                });

                if (TextUtils.isEmpty(title.getText().toString())) {
                    Toast.makeText(AddNotesActivity1.this, "Title Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(description.getText().toString())) {
                    Toast.makeText(AddNotesActivity1.this, "Description Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (date == null) {
                    Toast.makeText(AddNotesActivity1.this, "Date Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(time.getText().toString())) {
                    Toast.makeText(AddNotesActivity1.this, "Time Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(quantity.getText().toString())) {
                    Toast.makeText(AddNotesActivity1.this, "Quantity Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(location.getText().toString())) {
                    Toast.makeText(AddNotesActivity1.this, "Location Field is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseClass1 db = new DatabaseClass1(AddNotesActivity1.this);
                    db.addNotes(imageToStore, title.getText().toString(), description.getText().toString(), date, time.getText().toString(), quantity.getText().toString(), location.getText().toString());


                    Intent intent = new Intent(AddNotesActivity1.this, MainActivity4.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    public void chooseImage1(View objectView) {

        confirmDialog();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null);
            {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                image.setImageBitmap(imageToStore);
            }


        } catch (Exception e) {

        }

        if (requestCode == 100 && resultCode == RESULT_OK)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            location.setText(place.getAddress());


            lat = place.getLatLng().latitude;
            log = place.getLatLng().longitude;
            name = place.getName();
        }

        else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Allow the app to access photos,media and files on your device?");
        builder.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent objectIntent = new Intent();
                objectIntent.setType("image/*");

                objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);


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