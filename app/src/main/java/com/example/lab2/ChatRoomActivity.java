package com.example.lab2;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<String> list=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thirdpage);

        ListView myList = (ListView) findViewById(R.id.list);

        ListAdapter aListAdapter = new MyListAdapter();


        SwipeRefreshLayout refresher = (SwipeRefreshLayout)findViewById(R.id.refresher) ;
        refresher.setOnRefreshListener(()-> {
           // numObjects *= 2;
            ((MyListAdapter) aListAdapter).notifyDataSetChanged();
            refresher.setRefreshing( false );
        });

        //set adapter on the list view
        myList.setAdapter(aListAdapter );


        //This listens for items being clicked in the list view
        myList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.e("you clicked on :" , "item "+ position);

           // numObjects = 20;
            ((MyListAdapter) aListAdapter).notifyDataSetChanged();
        });

        EditText text = (EditText)findViewById(R.id.Edit1);

        Button sendButton = (Button)findViewById(R.id.sendButton);

        //listener on send button
        sendButton.setOnClickListener(b->{
            text.clearComposingText();
            ((MyListAdapter) aListAdapter).notifyDataSetChanged();
        });

        //listener on receive button
        Button receiveButton = (Button)findViewById(R.id.receiveButton);
        sendButton.setOnClickListener(b->{
            text.clearComposingText();
            ((MyListAdapter) aListAdapter).notifyDataSetChanged();
        });

    }


    //This class needs 4 functions to work properly:
    protected class MyListAdapter extends BaseAdapter
    {

        @Override
        //returns the number of items to display in the list
        public int getCount() {
            return list.size();
        }

        //returns what to show at a row position
        public Object getItem(int position){
            return list.get(position);
        }

        //creates a view object to go in a row of the ListView
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.activity_main_thirdpage,parent, false );


            EditText rowText = (EditText)newView.findViewById(R.id.Edit1);
            String stringToShow = getItem(position).toString();
            rowText.setText( stringToShow );

            //return the row:
            return newView;
        }

        //returns the database id of the item at position i
        public long getItemId(int position)
        {
            return position;
        }

    }



}














