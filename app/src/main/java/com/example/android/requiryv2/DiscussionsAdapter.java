package com.example.android.requiryv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by tito on 4/11/17.
 */

public class DiscussionsAdapter extends ArrayAdapter<Discussions> {

    TextView discussionsMessage, messageSender, messageTime;


    public DiscussionsAdapter(Context context, ArrayList<Discussions> arrayList) {
        super(context,0,arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View discussionsMessageItemView = convertView;
        if(discussionsMessageItemView==null){
            discussionsMessageItemView = LayoutInflater.from(getContext()).inflate(R.layout.discussions_message_item, parent, false);
        }

        Discussions discussions = getItem(position);

        discussionsMessage = discussionsMessageItemView.findViewById(R.id.discussion_message);
        messageSender = discussionsMessageItemView.findViewById(R.id.message_sender_textview);
        messageTime = discussionsMessageItemView.findViewById(R.id.message_date_textview);

        discussionsMessage.setText(discussions.getMsg());
        messageSender.setText(discussions.getuUserName());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date obj = sdf.parse(discussions.getTimestamp());
            SimpleDateFormat newSdf = new SimpleDateFormat("MMM dd, HH:mm");
            String msgTime = newSdf.format(obj);
            messageTime.setText(msgTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //messageSender.setText(discussions.getTimestamp());

        return discussionsMessageItemView;
    }
}
