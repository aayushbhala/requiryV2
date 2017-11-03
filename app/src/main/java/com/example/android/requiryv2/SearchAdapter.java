package com.example.android.requiryv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MAHE on 03-Nov-17.
 */
public class SearchAdapter extends ArrayAdapter<String> {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> userList;
    public SearchAdapter(Context context, ArrayList<String> userList,ArrayList<String> arrayList) {
        super(context,0,userList);
        this.arrayList = arrayList;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.search_adapter,parent,false);
        }

        String currentuID = (String) getItem(position);
        TextView mCirculatTV = (TextView) listItemView.findViewById(R.id.text_circle_username);
        TextView usernameTV = (TextView) listItemView.findViewById(R.id.user_name_text_view);
        if(ProFeedActivity.requiryUserMap.containsKey(currentuID)){
            RequiryUser requiryUser = ProFeedActivity.requiryUserMap.get(currentuID);
            mCirculatTV.setText(requiryUser.getuName().charAt(0));
            usernameTV.setText(requiryUser.getuName());
        }

        return listItemView;
    }
    public void filter(String charText){
        charText = charText.toLowerCase();
        userList.clear();
        for(String uID:arrayList){
            if(ProFeedActivity.requiryUserMap.containsKey(uID)){
                if(ProFeedActivity.requiryUserMap.get(uID).getuName().toLowerCase().contains(charText)){
                    userList.add(uID);
                }
            }
        }
        notifyDataSetChanged();
    }
}
