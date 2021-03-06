package com.example.android.requiryv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;

public class DiscussionsActivity extends AppCompatActivity {

    ListView lv;
    EditText acceptMessage;
    Button send;

    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    Project p;
    DiscussionsAdapter mAdapter;
    ArrayList<Discussions> mList;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussions);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.empty_view_discussion);
        lv = (ListView) findViewById(R.id.discussions_listview);
        lv.setEmptyView(linearLayout);
        acceptMessage = (EditText) findViewById(R.id.message_accept);
        send = (Button) findViewById(R.id.send_button);

        Intent intent = getIntent();
        p = (Project) intent.getSerializableExtra("project_data");

        sp = getSharedPreferences("User", MODE_PRIVATE);

        mList = new ArrayList<>();
        mAdapter = new DiscussionsAdapter(this, mList);

        lv.setAdapter(mAdapter);

        mDatabaseReference.child("discussions").orderByChild("pID").equalTo(p.getpID()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Discussions d = dataSnapshot.getValue(Discussions.class);
                mAdapter.add(d);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(acceptMessage.getText().toString().length() == 0){
                    acceptMessage.requestFocus();
                    acceptMessage.setError("Cannot be empty");
                }
                else{
                    DatabaseReference newRef = mDatabaseReference.child("discussions").push();
                    Discussions d = new Discussions();
                    d.setMsg(acceptMessage.getText().toString());
                    d.setpID(p.getpID());
                    d.setuID(sp.getString("uID", ""));
                    d.setpName(p.getpName());
                    d.setuUserName(sp.getString("uUsername", ""));
                    String date = Calendar.getInstance().getTime().toString();
                    d.setTimestamp(date);
                    newRef.setValue(d);
                    acceptMessage.setText("");
                    FirebaseMessaging.getInstance().subscribeToTopic("user_" + p.getpID());
                }
            }
        });

    }
}
