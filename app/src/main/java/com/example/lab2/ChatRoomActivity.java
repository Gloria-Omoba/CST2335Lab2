package com.example.lab2;

import android.content.Intent;
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

    ListView listView;
    Button sendButton;
    Button receiveButton;
    EditText editText;
    //boolean myMessage = true;
    List<ChatRoomMessage> chatMessages;
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
        boolean isTable = findViewById(R.id.fragmentLocation) != null; // check if frame is loaded

        viewData();

        listView.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("item", chatMessages.get(position).getMessage());
           // dataToPass.putInt("id", position);
            dataToPass.putLong("dataId", chatMessages.get(position).getMessageID());


            if (isTable){
                DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            }else {
                Intent emptyActivity = new Intent(this, EmptyActivity.class);
                emptyActivity.putExtras(dataToPass);
                startActivityForResult(emptyActivity, 345);
            }

        });



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



        /*//listener on receive button
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
        });*/

    }

    private void viewData(){
        Cursor cursor = db.viewData();

         if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                ChatRoomMessage model = new ChatRoomMessage(cursor.getString(1), cursor.getInt(2)==0?true:false, cursor.getLong(0));
                chatMessages.add(model);
                MessageAdapter adapter = new MessageAdapter(getApplicationContext(), chatMessages);

                //get adapter to list view
                listView.setAdapter(adapter);

                //notify chat room if list items have changed
              // adapter.notifyDataSetChanged();
            }
         }

    }

    //This function only gets called on the phone. The tablet never goes to a new activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 345)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra("dataId", 0);
                deleteMessageId((int)id);
            }
        }
    }

    public void deleteMessageId(int id)
    {

        db.deleteEntry(id);
        chatMessages.clear();
        viewData();
    }
}














