package com.example.android.requiryv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> userAdapter;
    private ArrayList<RequiryUser> userData;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mRequiryUserDatabaseReference;
    private Button mSubmitButton;
    private Button mProfeedButton;
    private DatabaseReference mProjectRef;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mRequiryUserDatabaseReference = mFirebaseDatabse.getReference().child("requiry_user");
        mProjectRef = mFirebaseDatabse.getReference().child("project");
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RequiryUser requiryUser = new RequiryUser(1, "Aayush Bhala", "9972244005", "aayushbest@gmail.com", "aayushbhala", 1, "Student at MIT Manipal");
                Project project = new Project("3","3"   ,
                        "Gravitational Force","Physics",
                        "2017-03-08",
                        "2017-05-09",
                        "Gravity, or gravitation, is a natural phenomenon b...",
                        "https://en.wikipedia.org/wiki/Gravity");
                mProjectRef.push().setValue(project);
            }
        });
        mProfeedButton = (Button) findViewById(R.id.profeedButton);
        mProfeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intent);
            }
        });
        submitButton = (Button) findViewById(R.id.signup);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
