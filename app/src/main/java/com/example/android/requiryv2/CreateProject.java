package com.example.android.requiryv2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateProject extends AppCompatActivity {
    private AutoCompleteTextView mName;
    private AutoCompleteTextView mDomain;
    private EditText mLinks;
    private AutoCompleteTextView mDesc;
    private EditText mETC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView) v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Create");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
        //TODO Add uID field from shared Preference
        mName = (AutoCompleteTextView) findViewById(R.id.pName_editText);
        mDomain = (AutoCompleteTextView) findViewById(R.id.pDomain_editText);
        mLinks = (EditText) findViewById(R.id.pLinks_editText);
        mDesc = (AutoCompleteTextView) findViewById(R.id.pDesc_editText);
        mETC = (EditText) findViewById(R.id.pETC_editText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create_button) {
            createProject();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createProject() {
        String name = mName.getText().toString().trim();
        String domain = mDomain.getText().toString().trim();
        String links = mLinks.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        String etc = mETC.getText().toString().trim();

        if (name.length() == 0) {
            mName.setError("Cannot leave empty");
            return;
        }
        if (domain.length() == 0) {
            mDomain.setError("Cannot leave empty");
            return;
        }
        if (desc.length() == 0) {
            mDesc.setError("Cannot leave empty");
            return;
        }
        DatabaseReference mProjectDatbaseRef = FirebaseDatabase.getInstance().getReference().child("project");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dateStarts = dateFormat.format(date);

        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        String uId = sp.getString("uID", "");
        DatabaseReference newRef = mProjectDatbaseRef.push();
        Project project = new Project(newRef.getKey(), uId,name,domain,dateStarts,etc,desc,links);
        newRef.setValue(project);
        finish();
    }
}
