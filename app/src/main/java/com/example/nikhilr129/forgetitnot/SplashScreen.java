package com.example.nikhilr129.forgetitnot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.os.Handler;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;


public class SplashScreen extends AppCompatActivity {
    private HTextView hTextView;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();

        setContentView(R.layout.splash_screen);

        //Thing done for animation
        hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText(getString(R.string.app_name));

        doTransitionToMainActivity();


    }

    private void makeFullScreen() {
        //remove Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // used for making app fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Hide Action Bar
        getSupportActionBar().hide();
    }

    private void doTransitionToMainActivity() {
        //Things for transition
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}