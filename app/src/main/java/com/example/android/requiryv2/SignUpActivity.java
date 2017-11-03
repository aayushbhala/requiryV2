package com.example.android.requiryv2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mNumberEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mDescEditText;
    private EditText mEmailEditText;
    private Button mRegisterButton;
    private RadioGroup radioGroup;
    private boolean flag;
    private Bundle myBundle;
    private String number;
    private String name;
    private String email;
    private String username;
    private String password;
    private String desc;
    private String who;
    private DatabaseReference mRequiryDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.action_bar, null);
        TextView actionbar_title = (TextView)v.findViewById(R.id.action_bar_title);
        actionbar_title.setText("SignUp");
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
        flag = false;
        mRegisterButton = (Button) findViewById(R.id.registerButton);
        mNameEditText = (EditText) findViewById(R.id.input_nameEditText);
        mNumberEditText = (EditText) findViewById(R.id.input_numberEditText);
        mUsernameEditText = (EditText) findViewById(R.id.input_usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.input_passwordEditText);
        mDescEditText = (EditText) findViewById(R.id.input_userdesc);
        radioGroup = (RadioGroup) findViewById(R.id.whoradiogroup);
        mEmailEditText = (EditText) findViewById(R.id.input_emailEditText);
        Intent intent = getIntent();
        myBundle = intent.getExtras();
        if(myBundle!=null){
            mRegisterButton.setText("Save");
            this.setTitle("Edit Profile");
            mNameEditText.setText(myBundle.getString("uName"));
            mNumberEditText.setText(myBundle.getString("uNumber"));
            mUsernameEditText.setText(myBundle.getString("uUsername"));
            mPasswordEditText.setText(myBundle.getString("uPassword"));
            mDescEditText.setText(myBundle.getString("uDesc"));
            int who = Integer.parseInt(myBundle.getString("uWho"));
            if(who==1){
                radioGroup.check(R.id.faculty_radiobutton);
            }
            else
                radioGroup.check(R.id.student_radiobutton);
            mEmailEditText.setText(myBundle.getString("uEmail"));
            flag = true;
        }
        mRequiryDatabaseRef = FirebaseDatabase.getInstance().getReference().child("requiry_user");
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag)
                    Toast.makeText(SignUpActivity.this, "Feature comming soon", Toast.LENGTH_SHORT).show();
                else
                    registerUser();
            }
        });
    }

    public void registerUser(){
        name = mNameEditText.getText().toString().trim();
        if(!TextUtils.isEmpty(mNumberEditText.getText().toString().trim()))
            number  = mNumberEditText.getText().toString();
        email = mEmailEditText.getText().toString().trim();
        username = mUsernameEditText.getText().toString().trim();
        password = mPasswordEditText.getText().toString().trim();
        desc = mDescEditText.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            mNameEditText.requestFocus();
            mNameEditText.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(email)){
            mEmailEditText.requestFocus();
            mEmailEditText.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(username)){
            mUsernameEditText.requestFocus();
            mUsernameEditText.setError("This field is required");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mPasswordEditText.requestFocus();
            mPasswordEditText.setError("This field is required");
            return;
        }
        /**
         * Radio Group
         * if the user is Student then in database it is stored as 0
         * if the user is Faculty then in database it is stored as 1
         */

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId==R.id.student_radiobutton)
            who = "0";
        else
            who="1";

        DatabaseReference newRef = mRequiryDatabaseRef.push();
        RequiryUser ru = new RequiryUser(newRef.getKey(),name,number,email,username,who,desc,password);
        newRef.setValue(ru);
        //TODO Add shared Preference here on success
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("uID", ru.getuID());
        editor.putString("uName", ru.getuName());
        editor.putString("uUsername", ru.getuUsername());
        editor.putString("uPassword", ru.getuPassword());
        editor.putString("uEmail", ru.getuEmail());
        editor.putString("uNumber", ru.getuNumber());
        editor.putString("uWho", ru.getuWho());
        editor.putString("uDes", ru.getuDesc());
        editor.commit();
        Intent intent  = new Intent(SignUpActivity.this, ProFeedActivity.class);
        startActivity(intent);
        finish();

    }
}
