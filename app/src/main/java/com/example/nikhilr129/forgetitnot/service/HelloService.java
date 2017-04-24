package com.example.nikhilr129.forgetitnot.service;

/**
 * Created by nikhilr129 on 19/4/17.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.main.MainActivity;


public class HelloService extends Service {
    IntentFilter filter;
    MyBroadcastReceiver receiver;
    PhoneCallReceiver phonecallreceiver;
    final int FOREGROUND_NOTIFICATION_ID=15966896;


    @Override
    public void onCreate() {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Forget It Not")
                .setContentText("Relax!! I will do as ordered")
                .setSmallIcon(R.drawable.bell)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(FOREGROUND_NOTIFICATION_ID, notification);
        receiver=new MyBroadcastReceiver();
        //time using timer

        //incoming call
        filter=new IntentFilter();
        registerReceiver(receiver,new IntentFilter("android.intent.action.PHONE_STATE"));

        //outgoing call
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));

        //location to be coded below

        //audio
        registerReceiver(receiver,new IntentFilter(AudioManager.ACTION_HEADSET_PLUG));



        //bluetooth on/off
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        //battery charging completed/battery low
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        //power connected/disconnected
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));

        //wifi plugged/unplugged(extra,not in app yet)
        registerReceiver(receiver,new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));


        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(receiver);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}