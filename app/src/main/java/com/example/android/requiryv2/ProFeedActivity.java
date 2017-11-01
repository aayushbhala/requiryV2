package com.example.android.requiryv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProFeedActivity extends AppCompatActivity {
    private ChildEventListener mProFeedEventListener;
    private ProFeedAdapter mProFeedAdapter;
    private FirebaseDatabase mProjectDatabase;
    private DatabaseReference mProjectDatabaseRefernce;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_feed);
        ArrayList<Project> projectData = new ArrayList<>();
        mProFeedAdapter = new ProFeedAdapter(this,projectData);
        listView = (ListView) findViewById(R.id.profeed_list_view);
        listView.setAdapter(mProFeedAdapter);
        mProjectDatabase = FirebaseDatabase.getInstance();
        mProjectDatabaseRefernce = mProjectDatabase.getReference().child("project");
        mProFeedEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project projectData = dataSnapshot.getValue(Project.class);
                mProFeedAdapter.add(projectData);
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mProjectDatabaseRefernce.addChildEventListener(mProFeedEventListener);
    }
}
