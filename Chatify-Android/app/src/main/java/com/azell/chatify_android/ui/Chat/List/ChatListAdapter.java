package com.azell.chatify_android.ui.Chat.List;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azell.chatify_android.Api.Model.User;
import com.azell.chatify_android.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcanhisares on 02/03/17.
 */

public class ChatListAdapter extends ArrayAdapter<User> {

    public ChatListAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = LayoutInflater.from(getContext())
                .inflate(R.layout.chat_list_item, null);
        TextView userTextView = (TextView) view.findViewById(R.id.text_chat_list_item);
        ImageView userImageView = (ImageView) view.findViewById(R.id.image_chat_list_item);
        User user = getItem(position);
        System.out.println(user);
        String url = user.getProfileImageUrl();
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(getContext())
                .load(Uri.parse(url))
                .fit()
                .transform(transformation)
                .into(userImageView);
        userTextView.setText(user.getUsername());

        return view;
    }
}
