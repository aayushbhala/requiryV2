package com.example.android.requiryv2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends Activity {

    EditText username, password;
    Button signInButton, signUpButton;

    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    boolean flag = false;
    RequiryUser ru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.sign_in_button);
        signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEntry()){
                    final DatabaseReference userRef = mDatabaseReference.child("requiry_user");
                    userRef.orderByChild("uUsername").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                if(ds.getValue(RequiryUser.class).getuUsername().equals(username.getText().toString())){
                                    if(ds.getValue(RequiryUser.class).getuPassword().equals(password.getText().toString())){
                                        flag = true;
                                        ru = ds.getValue(RequiryUser.class);
                                    }
                                    else{
                                        password.requestFocus();
                                        password.setError("Wrong Password");
                                    }
                                    return;
                                }
                            }
                            username.requestFocus();
                            username.setError("Wrong Username");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });

                    if(flag){
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

                        Intent intent = new Intent(getBaseContext(), SplashScreen.class);
                        startActivity(intent);
                    }
                }

            }
        });
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        if(sp.getString("uUsername","").length() != 0){
            Intent nextScreen = new Intent(SignInActivity.this, SplashScreen.class);
            startActivity(nextScreen);
        }

    }

    public boolean validateEntry(){
        if(username.getText().toString().length() == 0){
            username.requestFocus();
            username.setError("Cannot be empty");
            return false;
        }
        if(password.getText().toString().length() == 0) {
            password.requestFocus();
            password.setError("Cannot be empty");
            return false;
        }
        return true;
    }
}
