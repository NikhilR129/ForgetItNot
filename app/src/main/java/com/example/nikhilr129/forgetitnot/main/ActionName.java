package com.example.nikhilr129.forgetitnot.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.event.EventSelectionActivity;

/**
 * Created by root on 12/4/17.
 */

public  class ActionName {
    private Context context;
    public ActionName(Context context) {
        this.context = context;

    }

    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View viewRoot = inflater.inflate(R.layout.task_name, null);
        final EditText editText = (EditText) viewRoot.findViewById(R.id.main_task_name);
        builder.setView(viewRoot)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(editText.getText().toString().length() > 0) {
                            Intent intent=new Intent(context, EventSelectionActivity.class);
                            intent.putExtra("TITLE", editText.getText().toString());
                            context.startActivity(intent);
                        }
                        }
                    })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent e) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                });;
        ;
        AlertDialog dialog = builder.create();
        return dialog;
    }
}