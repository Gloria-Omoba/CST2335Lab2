package com.example.lab2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    String overflowMessage = "You clicked on the overflow menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch(item.getItemId())
        {

            case R.id.action_star:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_point:
                alertExample();
                return true;
            case R.id.action_lock:
                Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                Snackbar snackbar = Snackbar.make(toolbar, "Clicked item 3", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> finish());
                snackbar.show();
                return true;
            case R.id.overflow_menu:
                Toast.makeText(this,overflowMessage, Toast.LENGTH_LONG).show();
                return true;
        }
        return true;
    }

    public void alertExample()
    {
        View dialogBox = getLayoutInflater().inflate(R.layout.activity_dialog, null);
        EditText editText = (EditText)dialogBox.findViewById(R.id.editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Change overflow message ")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        overflowMessage = editText.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).setView(dialogBox);

        builder.create().show();
    }
}
