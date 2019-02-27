package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    EditText typeField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);
        //setContentView(R.layout.activity_main_lab3b);


        //save email using shared preferences
        typeField =(EditText)findViewById(R.id.editTextView2);
        sp= getSharedPreferences("FileName",Context.MODE_PRIVATE);
        String savedEmail= sp.getString("Email",  " ");
        typeField.setText(savedEmail);

        //Setting onClickListener for login button
        Button loginButton = (Button)findViewById(R.id.button3);
        loginButton.setOnClickListener( b -> {

            //Give directions to go from this page, to SecondActivity
            Intent nextPage = new Intent(MainActivity.this,ProfileActivity.class);

            //save email in email edit box in next page
            nextPage.putExtra("typed", typeField.getText().toString());


            //Now make the transition:
            startActivityForResult(nextPage, 2);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        //get an editor object
        SharedPreferences.Editor editor = sp.edit();

        //to save what was typed under name
        String inputTyped = typeField.getText().toString();

        editor.putString("Email",inputTyped);

        editor.commit();
    }
}
