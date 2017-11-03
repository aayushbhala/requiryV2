package com.example.android.requiryv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContributorsActivity extends AppCompatActivity {
    private DatabaseReference contributorDbRef;
    private ListView listView;
    private ContributorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Contributors");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
        setContentView(R.layout.activity_contributors);

        listView = (ListView) findViewById(R.id.contributor_list_view);
        ArrayList<Contributer> arrayList = new ArrayList<>();
        mAdapter = new ContributorAdapter(this,arrayList,getIntent().getStringExtra("pID"));
        listView.setAdapter(mAdapter);

        contributorDbRef = FirebaseDatabase.getInstance().getReference().child("contributor");
        contributorDbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Contributer contributer = dataSnapshot.getValue(Contributer.class);
                mAdapter.add(contributer);
                Log.e("Contributor 12", contributer.getpId() + " " + contributer.getuId());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContributorsActivity.this,ProfileActivity.class);
                intent.putExtra("uID",mAdapter.getItem(i).getuId());
                startActivity(intent);
            }
        });
    }

}
