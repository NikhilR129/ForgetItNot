package com.example.nikhilr129.forgetitnot.action.actionDialog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;

/**
 * Created by kanchicoder on 12/4/17.
 */

public  class ProfileDialog {
    private Context context;
    private Action action;
    private ActionAdapter adapter;
   public ProfileDialog(Context context, Action action, ActionAdapter adapter) {
       this.context = context;
       this.action = action;
       this.adapter = adapter;
   }
   public AlertDialog create() {
       AlertDialog.Builder builder = new AlertDialog.Builder(context);
       // Set the dialog title
       builder.setTitle("Choose Profile")
               // Specify the list array, the items to be selected by default (null for none),
               // and the listener through which to receive callbacks when items are selected
               .setSingleChoiceItems(R.array.profiles, 0, null)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int whichButton) {
                       dialog.dismiss();
                       int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                       Toast.makeText(context, String.valueOf(selectedPosition), Toast.LENGTH_SHORT).show();
                       adapter.data[0][0]=String.valueOf(selectedPosition);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
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
               });;

       AlertDialog dialog = builder.create();
       return dialog;
   }
}
