package com.example.android.requiryv2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MAHE on 03-Nov-17.
 */
public class ContributorAdapter extends ArrayAdapter<Contributer> {
    String pID;
    public ContributorAdapter(Context context, ArrayList<Contributer> arrayList,String pid) {
        super(context,0,arrayList);
        this.pID = pid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.contributor_adapter,parent,false);
        }
        Contributer currentContributor = (Contributer) getItem(position);

        Log.e("Contributor ",currentContributor.getpId()+ " " + currentContributor.getuId());
        TextView mCircularTV = (TextView) listItemView.findViewById(R.id.contributor_text_view);
        TextView usernameTV = (TextView) listItemView.findViewById(R.id.contributor_name_text_view);
        if(pID.equals(currentContributor.getpId())) {
            if (ProFeedActivity.requiryUserMap.containsKey(currentContributor.getuId())) {
                RequiryUser requiryUser = ProFeedActivity.requiryUserMap.get(currentContributor.getuId());
                if(!requiryUser.getuName().equals("")) {
                    mCircularTV.setText("" + requiryUser.getuName().charAt(0));
                    usernameTV.setText(requiryUser.getuName());
                }
            }
        }

        return listItemView;
    }

}
