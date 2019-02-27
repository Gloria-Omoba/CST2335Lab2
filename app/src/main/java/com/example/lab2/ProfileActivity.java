package com.example.lab2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3b);


        Log.e(ACTIVITY_NAME,"OnCreate" );

        //get intent from previous page
        Intent previous = getIntent();

        String previousTyped = previous.getStringExtra("typed");

        //Put the string that was sent from FirstActivity into the edit text:
        EditText email2 = (EditText)findViewById(R.id.email2);
        email2.setText(previousTyped);

        //initialize and set on  click listener on the image button
        mImageButton = (ImageButton)findViewById(R.id.imageButton1);
        mImageButton.setOnClickListener( b -> {
            dispatchTakePictureIntent();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"OnStart" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"OnResume" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME,"OnPause" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME,"OnStop" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"onDestroy" );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
            Log.e(ACTIVITY_NAME,"onActivityResult" );
        }
    }

    private void dispatchTakePictureIntent () {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
