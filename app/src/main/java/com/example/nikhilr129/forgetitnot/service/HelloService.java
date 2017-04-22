package com.example.nikhilr129.service;

/**
 * Created by nikhilr129 on 19/4/17.
 */

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;


public class HelloService extends Service {
    IntentFilter filter;
    MyBroadcastReceiver receiver;
    class Mythread implements Runnable{
        String type;
        IntentFilter filter;
        BroadcastReceiver receiver;
        Mythread(String s)
        {
            type=s;
            receiver=new MyBroadcastReceiver();
        }
        @Override
        public void run() {
            switch(type){
                case "power":
                    filter=new IntentFilter(Intent.ACTION_POWER_CONNECTED);
                    break;
                case "bluetooth":
                    filter=new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                    break;

            }
            if(receiver!=null && filter!=null)
            registerReceiver(receiver,filter);
        }
    }
    @Override
    public void onCreate() {
        receiver=new MyBroadcastReceiver();
        filter=new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver,filter);
        filter=new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(receiver,filter);
        filter=new IntentFilter(AudioManager.ACTION_HEADSET_PLUG);
        registerReceiver(receiver,filter);
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String event=intent.getStringExtra("action");
        Toast.makeText(this, event, Toast.LENGTH_SHORT).show();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!=null)
            unregisterReceiver(receiver);
        Toast.makeText(this, "stopping", Toast.LENGTH_SHORT).show();
    }
}