package com.example.finaltask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    SeekBar seekBar;
    TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar = findViewById(R.id.seekBar);
        value = findViewById(R.id.value);

        int cBrightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);
        seekBar.setProgress(cBrightness);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();
                boolean canWrite = Settings.System.canWrite(context);
                if(canWrite)
                {
                    int sBrightness = progress*255/100;
                    int valueToShow = progress*255/255;
                    value.setText(valueToShow + "");
                    Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,sBrightness);

                }
                else
                {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void showMenuset(View view)
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
                Intent intent = new Intent(SettingsActivity.this,MainActivity2.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent1 = new Intent(SettingsActivity.this,Account.class);
                startActivity(intent1);
                return true;

            case R.id.item3:
                Intent intent2 = new Intent(SettingsActivity.this,MainActivity4.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent4 = new Intent(SettingsActivity.this,cartActivity.class);
                startActivity(intent4);
                return true;

            case R.id.item5:
                Intent intent5 = new Intent(SettingsActivity.this,SettingsActivity.class);
                startActivity(intent5);
                return true;


            default:
                return false;
        }
    }
}