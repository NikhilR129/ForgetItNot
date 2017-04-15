package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;

/**
 * Created by root on 12/4/17.
 */

public  class MessageDialog {
    private Context context;
    public MessageDialog(Context context) {
        this.context = context;
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set the dialog title
        builder.setTitle("Choose message")
                .setSingleChoiceItems(R.array.profiles, 0, null)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        Toast.makeText(context, String.valueOf(selectedPosition), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
