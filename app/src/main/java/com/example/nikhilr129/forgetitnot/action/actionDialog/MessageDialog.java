package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;
import com.example.nikhilr129.forgetitnot.action.ActionSelectionActivity;
import com.example.nikhilr129.forgetitnot.main.MainActivity;

/**
 * Created by root on 12/4/17.
 */

public  class MessageDialog {
    private ActionSelectionActivity context;
    private Action action;
    private ActionAdapter adapter;
    private View viewRoot;
    private int PICK_CONTACT = 1;
    private int MY_PERMISSION_REQUEST_READ_CONTACTS=20, MY_PERMISSION_REQUEST_SEND_SMS=30;
    public MessageDialog (ActionSelectionActivity context, Action action, ActionAdapter adapter) {
        this.context = context;
        this.action = action;
        this.adapter = adapter;
    }
    public View getView() {
        return viewRoot;
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        viewRoot = inflater.inflate(R.layout.message_dialog_layout, null);
        Button select = (Button) viewRoot.findViewById(R.id.event_call_dialog_select);
        Button remove = (Button) viewRoot.findViewById(R.id.event_call_dialog_remove);
        final TextView textView = (TextView) viewRoot.findViewById(R.id.event_call_dialog_textView);
        final EditText editText=(EditText)viewRoot.findViewById(R.id.action_message_editText);

        builder.setView(viewRoot).
                setTitle("Fill Details")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        adapter.data[1][1]=editText.getText().toString();
                        if(textView.getText().length() == 0 && editText.getText().length() == 0) {
                            action.setSelected();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
        final AlertDialog dialog = builder.create();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    intent.setType(Phone.CONTENT_TYPE);  //should filter only contacts with phone numbers
                    (context).startActivityForResult(intent, PICK_CONTACT);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
                        Snackbar.make(viewRoot,
                                "Needs Contacts read and send SMS permission",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSION_REQUEST_READ_CONTACTS);
                                        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SEND_SMS);
                                    }
                                }).show();
                    }else{
                        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSION_REQUEST_READ_CONTACTS);
                        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SEND_SMS);
                    }
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
