package com.example.nikhilr129.forgetitnot.service;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.nikhilr129.forgetitnot.Models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nikhilr129 on 19/4/17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    Realm realm;
    public MyBroadcastReceiver()
    {
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        Realm.init(context);
        realm=Realm.getDefaultInstance();

        if (isInitialStickyBroadcast()) {
            //Log.d(TAG,intent.getAction());
            // Ignore this call to onReceive, as this is the sticky broadcast
        } else {
            // Connectivity state has changed
            String condition=intent.getAction();
            RealmResults rl;

            //this one gets printed in log first,others inside switch block
            Log.d(TAG,condition);
            Intent i=new Intent(context,HelloService.class);
            switch(condition)
            {
                case "android.intent.action.HEADSET_PLUG":
                    Log.d(TAG,"headset");
                    i.putExtra("event","headset");
                    if(intent.getIntExtra("state",-1)==1)
                    {
                        i.putExtra("type","plugged");
                        rl=realm.where(Task.class).equalTo("event.type","headset").equalTo("event.a0","plugged").findAll();
                        Log.d(TAG,rl.size()+"");
                    }
                    if(intent.getIntExtra("state",-1)==0)
                    {
                        i.putExtra("type","unplugged");
                        rl=realm.where(Task.class).equalTo("event.type","headset").equalTo("event.a0","unplugged").findAll();
                        Log.d(TAG,rl.size()+"");
                    }

                    break;
                case "android.intent.action.ACTION_POWER_CONNECTED":
                    i.putExtra("event","power");
                    i.putExtra("type","connected");
                    rl=realm.where(Task.class).equalTo("event.type","power").equalTo("event.a0","connected").findAll();
                    Log.d(TAG,rl.size()+"");
                    Log.d(TAG,"power");
                    break;
                case "android.intent.action.ACTION_POWER_DISCONNECTED":
                    i.putExtra("event","power");
                    i.putExtra("type","disconnected");
                    rl=realm.where(Task.class).equalTo("event.type","power").equalTo("event.a0","disconnected").findAll();
                    Log.d(TAG,rl.size()+"");
                    Log.d(TAG,"power");
                    break;
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    i.putExtra("event","bluetooth");
                    Log.d(TAG,"bluetooth");
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    if(state== BluetoothAdapter.STATE_CONNECTED)
                    {
                        i.putExtra("type","connected");
                        rl = realm.where(Task.class).equalTo("event.type", "bluetooth").equalTo("event.a0", "connected").findAll();
                        Log.d(TAG,rl.size()+"");
                    }
                    else if(state== BluetoothAdapter.STATE_DISCONNECTED)
                    {
                        i.putExtra("type","disconnected");
                        rl=realm.where(Task.class).equalTo("event.type","bluetooth").equalTo("event.a0","disconnected").findAll();
                        Log.d(TAG,rl.size()+"");
                    }
                    break;
            }


           // Log.d(TAG,i.getStringExtra("event"+i.getStringExtra("type")));
            //context.startService(i);
        }

    }
}
