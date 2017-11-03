package com.example.android.requiryv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private String uId;
    private RequiryUser requiryUser;

    private TextView uProfilePic, uName, uUserName, uEmail, uNumber, uWho, uDesc;
    private Button deleteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uProfilePic = (TextView) findViewById(R.id.user_profile_photo);
        uName = (TextView) findViewById(R.id.user_profile_name);
        uUserName = (TextView) findViewById(R.id.user_profile_username);
        uEmail = (TextView) findViewById(R.id.user_profile_email);
        uNumber = (TextView) findViewById(R.id.user_profile_number);
        uDesc = (TextView) findViewById(R.id.user_profile_desc);
        uWho = (TextView) findViewById(R.id.user_profile_who);
        deleteProfile = (Button) findViewById(R.id.deleteButton);

        //Bundle userDetails = getIntent().getExtras();
        uId = getIntent().getStringExtra("uID");

        requiryUser = ProFeedActivity.requiryUserMap.get(uId);
        /*
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    requiryUser = ds.getValue(RequiryUser.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.child("requiry_user").orderByChild("uID").equalTo(uId).addValueEventListener(listener);
        mDatabaseReference.removeEventListener(listener);
        */
        uProfilePic.setText(""+requiryUser.getuName().charAt(0));
        uName.setText(requiryUser.getuName());
        uWho.setText(requiryUser.getuWho().equals("1") ? "Faculty" :  "Student");
        uDesc.setText(requiryUser.getuDesc());
        uEmail.setText(requiryUser.getuEmail());
        uNumber.setText(requiryUser.getuNumber());
        uUserName.setText(requiryUser.getuUsername());

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueEventListener l = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                            ds.getRef().removeValue();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                mDatabaseReference.child("requiry_user").orderByChild("uID").equalTo(uId).addValueEventListener(l);
                mDatabaseReference.removeEventListener(l);

                Toast.makeText(getBaseContext(), "Deleted Profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
