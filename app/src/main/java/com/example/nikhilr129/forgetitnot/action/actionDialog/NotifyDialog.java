package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.nikhilr129.forgetitnot.R;

/**
 * Created by root on 12/4/17.
 */

public  class NotifyDialog {
    private Context context;

    public NotifyDialog(Context context) {
        this.context = context;
    }

    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View viewRoot = inflater.inflate(R.layout.notify_dialog_layout, null);
        builder.setView(viewRoot)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        EditText editText = (EditText) viewRoot.findViewById(R.id.action_nofify_editText);
                        Toast.makeText(context, editText.getText(), Toast.LENGTH_SHORT).show();
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