package com.example.android.requiryv2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private String creator = "";

    public ProFeedAdapter(Context context, ArrayList<Project> arrayList) {
        super(context,0,arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View profeedListItemView = convertView;
        if(profeedListItemView==null){
            profeedListItemView = LayoutInflater.from(getContext()).inflate(R.layout.profeed_recycle_item,parent,false);
        }
        final Project projectData = getItem(position);

        mProjectNameTextView = (TextView) profeedListItemView.findViewById(R.id.project_name);
        mCircularTextView = (TextView) profeedListItemView.findViewById(R.id.text_circle);
        mCreatedByTextView = (TextView) profeedListItemView.findViewById(R.id.created_by);
        mStartDateTextView = (TextView) profeedListItemView.findViewById(R.id.start_date);
        mEndDateTextView = (TextView) profeedListItemView.findViewById(R.id.end_date);
        mDomainTextView = (TextView) profeedListItemView.findViewById(R.id.domain);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cnt = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    cnt++;
                    RequiryUser ru = ds.getValue(RequiryUser.class);
                    creator = ru.getuName();
                }
                mCreatedByTextView.setText(creator);
                creator = "";
                Log.d("Message", projectData.getpName()+"="+String.valueOf(cnt));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mRequiryUserDatabse = FirebaseDatabase.getInstance();
        mReqiryDatabaseReference = mRequiryUserDatabse.getReference();
        mReqiryDatabaseReference.child("requiry_user").orderByChild("uID").equalTo(projectData.getuID()).addValueEventListener(listener);
        mReqiryDatabaseReference.removeEventListener(listener);

        mProjectNameTextView.setText(projectData.getpName());
        mCircularTextView.setText(""+projectData.getpName().charAt(0));

        /* SimpleDateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        String start_date = null;
        String end_date = null;
        try {
            Date st_date = date.parse(projectData.getpDateStarts());
            Date ed_date = date.parse(projectData.getpDateEnds());
             Log.e("ProFeed","start date:"+st_date);
             Log.e("ProFeed","end date:"+ed_date);
            start_date = simpleDateFormat.format(st_date);
            end_date = simpleDateFormat.format(ed_date);
        } catch (Exception e) {
            Log.e("Pro Feed", "Parsing failed miserably " + e);
        }*/
        mStartDateTextView.setText(projectData.getpDateStarts());
        mEndDateTextView.setText(projectData.getpDateEnds());
        mDomainTextView.setText(projectData.getpDomain());
        return profeedListItemView;
    }
}
