package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;

import java.util.ArrayList;

/**
 * Created by root on 12/4/17.
 */


public  class SpeakerDialog {
    private Context context;
    private Action action;
    private ActionAdapter adapter;
    public SpeakerDialog(Context context, Action action, ActionAdapter adapter) {
        this.context = context;
        this.action = action;
        this.adapter = adapter;
    }

    public AlertDialog create() {
        final CharSequence[] items = {"On"};
        final ArrayList seletedItems=new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Select")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(seletedItems.size() == 0) {
                            action.setSelected();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "Please Select the option", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                        dialog.dismiss();
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