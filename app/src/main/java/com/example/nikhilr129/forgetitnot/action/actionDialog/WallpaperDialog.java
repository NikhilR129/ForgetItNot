package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.nikhilr129.forgetitnot.R;



/**
 * Created by root on 13/4/17.
 */
public  class WallpaperDialog {
    private Context context;
    private static final int SELECT_PHOTO = 0;
    public WallpaperDialog (Context context) {
        this.context = context;
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View viewRoot = inflater.inflate(R.layout.wallpaper_dialog_layout, null);
        Button select = (Button) viewRoot.findViewById(R.id.action_wallpaper_select);
        Button remove = (Button) viewRoot.findViewById(R.id.action_wallpaper_remove);
        final ImageView imageView = (ImageView) viewRoot.findViewById(R.id.action_wallpaper_imageview);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
              //  context.startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageDrawable(null);
            }
        });
        builder.setView(viewRoot).
                setTitle("Choose Volume")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        ;
        AlertDialog dialog = builder.create();
        return dialog;
    }
}