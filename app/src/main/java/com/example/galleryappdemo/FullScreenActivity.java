package com.example.galleryappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FullScreenActivity extends AppCompatActivity {

    ImageView fullImage;
    String image = "";
    String name = "";
    ScaleGestureDetector scaleGestureDetector;
    float scaleFactor = 1.0f;
    //TextView textView;

    float x, y;
    float dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        fullImage = findViewById(R.id.fullImage);
        //textView = findViewById(R.id.textView);


        Intent intent = getIntent();
        image = intent.getStringExtra("parseData");

        name = intent.getStringExtra("imageName");
        //textView.setText("Location: "+name);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this).load(image).into(fullImage);

        scaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x = event.getX();
            y = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            dx = event.getX() - x;
            dy = event.getY() - y;

            fullImage.setX(fullImage.getX() + dx);
            fullImage.setY(fullImage.getY() + dy);

            x = event.getX();
            y = event.getY();
        }

        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f,Math.min(scaleFactor,10.0f));

            fullImage.setScaleX(scaleFactor);
            fullImage.setScaleY(scaleFactor);

            return true;
        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        startActivity(getIntent());
    }*/
}
