package com.example.nikhilr129.forgetitnot.Intro;

import android.Manifest;
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

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.main.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

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
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG,
                        Manifest.permission.ADD_VOICEMAIL,
                        Manifest.permission.USE_SIP,
                        Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.BODY_SENSORS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_WAP_PUSH,
                        Manifest.permission.RECEIVE_MMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {ActivityOptionsCompat compat = setActivityAnimation();
                startActivity(new Intent(MainIntroActivity.this, MainActivity.class), compat.toBundle());
                finish();}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG,
                        Manifest.permission.ADD_VOICEMAIL,
                        Manifest.permission.USE_SIP,
                        Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.BODY_SENSORS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_WAP_PUSH,
                        Manifest.permission.RECEIVE_MMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {ActivityOptionsCompat compat = setActivityAnimation();
                startActivity(new Intent(MainIntroActivity.this, MainActivity.class), compat.toBundle());
                finish();}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
    private ActivityOptionsCompat setActivityAnimation() {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainIntroActivity.this, null);
        return compat;
    }
}