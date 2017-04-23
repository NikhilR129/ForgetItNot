package com.example.nikhilr129.forgetitnot.action.actionDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.Action;
import com.example.nikhilr129.forgetitnot.action.ActionAdapter;
import com.example.nikhilr129.forgetitnot.action.actionDialog.LoadAppSpinner.CustomAdapter;

import java.util.List;

/**
 * Created by kanchicoder on 4/16/2017.
 */
public  class LoadAppDialog {
    Context context;
    String appName[];
    Drawable icon[];
    String packageName[];
    private View viewRoot;
    private Action action;
    private ActionAdapter adapter;
    public  LoadAppDialog(Context context, Action action, ActionAdapter adapter) {
        this.context  = context;
        this.action = action;
        this.adapter = adapter;
        getPackagesDetails();
    }
    public AlertDialog create() {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        viewRoot = inflater.inflate(R.layout.load_app_dialog, null);
        builder.setView(viewRoot).
                setTitle("Select App")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
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
            dialog.onBackPressed();
            Spinner spin = (Spinner) viewRoot.findViewById(R.id.action_load_app_spinner);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    adapter.data[4][0]=packageName[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            CustomAdapter customAdapter=new CustomAdapter(context, icon ,appName);
            spin.setAdapter(customAdapter);
            return dialog;
        }

    private void getPackagesDetails() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = ((Activity)context).getPackageManager().queryIntentActivities( mainIntent, 0);
        icon = new Drawable[pkgAppsList.size()];
        appName = new String[pkgAppsList.size()];
        packageName = new String[pkgAppsList.size()];
        int i = 0;
        for (Object object : pkgAppsList)  {
            ResolveInfo info = (ResolveInfo) object;
            Drawable image    = ((Activity)context).getPackageManager().getApplicationIcon(info.activityInfo.applicationInfo);
            String strAppName  	= info.activityInfo.applicationInfo.publicSourceDir.toString();
            String strPackageName  = info.activityInfo.applicationInfo.packageName.toString();
            final String title 	= (String)((info != null) ?((Activity)context).getPackageManager().getApplicationLabel(info.activityInfo.applicationInfo) : "???");
            icon[i] = image;
            appName[i] = title;
            packageName[i] = strPackageName;
            i++;
            //Log.v("name", title + " " + strAppName);
        }
    }
}
