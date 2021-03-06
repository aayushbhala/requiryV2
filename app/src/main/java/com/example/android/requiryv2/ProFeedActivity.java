package com.example.android.requiryv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by aayuhbhala on 01/11/17.
 */
public class ProFeedActivity extends AppCompatActivity {
    private ChildEventListener mProFeedEventListener;
    private ProFeedAdapter mProFeedAdapter;
    private FirebaseDatabase mProjectDatabase;
    private DatabaseReference mProjectDatabaseRefernce;
    private ListView listView;
    private ArrayList<Project> projectData;
    public static HashMap<String, RequiryUser> requiryUserMap = new HashMap<>(100);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_feed);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProFeedActivity.this, CreateProject.class);
                startActivity(intent);

            }
        });
        projectData = new ArrayList<>();
        mProFeedAdapter = new ProFeedAdapter(this,projectData);
        listView = (ListView) findViewById(R.id.profeed_list_view);
        listView.setAdapter(mProFeedAdapter);
        DatabaseReference requirydatabaseRef = FirebaseDatabase.getInstance().getReference().child("requiry_user");
        requirydatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    RequiryUser ru = dataSnapshot.getValue(RequiryUser.class);
                    requiryUserMap.put(ru.getuID(), ru);
                    Log.e("ProFeed",ru.getuID());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                RequiryUser requiryUser = dataSnapshot.getValue(RequiryUser.class);
                SharedPreferences sp = getSharedPreferences("User",MODE_PRIVATE);
                if(sp.getString("uID","").equals(requiryUser.getuID())){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("uID", "");
                    editor.putString("uName","");
                    editor.putString("uUsername", "");
                    editor.putString("uPassword", "");
                    editor.putString("uEmail", "");
                    editor.putString("uNumber", "");
                    editor.putString("uWho", "");
                    editor.putString("uDes", "");
                    editor.commit();
                    ProFeedActivity.requiryUserMap.remove(sp.getString("uID",""));
                    //TODO Add clean slate code here
                    Toast.makeText(ProFeedActivity.this, "Your account has been deactivated", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(ProFeedActivity.this,SignInActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ProFeedActivity.this, ProjectActivity.class);
                Project project = (Project) adapterView.getAdapter().getItem(i);
                intent.putExtra("project_data", (Serializable) project);
                startActivity(intent);
            }
        });
        SharedPreferences sp = getSharedPreferences("User",MODE_PRIVATE);
        String username = sp.getString("uUsername","");
        FirebaseMessaging.getInstance().subscribeToTopic("user_" + username);

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_item){
            SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
            sp.edit().clear().apply();
            Intent intent = new Intent(getBaseContext(), SignInActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.profile_item){
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
            intent.putExtra("uID", sp.getString("uID", ""));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
