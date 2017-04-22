package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;

/**
 * Created by root on 12/4/17.
 */

public  class VolumeDialog {
    private Context context;
    private Action action;
    private ActionAdapter adapter;
    public VolumeDialog(Context context, Action action, ActionAdapter adapter) {
        this.context = context;
        this.action = action;
        this.adapter = adapter;
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View viewRoot = inflater.inflate(R.layout.volume_dialog_layout, null);
        builder.setView(viewRoot).
                setTitle("Choose Volume")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
        //                TextView textView = (TextView)viewRoot.findViewById(R.id.volume_dialog_textview);
        //                Toast.makeText(context, textView.getText(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        SeekBar alarm = (SeekBar) viewRoot.findViewById(R.id.action_alarm_volume);
                        SeekBar media = (SeekBar) viewRoot.findViewById(R.id.action_media_volume);
                        SeekBar ring = (SeekBar) viewRoot.findViewById(R.id.action_ring_volume);
                        int a = alarm.getProgress();
                        int m = media.getProgress();
                        int r = ring.getProgress();
                        Toast.makeText(context, String.valueOf(a) + " " + String.valueOf(m) + " " + String.valueOf(r), Toast.LENGTH_SHORT).show();
                        adapter.data[6][0]=String.valueOf(a);
                        adapter.data[6][1]=String.valueOf(m);
                        adapter.data[6][2]=String.valueOf(r);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                action.setSelected();
                                adapter.notifyDataSetChanged();
                            }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent e) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            action.setSelected();
                            adapter.notifyDataSetChanged();
                            return true;
                        }
                        return false;
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}