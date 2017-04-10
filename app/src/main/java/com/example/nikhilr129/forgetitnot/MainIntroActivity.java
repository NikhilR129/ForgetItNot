package com.example.nikhilr129.forgetitnot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class MainIntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        makeFullScreen();
        super.onCreate(savedInstanceState);
        initSlides();
    }

    private void initSlides() {
        addSlide(SampleSlide.newInstance(R.layout.custom_intro_slide));
        addSlide(AppIntroFragment.newInstance("Create Automation\n Rules & Save Time", "Each Rule has "+
                "3 Components:\nEvent, Condition and Action\n\nEvent(Triggered) -> Check Condition\nCondition(Satisfied) -> Execute Action", R.drawable.custom_slide_2, Color.parseColor("#673AB7")));
        setFlowAnimation();
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
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        //super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startActivity(new Intent(MainIntroActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
     //   super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(MainIntroActivity.this, MainActivity.class));
        finish();

    }
}