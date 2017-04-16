package com.example.nikhilr129.forgetitnot.event.eventDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import com.example.nikhilr129.forgetitnot.R;

/**
 * Created by kanchicoder on 4/16/2017.
 */

public class IncomingCallDialog {
    private Context context;
    private View viewRoot;
    private int PICK_CONTACT = 1;
    public IncomingCallDialog (Context context) {
        this.context = context;
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
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(Phone.CONTENT_TYPE);  //should filter only contacts with phone numbers
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
