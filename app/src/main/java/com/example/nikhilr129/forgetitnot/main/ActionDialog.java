package com.example.nikhilr129.forgetitnot.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nikhilr129.forgetitnot.R;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * Created by root on 12/4/17.
 */

public  class ActionDialog {
    private Context context;
    private  Task task;
    public ActionDialog(Context context, Task task) {
        this.context = context;
        this.task = task;
    }
    private  void setAttributes(String type, String a[], LinearLayout layout) {
        TextView t1, t2, t3;
        switch(type) {
            case "Profile":
                t1= new TextView(context);
                t1.setText("  " + context.getResources().getStringArray(R.array.profiles)[Integer.parseInt(a[0])]);
                layout.addView(t1);
                break;
            case "Message":
                t1 = new TextView(context);
                t1.setText(" Mob No: " + a[0] + "\n");
                layout.addView(t1);
                t2 = new TextView(context);
                t2.setText(" Message:  " + a[1]);
                layout.addView(t2);
                break;
            case "Wifi":
                t1= new TextView(context);
                t1.setText("  " + context.getResources().getStringArray(R.array.wifi)[Integer.parseInt(a[0])]);
                layout.addView(t1);
                break;
            case "Notify":
                t1 = new TextView(context);
                t1.setText(" Title : " + a[0] + "\n");
                layout.addView(t1);
                t2 = new TextView(context);
                t2.setText(" Description :" + a[1]);
                layout.addView(t2);
                break;
            case "Load App":
                final String packageName = a[0];
                PackageManager packageManager= context.getApplicationContext().getPackageManager();
                String appName = null;
                try {
                    appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                t1 = new TextView(context);
                t1.setText(" AppName : " + appName);
                layout.addView(t1);
                break;
            case "Speakerphone":
                t1 = new TextView(context);
                t1.setText("  " + "On");
                layout.addView(t1);
                break;
            case "Volume":
                t1 = new TextView(context);
                t1.setText(" Media " + Integer.parseInt(a[0])*10 + "%\n");
                layout.addView(t1);
                t1 = new TextView(context);
                t1.setText(" Alarm " + Integer.parseInt(a[1])*10  + "%\n");
                layout.addView(t1);
                t1 = new TextView(context);
                t1.setText(" Ring " + Integer.parseInt(a[2])*10  + "%");
                layout.addView(t1);
                break;
            case "Wallpaper":
                ImageView img = new ImageView(context);
                Bitmap bitmap = null;
                img.setImageBitmap(bitmap);
                layout.addView(img);
                img.requestLayout();
                img.getLayoutParams().height = 150;
                img.getLayoutParams().width = 120;
                break;
            default:
                break;
        }
    }
    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View viewRoot = inflater.inflate(R.layout.main_action, null);
        LinearLayout linearLayout = (LinearLayout) viewRoot.findViewById(R.id.main_action_linear_layout) ;
        Realm.init(context);
        Realm realm=Realm.getDefaultInstance();
        RealmResults<com.example.nikhilr129.forgetitnot.Models.Task> rl=
                realm.where(com.example.nikhilr129.forgetitnot.Models.Task.class).equalTo("id",task.getId()).findAll();
             RealmList<com.example.nikhilr129.forgetitnot.Models.Action> actions=rl.get(0).actions;

        for(int x=0;x<actions.size();x++) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layout.setPadding(8, 8, 8, 8);
            com.example.nikhilr129.forgetitnot.Models.Action action=actions.get(x);
            String type=action.type;
            String a[] = new String[4];
            a[0]=action.a0;
            a[1]=action.a1;
            a[2]=action.a2;
            a[3]=action.a3;
            TextView title = new TextView(context);
            title.setText("   " + type);
            title.setTextSize(16);
            title.setTypeface(null, Typeface.BOLD);
            layout.addView(title);
            setAttributes(type, a, layout);
            linearLayout.addView(layout);
        }
        builder.setView(viewRoot)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        }
                    });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}