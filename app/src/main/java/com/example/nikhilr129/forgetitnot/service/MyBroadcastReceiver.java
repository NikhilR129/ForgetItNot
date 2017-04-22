package com.example.nikhilr129.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by nikhilr129 on 19/4/17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    public MyBroadcastReceiver()
    {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isInitialStickyBroadcast()) {
            // Ignore this call to onReceive, as this is the sticky broadcast
        } else {
            // Connectivity state has changed
            Intent i=new Intent(context,HelloService.class);
            i.putExtra("action",intent.getAction());
            int state = intent.getIntExtra("state", -1);
            context.startService(i);
            Log.d(TAG,intent.getAction());
            Log.d(TAG,state+"");
        }

    }
}
