package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;

/**
 * Created by root on 12/4/17.
 */

public  class VolumeDialog {
    private Context context;
    public VolumeDialog(Context context) {
        this.context = context;
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
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        ;
        AlertDialog dialog = builder.create();
        return dialog;
    }
}