package com.example.android.requiryv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private TextView msplashTextView;
    private ImageView imageView;
    private static int SPLASH_TIME_OUT = 2000;
    private Project project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        msplashTextView = (TextView) findViewById(R.id.splash_tv);
        imageView = (ImageView) findViewById(R.id.imgLogo);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        msplashTextView.startAnimation(anim);
        imageView.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, ProFeedActivity.class);
                //i.putExtra("profeed_data",project);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
