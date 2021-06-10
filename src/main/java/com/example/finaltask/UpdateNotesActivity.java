package com.example.finaltask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class UpdateNotesActivity extends AppCompatActivity {

    EditText title, description,time,quantity;
    ImageButton location;
    ImageView image;
    ImageView im;
    Button cart;
    EditText date;
    String id;
    String location_text;
    double longitude1, latitude1;
    Uri img;
    private Button paypal;

    int PICK_IMAGE_REQUEST = 100;
    Uri imageFilePath;
    Bitmap imageToStore;

    private int PAYPAL_REQ_CODE =12;
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalClientIdConfigClass.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        try {
            image = findViewById(R.id.image2);
            title = findViewById(R.id.title2);
            description = findViewById(R.id.description2);
            time = findViewById(R.id.time2);
            quantity = findViewById(R.id.quantity2);
            location = findViewById(R.id.location2);
            cart = findViewById(R.id.cart);
            date = findViewById(R.id.date2);

            Intent i = getIntent();
            title.setText(i.getStringExtra("title"));
            description.setText(i.getStringExtra("description"));
            date.setText(i.getStringExtra("date"));
            time.setText(i.getStringExtra("time"));
           // location.setText(i.getStringExtra("location"));
            location_text = (i.getStringExtra("location"));
            id = i.getStringExtra("id");
            quantity.setText(i.getStringExtra("quantity"));

            paypal = findViewById(R.id.paypal);

            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfig);
            startService(intent);

            paypal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PaypalPaymentMethods();

                }
            });


            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseCart db = new DatabaseCart(UpdateNotesActivity.this);
                    db.addToCart(title.getText().toString());

                    Toast.makeText(UpdateNotesActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();

                }
            });


            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LatLng l = getCoordinates(UpdateNotesActivity.this,location_text);
                    Bundle args = new Bundle();
                    args.putParcelable("position",l);
                    Intent intent = new Intent(UpdateNotesActivity.this,MapsActivity.class);
                    intent.putExtra("bundle",args);
                    startActivity(intent);

                }
            });


            byte[] byteArray = i.getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            image.setImageBitmap(bmp);
           // img = Uri.parse(i.getStringExtra("image"));
            //im = MediaStore.Images.Media.getBitmap(getContentResolver(), img);
            //image.setImageBitmap(im);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public LatLng getCoordinates(Context context,String strAddress)
    {
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = geocoder.getFromLocationName(strAddress,1);
            if (address == null)
            {
                return  null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(),location.getLongitude());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return p1;
    }

    private void PaypalPaymentMethods()
    {
        PayPalPayment payments = new PayPalPayment(new BigDecimal(1),"AUD",title.getText().toString(),PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payments);

        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
}