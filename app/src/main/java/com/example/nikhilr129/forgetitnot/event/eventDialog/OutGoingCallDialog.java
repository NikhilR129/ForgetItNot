package com.example.nikhilr129.forgetitnot.event.eventDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.event.Event;
import com.example.nikhilr129.forgetitnot.event.EventAdapter;

/**
 * Created by kanchicoder on 4/16/2017.
 */

public class OutGoingCallDialog {
    private  Context context;
    private  Event event;
    private  EventAdapter adapter;
    private View viewRoot;
    private int PICK_CONTACT = 2;
    public OutGoingCallDialog (Context context, Event event, EventAdapter adapter) {
        this.context = context;
        this.event = event;
        this.adapter = adapter;
    }
    public View getView() {
        return viewRoot;
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        viewRoot = inflater.inflate(R.layout.call_dialog, null);
        Button select = (Button) viewRoot.findViewById(R.id.event_call_dialog_select);
        Button remove = (Button) viewRoot.findViewById(R.id.event_call_dialog_remove);
        final TextView textView = (TextView) viewRoot.findViewById(R.id.event_call_dialog_textView);
        builder.setView(viewRoot).
                setTitle("Pick Contact")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(textView.getText().length() == 0) {
                            event.setSelected();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "Please pick contact", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        event.setSelected();
                        adapter.notifyDataSetChanged();

                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent e) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            event.setSelected();
                            adapter.notifyDataSetChanged();
                            return true;
                        }
                        return false;
                    }
                });
        final AlertDialog dialog = builder.create();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);  //should filter only contacts with phone numbers
                ((Activity)context).startActivityForResult(intent, PICK_CONTACT);
                if (dialog.isShowing()) {
                    dialog.dismiss();
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
