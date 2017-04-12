package com.example.nikhilr129.forgetitnot.Intro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.view.WindowManager;

import com.example.nikhilr129.forgetitnot.main.MainActivity;
import com.example.nikhilr129.forgetitnot.R;
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
        if(Build.VERSION.SDK_INT >= 21)
            setTransition();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setTransition() {
        TransitionInflater tf = TransitionInflater.from(this);
        Transition t = tf.inflateTransition(R.transition.fadetransition);       ;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setExitTransition(t);
            }
    }

    private void initSlides() {
        addSlide(SampleSlideForAppIntro.newInstance(R.layout.custom_intro_slide));
        addSlide(AppIntroFragment.newInstance("Create Automation\n Rules & Save Time", "Each Rule has "+
                "3 Components:\nCondition, Condition and Action\n\nCondition(Triggered) -> Check Condition\nCondition(Satisfied) -> Execute Action", R.drawable.custom_slide_2, Color.parseColor("#673AB7")));
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
        ActivityOptionsCompat compat = setActivityAnimation();
        startActivity(new Intent(MainIntroActivity.this, MainActivity.class), compat.toBundle());
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
     //   super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        ActivityOptionsCompat compat = setActivityAnimation();
        startActivity(new Intent(MainIntroActivity.this, MainActivity.class), compat.toBundle());
        finish();
    }
    private ActivityOptionsCompat setActivityAnimation() {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainIntroActivity.this, null);
        return compat;
    }
}