package com.example.lab2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView listView;
    private View sendButton;
    private View receiveButton;
    private EditText editText;
    boolean myMessage = true;
    private List<ChatRoomMessage> chatMessages;
    private MessageAdapter adapter;
    MyDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thirdpage);


        listView = (ListView) findViewById(R.id.list);
        editText = (EditText)findViewById(R.id.Edit1);
        sendButton = (Button)findViewById(R.id.sendButton);
        receiveButton = (Button)findViewById(R.id.receiveButton);
        chatMessages = new ArrayList<>();

        db = new MyDatabaseHelper(this);
        adapter = new MessageAdapter(this, chatMessages);

        //get adapter to list view
        listView.setAdapter(adapter);
        viewData();

        //Event listener on send button
        sendButton.setOnClickListener(b-> {

                    if (editText.getText().toString().trim().equals("")) {
                        Toast.makeText(ChatRoomActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = editText.getText().toString();
                        db.insertData(message,true);

                        //notify chat room if list items have changed
                        //adapter.notifyDataSetChanged();
                        editText.setText("");
                        chatMessages.clear();
                        viewData();
                    }
                });



        //listener on receive button
        receiveButton.setOnClickListener(b->{
            if (editText.getText().toString().trim().equals("")) {
                Toast.makeText(ChatRoomActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
            } else {
                //insert data to database
                String message = editText.getText().toString();
                db.insertData(message,false);
                editText.setText("");
                chatMessages.clear();
                 viewData();
            }
        });

    }

    private void viewData(){
        Cursor cursor = db.viewData();

         if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                ChatRoomMessage model = new ChatRoomMessage(cursor.getString(1), cursor.getInt(2)==0);
                chatMessages.add(model);

                //notify chat room if list items have changed
                adapter.notifyDataSetChanged();
            }
         }

    }
}














