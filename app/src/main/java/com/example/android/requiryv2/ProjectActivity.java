package com.example.android.requiryv2;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class ProjectActivity extends AppCompatActivity {

    String pId, uId, pCreatorId;
    TextView pNameTV, pCreatorTV, pDomainTV, pStartDateTV, pEndDateTV, pDescriptionTV, pLinkTV;
    Button pApplyButton, pDiscussionButton, pContributorsButton;

    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        pNameTV = (TextView) findViewById(R.id.p_name_text_view);
        pCreatorTV = (TextView) findViewById(R.id.p_creator_text_view);
        pDomainTV = (TextView) findViewById(R.id.p_domain_text_view);
        pStartDateTV = (TextView) findViewById(R.id.p_start_date_text_view);
        pEndDateTV = (TextView) findViewById(R.id.p_end_date_text_view);
        pDescriptionTV = (TextView) findViewById(R.id.p_description_text_view);
        pLinkTV = (TextView) findViewById(R.id.p_link_text_view);
        pApplyButton = (Button) findViewById(R.id.p_apply_button);
        pDiscussionButton = (Button) findViewById(R.id.p_discussions_button);
        pContributorsButton = (Button) findViewById(R.id.p_contributors_button);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    RequiryUser ru = ds.getValue(RequiryUser.class);
                    pCreatorTV.setText(ru.getuName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Intent i = getIntent();
        final Bundle dataFromProfeed = i.getExtras();
        pId = i.getStringExtra("pID");
        pNameTV.setText(i.getStringExtra("pName"));
        pCreatorId = i.getStringExtra("pCreator");
        pDomainTV.setText(i.getStringExtra("pDomain"));
        pStartDateTV.setText(i.getStringExtra("pDateStarts"));
        pEndDateTV.setText(i.getStringExtra("pDateEnds"));
        pDescriptionTV.setText(i.getStringExtra("pDesc"));
        pLinkTV.setText(i.getStringExtra("pLink"));

        //SharedPreferences sp = getSharedPreferences("USer", MODE_PRIVATE);
        //uId = sp.getString("uId", "");

        mDatabaseReference.child("requiry_user").orderByChild("uID").equalTo(pCreatorId).addValueEventListener(listener);
        mDatabaseReference.removeEventListener(listener);

        uId = "1";

        if(uId.equals(pCreatorId)){
            pApplyButton.setText("Delete");
            pApplyButton.setOnClickListener(new View.OnClickListener() {
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
                    mDatabaseReference.child("project").orderByChild("pID").equalTo(pId).addValueEventListener(l);
                    mDatabaseReference.removeEventListener(l);

                    Toast.makeText(getBaseContext(), "Deleted Project", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), ProFeedActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            pApplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Applications a = new Applications(pId, uId);
                    mDatabaseReference.child("applications").push().setValue(a);

                    Toast.makeText(getBaseContext(), "Application Sent", Toast.LENGTH_SHORT).show();

                    pApplyButton.setClickable(false);
                }
            });
        }

        pDiscussionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DiscussionsActivity.class);
                intent.putExtras(dataFromProfeed);
                startActivity(intent);
            }
        });

        pContributorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ContributorsActivity.class);
                intent.putExtra("pID", pId);
                startActivity(intent);
            }
        });

    }
}
