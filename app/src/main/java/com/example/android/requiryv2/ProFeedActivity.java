package com.example.android.requiryv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    private ArrayList<Project> projectData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_feed);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProFeedActivity.this,CreateProject.class);
                startActivity(intent);

            }
        });
        projectData = new ArrayList<>();
        mProFeedAdapter = new ProFeedAdapter(this,projectData);
        listView = (ListView) findViewById(R.id.profeed_list_view);
        listView.setAdapter(mProFeedAdapter);
        mProjectDatabase = FirebaseDatabase.getInstance();
        mProjectDatabaseRefernce = mProjectDatabase.getReference().child("project");
        mProFeedEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project project = dataSnapshot.getValue(Project.class);
                mProFeedAdapter.add(project);
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Project project = dataSnapshot.getValue(Project.class);
                mProFeedAdapter.remove(ProFeedAdapter.hashMap.get(project.getpID()));
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
