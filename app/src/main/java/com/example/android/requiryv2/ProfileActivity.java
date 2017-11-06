package com.example.android.requiryv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Log.e("Profilr Act",""+uId);

        requiryUser = ProFeedActivity.requiryUserMap.get(uId);
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        if(!sp.getString("uID","").equals(requiryUser.getuID())){
            deleteProfile.setVisibility(View.INVISIBLE);
        }
        uProfilePic.setText(""+requiryUser.getuName().charAt(0));
        uName.setText(requiryUser.getuName());
        uWho.setText(requiryUser.getuWho().equals("1") ? "Faculty" :  "Student");
        uDesc.setText(requiryUser.getuDesc());
        uEmail.setText(requiryUser.getuEmail());
        uNumber.setText(requiryUser.getuNumber());
        uUserName.setText(requiryUser.getuUsername());

        uNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+requiryUser.getuNumber()));
                startActivity(intent);

            }
        });
        uEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{requiryUser.getuEmail()});
                startActivity(i);
            }
        });

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
                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
