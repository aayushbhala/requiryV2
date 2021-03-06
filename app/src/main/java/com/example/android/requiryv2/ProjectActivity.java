package com.example.android.requiryv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
    TextView pCreatorTV, pDomainTV, pStartDateTV, pEndDateTV, pDescriptionTV, pLinkTV;
    Button pApplyButton, pDiscussionButton, pContributorsButton;

    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    Project project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        pCreatorTV = (TextView) findViewById(R.id.pCreator_textView);
        pDomainTV = (TextView) findViewById(R.id.pDomain_textView);
        pStartDateTV = (TextView) findViewById(R.id.pStarted_textView);
        pEndDateTV = (TextView) findViewById(R.id.pETC_textView);
        pDescriptionTV = (TextView) findViewById(R.id.pDesc_textView);
        pLinkTV = (TextView) findViewById(R.id.pLinks_textView);
        pApplyButton = (Button) findViewById(R.id.apply_button);
        pDiscussionButton = (Button) findViewById(R.id.discuss_button);
        pContributorsButton = (Button) findViewById(R.id.contributors_button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.search_member_fab);
        fab.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        project = (Project) i.getSerializableExtra("project_data");

        setTitle(project.getpName());

        pId = project.getpID();
        pCreatorId = project.getuID();
        String creator = "";
        if(ProFeedActivity.requiryUserMap.containsKey(project.getuID()))
            creator = ProFeedActivity.requiryUserMap.get(project.getuID()).getuName();
        pCreatorTV.setText(creator);
        pDomainTV.setText(project.getpDomain());
        pStartDateTV.setText(project.getpDateStarts());
        pEndDateTV.setText(project.getpDateEnds());
        pDescriptionTV.setText(project.getpDesc());
        pLinkTV.setText(project.getpLink());
        pLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("" + pLinkTV.getText()));
                startActivity(intent);
            }
        });


        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        uId = sp.getString("uID", "");

        if(uId.equals(pCreatorId)){
            pApplyButton.setText("Delete");
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProjectActivity.this,AddUserActivity.class);
                    intent.putExtra("pID",pId);
                    startActivity(intent);
                }
            });
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

                    finish();
                }
            });
        }
        else{
            pApplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msg = ProFeedActivity.requiryUserMap.get(uId).getuName()+" sent application for "+project.getpName();
                    Applications a = new Applications(pId, uId, project.getuID(), msg, project.getpName());
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
                intent.putExtra("project_data", project);
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