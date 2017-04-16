package com.example.nikhilr129.forgetitnot.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;

public class TimePickerActivity extends AppCompatActivity {

    Intent returnintent;
    boolean already_checked;

    private void init()
    {
        returnintent=new Intent();
        already_checked=false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize instances;
        init();
        setContentView(R.layout.activity_time_picker);
        TimePicker tp=(TimePicker)findViewById(R.id.timePicker);

        final CheckBox ed=(CheckBox)findViewById(R.id.everyday),mon=(CheckBox)findViewById(R.id.mon),
        tue=(CheckBox)findViewById(R.id.tue),wed=(CheckBox)findViewById(R.id.wed),
        thurs=(CheckBox)findViewById(R.id.thurs),fri=(CheckBox)findViewById(R.id.fri),
        sat=(CheckBox)findViewById(R.id.sat),sun=(CheckBox)findViewById(R.id.sun);

        Button cancel=(Button)findViewById(R.id.cancel),done=(Button)findViewById(R.id.done);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED, returnintent);
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(mon.isSelected() || tue.isSelected() || wed.isSelected() || thurs.isSelected() ||fri.isSelected() || sat.isSelected() || sun.isSelected()))
                {
                    Toast.makeText(TimePickerActivity.this, "Please select atleast one day", Toast.LENGTH_SHORT).show();
                    return;
                }
                returnintent.putExtra("hello","world");
                setResult(Activity.RESULT_OK,returnintent);
                finish();
            }
        });

        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(already_checked)
                {
                    mon.setChecked(false);
                    tue.setChecked(false);
                    wed.setChecked(false);
                    thurs.setChecked(false);
                    fri.setChecked(false);
                    sat.setChecked(false);
                    sun.setChecked(false);
                    already_checked=false;
                }
                else
                {
                    mon.setChecked(true);
                    tue.setChecked(true);
                    wed.setChecked(true);
                    thurs.setChecked(true);
                    fri.setChecked(true);
                    sat.setChecked(true);
                    sun.setChecked(true);
                    already_checked=true;
                }
                
            }
        });
    }
}
