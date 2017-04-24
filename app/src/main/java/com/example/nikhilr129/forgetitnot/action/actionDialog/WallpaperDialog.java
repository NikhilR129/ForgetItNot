package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;
import com.example.nikhilr129.forgetitnot.action.ActionSelectionActivity;
import com.example.nikhilr129.forgetitnot.main.MainActivity;


/**
 * Created by root on 13/4/17.
 */
public  class WallpaperDialog {
    private ActionSelectionActivity context;
    private View viewRoot;
    private Action action;
    private int MY_PERMISSION_REQUEST_READ_STORAGE=40;
    private ActionAdapter adapter;
    private static final int SELECT_PHOTO = 0;
    public WallpaperDialog (ActionSelectionActivity context, Action action, ActionAdapter adapter) {
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
        viewRoot = inflater.inflate(R.layout.wallpaper_dialog_layout, null);
        Button select = (Button) viewRoot.findViewById(R.id.action_wallpaper_select);
        Button remove = (Button) viewRoot.findViewById(R.id.action_wallpaper_remove);
        final ImageView imageView = (ImageView) viewRoot.findViewById(R.id.action_wallpaper_imageview);
        builder.setView(viewRoot).
                setTitle("Choose Wallpaper")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.dismiss();
                        if(imageView.getDrawable() == null) {
                            action.setSelected();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "Please select image", Toast.LENGTH_SHORT).show();
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
                });
        final AlertDialog dialog = builder.create();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    (context).startActivityForResult(pickPhoto , 0);//one can be replaced with any action code
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Snackbar.make(viewRoot,
                                "Needs Contacts read external storage permission",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_READ_STORAGE);
                                    }
                                }).show();
                    }else{
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_READ_STORAGE);
                    }
                }

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageDrawable(null);
            }
        });
        return dialog;
    }
}