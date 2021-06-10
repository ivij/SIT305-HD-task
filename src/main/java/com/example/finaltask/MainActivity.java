package com.example.finaltask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText usermail, userpassword;
    Button lgin, sgup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Log In");

        mFirebaseAuth = FirebaseAuth.getInstance();
        usermail = findViewById(R.id.umail);
        userpassword = findViewById(R.id.upwd);
        lgin = findViewById(R.id.login);
        sgup = findViewById(R.id.signup);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // updateUI(account);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser!= null){
                    Intent i = new Intent(MainActivity.this,GestureActivity.class);
                    startActivity(i);
                }
                else {
                }
            }
        };
        sgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intologin = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intologin);
            }
        });
        lgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailid = usermail.getText().toString();
                String pwd = userpassword.getText().toString();

                if (mailid.isEmpty()){
                    usermail.setError("Please enter the email");
                    usermail.requestFocus();
                }
                else if (pwd.isEmpty()){
                    userpassword.setError("Please Enter user Password");
                    userpassword.requestFocus();
                }
                else if (mailid.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are Empty!!",Toast.LENGTH_SHORT).show();
                }
                else if (!(mailid.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(mailid,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, " Error!!!",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                Intent intoHome = new Intent(MainActivity.this, GestureActivity.class);
                                startActivity(intoHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error Occurred!!", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //startActivity(signInIntent);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            startActivity(new Intent(MainActivity.this,GestureActivity.class));

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
            Log.d("signInResult: Failed", e.toString());
        }
    }
}
