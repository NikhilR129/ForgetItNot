package com.example.nikhilr129.forgetitnot.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.nikhilr129.forgetitnot.Intro.MainIntroActivity;
import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.main.MainActivity;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;


public class SplashScreen extends AppCompatActivity {
    private HTextView hTextView;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make app fullscreen
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
        //getSupportActionBar().hide();
    }

    private void doTransitionToMainActivity() {
        //Things for transition
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart) {
                    //  Launch app intro
                    Intent i = new Intent(SplashScreen.this, MainIntroActivity.class);
                    startActivity(i);
                    finish();
                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();
                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
<<<<<<< HEAD
               }
=======
                }
>>>>>>> 0549d490afaa902c6e50563ecef3f1da54ab609a
                else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                  finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}