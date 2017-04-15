package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 12/4/17.
 */


public  class SpeakerDialog {
    private Context context;
    public SpeakerDialog(Context context) {
        this.context = context;
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
                        for(int i = 0; i < seletedItems.size(); ++i) {
                            Toast.makeText(context, seletedItems.get(i).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}