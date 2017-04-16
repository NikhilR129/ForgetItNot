package com.example.nikhilr129.forgetitnot.action.actionDialog.LoadAppSpinner;

/**
 * Created by kanchicoder on 4/16/2017.
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhilr129.forgetitnot.R;

public class CustomAdapter extends BaseAdapter {
    Context context;
    Drawable icons[];
    String[] appName;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, Drawable[] icons, String[] appName) {
        this.context = applicationContext;
        this.icons = icons;
        this.appName = appName;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner, null);
        ImageView icon = (ImageView) view.findViewById(R.id.action_spinner_imageView);
        TextView names = (TextView) view.findViewById(R.id.action_spinner_textView);
        icon.setImageDrawable(icons[i]);
        names.setText(appName[i]);
        return view;
    }
}