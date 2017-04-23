package com.example.nikhilr129.forgetitnot.event.eventDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.event.Event;
import com.example.nikhilr129.forgetitnot.event.EventAdapter;

import static android.R.attr.data;

/**
 * Created by kanchicoder on 4/16/2017.
 */

public class TimeDialog  {   
    private boolean already_checked = false;
    private View viewRoot;
    private  Context context;
    private  Event event;
    private  EventAdapter adapter;
    public TimeDialog(Context context, Event event, EventAdapter adapter) {
        this.context = context;
        this.event = event;
        this.adapter = adapter;
    }
    public  AlertDialog create () {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        viewRoot = inflater.inflate(R.layout.time_picker_dialog, null);
        TimePicker tp=(TimePicker)viewRoot.findViewById(R.id.timePicker);
        final CheckBox ed=(CheckBox)viewRoot.findViewById(R.id.everyday),mon=(CheckBox)viewRoot.findViewById(R.id.mon),
                tue=(CheckBox)viewRoot.findViewById(R.id.tue),wed=(CheckBox)viewRoot.findViewById(R.id.wed),
                thurs=(CheckBox)viewRoot.findViewById(R.id.thurs),fri=(CheckBox)viewRoot.findViewById(R.id.fri),
                sat=(CheckBox)viewRoot.findViewById(R.id.sat),sun=(CheckBox)viewRoot.findViewById(R.id.sun);
        builder.setView(viewRoot).
                setTitle("Select")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        int[] tmp=new int[7];
                        if(mon.isChecked())
                            tmp[0]=1;
                        if(tue.isChecked())
                            tmp[1]=1;
                        if(wed.isChecked())
                            tmp[2]=1;
                        if(thurs.isChecked())
                            tmp[3]=1;
                        if(fri.isChecked())
                            tmp[4]=1;
                        if(sat.isChecked())
                            tmp[5]=1;
                        if(sun.isChecked())
                            tmp[6]=1;
                        adapter.data[0][1]="";
                        for(int i=0;i<7;i++){
                            adapter.data[0][1]+=Integer.toString(tmp[i]);
                        }
                        if(!(mon.isChecked() || tue.isChecked() || wed.isChecked() || thurs.isChecked() ||fri.isChecked() || sat.isChecked() || sun.isChecked())) {
                            Toast.makeText(context, "Please select atleast one day", Toast.LENGTH_SHORT).show();
                            event.setSelected();
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        event.setSelected();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent e) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            event.setSelected();
                            adapter.notifyDataSetChanged();
                            return true;
                        }
                        return false;
                    }
                });;

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
                    mon.setClickable(true);
                    tue.setClickable(true);
                    wed.setClickable(true);
                    thurs.setClickable(true);
                    fri.setClickable(true);
                    sat.setClickable(true);
                    sun.setClickable(true);
                    adapter.data[0][1]="0000000";
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
                    mon.setClickable(false);
                    tue.setClickable(false);
                    wed.setClickable(false);
                    thurs.setClickable(false);
                    fri.setClickable(false);
                    sat.setClickable(false);
                    sun.setClickable(false);
                    adapter.data[0][1]="1111111";
                }

            }
        });
        final AlertDialog dialog = builder.create();
        return dialog;
    }
}
