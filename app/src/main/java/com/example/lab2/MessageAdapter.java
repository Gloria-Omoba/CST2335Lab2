package com.example.lab2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<ChatRoomMessage> messages;
    LayoutInflater inflater;


    public MessageAdapter(Context context, List<ChatRoomMessage> messages) {
        this.context = context;
        this.messages = messages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int id) {
        return (long) id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;

        //used to load XML layout

        if (messages.get(position).isSent()) {
            newView = inflater.inflate(R.layout.message_right, null);
        } else {
            newView= inflater.inflate(R.layout.message_left,null );
        }

        TextView  msg = newView.findViewById(R.id.txt_msg);
        msg.setText(messages.get(position).getMessage());

        return newView;
    }


}
