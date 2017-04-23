package com.example.nikhilr129.forgetitnot.service;

/**
 * Created by nikhilr129 on 19/4/17.
 */

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

import io.realm.Realm;


public class HelloService extends Service {
    IntentFilter filter;
    MyBroadcastReceiver receiver;
    PhoneCallReceiver phonecallreceiver;
    Realm realm;

  /*  class Mythread implements Runnable{
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
    }*/

    @Override
    public void onCreate() {

        receiver=new MyBroadcastReceiver();
        phonecallreceiver=new PhoneCallReceiver();
        //time using timer

        //incoming call
        filter=new IntentFilter();
        registerReceiver(phonecallreceiver,new IntentFilter("android.intent.action.PHONE_STATE"));

        //outgoing call
        registerReceiver(phonecallreceiver,new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));

        //location to be coded below

        //audio
        registerReceiver(receiver,new IntentFilter(AudioManager.ACTION_HEADSET_PLUG));

        //blueooth
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        //battery plugged/unplugged
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));

        //wifi plugged/unplugged(extra,not in app yet)
        registerReceiver(receiver,new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));


        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        /*String event=intent.getStringExtra("event");
        if(event!=null)
        {
            //final RealmResults<Task> tasks= realm.where(Task.class).findAll();
            //Log.d(TAG,tasks.size()+"");
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.bell)
                            .setContentTitle("Task completed")
                            .setContentText("Click here to view");
// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, MainActivity.class);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mNotificationManager.notify(123, mBuilder.build());

        }*/


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
        if(receiver!=null)
            unregisterReceiver(receiver);
        if(phonecallreceiver!=null)
            unregisterReceiver(phonecallreceiver);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }
}