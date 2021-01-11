package com.example.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.lab2.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView listView;
    private View sendButton;
    private View receiveButton;
    private EditText editText;
    boolean myMessage = true;
    private List<ChatRoomMessage> chatMessages;
    private ArrayAdapter<ChatRoomMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thirdpage);

        //to store messages
        chatMessages = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list);
        editText = (EditText)findViewById(R.id.Edit1);
        sendButton = (Button)findViewById(R.id.sendButton);
        receiveButton = (Button)findViewById(R.id.receiveButton);

        adapter= new MessageAdapter(this, R.layout.message_left, chatMessages);

        //get adapter to list view
        listView.setAdapter(adapter);

        //Event listener on send button
        sendButton.setOnClickListener(b-> {

                    if (editText.getText().toString().trim().equals("")) {
                        Toast.makeText(ChatRoomActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                    } else {
                        //add message to list
                        ChatRoomMessage chat = new ChatRoomMessage(editText.getText().toString(), true);
                        chatMessages.add(chat);

                        //notify chat room if list items have changed
                        adapter.notifyDataSetChanged();
                        editText.setText("");
                    }
                });



        //listener on receive button
        receiveButton.setOnClickListener(b->{
            if (editText.getText().toString().trim().equals("")) {
                Toast.makeText(ChatRoomActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
            } else {
                //add message to list
                ChatRoomMessage chat = new ChatRoomMessage(editText.getText().toString(), false);
                chatMessages.add(chat);

                //notify chat room if list items have changed
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }
}














