package com.azell.chatify_android.ui.Chat.Detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.azell.chatify_android.Api.Model.Message;
import com.azell.chatify_android.R;

import java.util.ArrayList;

/**
 * Created by mcanhisares on 05/03/17.
 */

public class MessageListAdapter extends ArrayAdapter<Message> {

    public MessageListAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = LayoutInflater.from(getContext())
                .inflate(R.layout.message_item, null);
        TextView userTextView = (TextView) view.findViewById(R.id.text_username_message);
        TextView messageTextView = (TextView) view.findViewById(R.id.text_message_content);
        TextView timeMessageTextView = (TextView) view.findViewById(R.id.text_time_message);
        Message message = getItem(position);
        System.out.println(message);

        userTextView.setText(message.getUsername());
        messageTextView.setText(message.getText());
        //TODO IMPLEMENT MESSAGE TIME
//        timeMessageTextView.setText(message.getTime());
        return view;
    }
}
