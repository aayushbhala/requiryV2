package com.example.android.requiryv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        final ArrayList<Project> projectData = new ArrayList<>();

        mProFeedAdapter = new ProFeedAdapter(this,projectData);
        listView = (ListView) findViewById(R.id.profeed_list_view);
        listView.setAdapter(mProFeedAdapter);
        mProjectDatabase = FirebaseDatabase.getInstance();
        mProjectDatabaseRefernce = mProjectDatabase.getReference().child("project");

        Project project = new Project("1","1",
                "Video Indexing","Deep Learning",
                "2017-03-08",
                "2017-05-09",
                "Machine Learning",
                "https://en.wikipedia.org/wiki/Gravity");
        mProjectDatabaseRefernce.push().setValue(project);

        mProFeedEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project pD = dataSnapshot.getValue(Project.class);
                mProFeedAdapter.add(pD);
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mProFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Project pD = dataSnapshot.getValue(Project.class);
                for(int i=0; i<projectData.size(); i++){
                    if(projectData.get(i).getpID().equals(pD.getpID())) {
                        projectData.remove(i);
                        break;
                    }
                }
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Project p = projectData.get(pos);
                Intent intent = new Intent(getBaseContext(), ProjectActivity.class);
                intent.putExtra("pID", p.getpID());
                intent.putExtra("pName", p.getpName());
                intent.putExtra("pCreator", p.getuID());
                intent.putExtra("pDateStarts", p.getpDateStarts());
                intent.putExtra("pDateEnds", p.getpDateEnds());
                intent.putExtra("pDesc", p.getpDesc());
                intent.putExtra("pDomain", p.getpDomain());
                intent.putExtra("pLink", p.getpLink());
                startActivity(intent);
            }
        });
    }


}
