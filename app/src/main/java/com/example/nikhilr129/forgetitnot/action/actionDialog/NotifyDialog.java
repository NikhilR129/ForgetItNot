package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;

/**
 * Created by root on 12/4/17.
 */

public  class NotifyDialog {
    private Context context;
    private Action action;
    private ActionAdapter adapter;
    public NotifyDialog(Context context, Action action, ActionAdapter adapter) {
        this.context = context;
        this.action = action;
        this.adapter = adapter;
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
                        EditText editTextTitle = (EditText) viewRoot.findViewById(R.id.action_nofify_editText_title);
                        Toast.makeText(context, editText.getText(), Toast.LENGTH_SHORT).show();
                        adapter.data[3][0]=editTextTitle.getText().toString();
                        adapter.data[3][1]=editText.getText().toString();
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
                });;
        ;
        AlertDialog dialog = builder.create();
        return dialog;
    }
}