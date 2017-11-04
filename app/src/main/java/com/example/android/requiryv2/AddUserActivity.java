package com.example.android.requiryv2;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddUserActivity extends AppCompatActivity {
    private ArrayList<String> userList;
    private ListView listView;
    private SearchAdapter searchAdapter;
    private  EditText searchET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("Add contributor");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
        searchET = (EditText) findViewById(R.id.actionbar_edit_text);
        userList = new ArrayList<>(ProFeedActivity.requiryUserMap.keySet());
        handleIntent();
    }
    private void handleIntent() {

            listView = (ListView) findViewById(R.id.search_list_view);
            //listView.setVisibility(View.INVISIBLE);
            //TODO Add this
            //RelativeLayout layout = (RelativeLayout) findViewById(R.id.emptyView);
            //listView.setEmptyView(layout);
            searchAdapter = new SearchAdapter(AddUserActivity.this,new ArrayList<String>(),userList);
            listView.setAdapter(searchAdapter);
            searchET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //listView.setVisibility(View.VISIBLE);
                    searchAdapter.filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    DatabaseReference contridbRef = FirebaseDatabase.getInstance().getReference().child("contributor");
                    Contributer user = new Contributer(getIntent().getStringExtra("pID"),searchAdapter.getItem(i));
                    contridbRef.push().setValue(user);
                    String name = ProFeedActivity.requiryUserMap.get(user.getuId()).getuName();
                    Toast.makeText(AddUserActivity.this,name +" added to your project",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


    }
}
