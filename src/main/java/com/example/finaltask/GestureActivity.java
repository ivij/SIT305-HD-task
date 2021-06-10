package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GestureActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    SwipeListener swipeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        relativeLayout = findViewById(R.id.relative_layout);
        swipeListener = new SwipeListener(relativeLayout);
    }
    private class SwipeListener implements View.OnTouchListener
    {
        GestureDetector gestureDetector;

        SwipeListener(View view)
        {
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float xDiff = e2.getX() - e1.getX();
                    float yDiff = e2.getY() - e1.getY();

                    try
                    {
                        if (Math.abs(xDiff)>Math.abs(yDiff))
                        {
                            if (Math.abs(xDiff) > threshold && Math.abs(velocityX) >velocity_threshold )
                            {
                                if (xDiff>0 )
                                {
                                    Intent intent = new Intent(GestureActivity.this,AddNotesActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent1 = new Intent(GestureActivity.this,AddNotesActivity1.class);
                                    startActivity(intent1);
                                }
                            }

                        }

                        else
                        {
                            if (Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold )
                            {
                                if ( yDiff>0)
                                {
                                    Intent intent2 = new Intent(GestureActivity.this,MainActivity2.class);
                                    startActivity(intent2);
                                }

                                else
                                {
                                    Intent intent3 = new Intent(GestureActivity.this,chatBotActivity.class);
                                    startActivity(intent3);
                                }
                            }

                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return false;
                }
            };

            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }
}