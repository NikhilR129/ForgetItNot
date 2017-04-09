package com.example.nikhilr129.forgetitnot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class SelectEventConditionAction extends AppCompatActivity{
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_condition_action);
        //Toolbar support in android
        setToolbar();

    }
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.event_conditon_action_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.iconsTint));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.iconsTint));
    }
}
