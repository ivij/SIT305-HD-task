package com.example.finaltask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;

public class Account extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    FirebaseAuth mFirebaseAuth;
    ImageView image;
    TextView name,email,id;
    GoogleSignInClient mGoogleSignInClient;
    Button signOut;
    int PICK_IMAGE_REQUEST = 100;
    Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        signOut = findViewById(R.id.signOut);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            id.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);



            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            //Glide.with(this).load(String.valueOf(personPhoto)).into(image);

            String urlLink = acct.getPhotoUrl().toString();
            if (urlLink.isEmpty())
            {
                Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
            }
            else
            {
                LoadImage loadImage = new LoadImage(image);
                loadImage.execute(urlLink);
            }

        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();;
                FirebaseAuth.getInstance().signOut();
                Intent intoMain= new Intent(Account.this,MainActivity.class);
                startActivity(intoMain);
            }
        });


    }

    public void showMenu(View view)
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
                Intent intent = new Intent(Account.this,MainActivity2.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent1 = new Intent(Account.this,Account.class);
                startActivity(intent1);
                return true;

            case R.id.item3:
                Intent intent2 = new Intent(Account.this,MainActivity4.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent4 = new Intent(Account.this,cartActivity.class);
                startActivity(intent4);
                return true;
            case R.id.item5:
                Intent intent5 = new Intent(Account.this,SettingsActivity.class);
                startActivity(intent5);
                return true;

            default:
                return false;
        }


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Account.this,"Logged Out successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private class LoadImage extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView image)
        {
            this.imageView = image;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            return  bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }
}