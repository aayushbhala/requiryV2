package com.example.android.requiryv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MAHE on 01-Nov-17.
 */
public class ProFeedAdapter extends ArrayAdapter<Project> {
    private TextView mProjectNameTextView;
    private TextView mCircularTextView;
    private TextView mCreatedByTextView;
    private TextView mStartDateTextView;
    private TextView mEndDateTextView;
    private TextView mDomainTextView;
    private FirebaseDatabase mRequiryUserDatabse;
    private DatabaseReference mReqiryDatabaseReference;
    public static HashMap<String,Project> hashMap = new HashMap<>();

    public ProFeedAdapter(Context context, ArrayList<Project> arrayList) {
        super(context,0,arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View profeedListItemView = convertView;
        if(profeedListItemView==null){
            profeedListItemView = LayoutInflater.from(getContext()).inflate(R.layout.profeed_recycle_item,parent,false);
        }
        Project projectData = getItem(position);
        hashMap.put(projectData.getpID(),projectData);
        mRequiryUserDatabse = FirebaseDatabase.getInstance();
        mReqiryDatabaseReference = mRequiryUserDatabse.getReference().child("requiry_user");
        mReqiryDatabaseReference.orderByChild("uID").equalTo(projectData.getuID());

        mProjectNameTextView = (TextView) profeedListItemView.findViewById(R.id.project_name);
        mCircularTextView = (TextView) profeedListItemView.findViewById(R.id.text_circle);
        mCreatedByTextView = (TextView) profeedListItemView.findViewById(R.id.created_by);
        mStartDateTextView = (TextView) profeedListItemView.findViewById(R.id.start_date);
        mEndDateTextView = (TextView) profeedListItemView.findViewById(R.id.end_date);
        mDomainTextView = (TextView) profeedListItemView.findViewById(R.id.domain);

        mProjectNameTextView.setText(projectData.getpName());
        mCircularTextView.setText(""+projectData.getpName().charAt(0));
        mCreatedByTextView.setText(projectData.getuID()+"");
        String creator = "";
        if(ProFeedActivity.requiryUserMap.containsKey(projectData.getuID()))
            creator = ProFeedActivity.requiryUserMap.get(projectData.getuID()).getuName();
        mCreatedByTextView.setText(creator);

        mStartDateTextView.setText(projectData.getpDateStarts());
        mEndDateTextView.setText(projectData.getpDateEnds());
        mDomainTextView.setText(projectData.getpDomain());
        return profeedListItemView;
    }
}
