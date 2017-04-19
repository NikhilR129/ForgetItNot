package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;

/**
 * Created by root on 12/4/17.
 */

public  class MessageDialog {
    private Context context;
    private Action action;
    private ActionAdapter adapter;
    private View viewRoot;
    private int PICK_CONTACT = 1;
    public MessageDialog (Context context, Action action, ActionAdapter adapter) {
        this.context = context;
        this.action = action;
        this.adapter = adapter;
    }
    public View getView() {
        return viewRoot;
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        viewRoot = inflater.inflate(R.layout.message_dialog_layout, null);
        Button select = (Button) viewRoot.findViewById(R.id.event_call_dialog_select);
        Button remove = (Button) viewRoot.findViewById(R.id.event_call_dialog_remove);
        final TextView textView = (TextView) viewRoot.findViewById(R.id.event_call_dialog_textView);

        builder.setView(viewRoot).
                setTitle("Fill Details")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
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
        final AlertDialog dialog = builder.create();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(Phone.CONTENT_TYPE);  //should filter only contacts with phone numbers
                ((Activity)context).startActivityForResult(intent, PICK_CONTACT);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    action.setSelected();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(null);
            }
        });
        return dialog;
    }
}
