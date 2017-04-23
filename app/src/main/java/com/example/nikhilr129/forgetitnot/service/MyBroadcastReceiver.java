package com.example.nikhilr129.forgetitnot.service;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.ActionHandlers.PerformAction;
import com.example.nikhilr129.forgetitnot.Models.Action;
import com.example.nikhilr129.forgetitnot.Models.Task;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by nikhilr129 on 19/4/17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    Realm realm;
    PerformAction taskperformer;
    public MyBroadcastReceiver()
    {
    }

    void perform(Context context,String type,String a0,String a1,String a2,String a3)
    {
        PerformAction performer=new PerformAction(context);
        switch(type)
        {
            case "Profile":
                performer.performProfileAction(a0);
                break;
            case "Message":
                performer.performMessageAppAction(0,a0,a1);
                break;
            case "Wifi":
                performer.performWifiAction(a0);
                break;
            case "Notify":
                performer.performNotifyAppAction(a0);
                break;
            case "LoadApp":
                performer.performLoadAppAction(a0);
                break;
            case "Speakerphone":
                performer.peformSpeakerPhoneAction();
                break;
            case "Volume":
                performer.performVolumeAction(Integer.parseInt(a0),Integer.parseInt(a1),Integer.parseInt(a2));
                break;
            case "Wallpaper":
                Uri uri=Uri.parse(a0);
                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
                    performer.performWallpaperAction(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Wallpaper not found", Toast.LENGTH_SHORT).show();
                }


        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        Realm.init(context);
        realm=Realm.getDefaultInstance();

        if (isInitialStickyBroadcast()) {
            //ignore this broadcast
        } else {
            taskperformer=new PerformAction(context);
            Realm.init(context);
            Realm realm=Realm.getDefaultInstance();
            RealmResults<Task> rl;
            // Connectivity state has changed
            String condition=intent.getAction();

            Intent i=new Intent(context,HelloService.class);
            switch(condition)
            {
                case "android.intent.action.HEADSET_PLUG":   //fine
                    i.putExtra("event","headset");
                    if(intent.getIntExtra("state",-1)==1)
                    {
                        i.putExtra("type","plugged");
                        rl=realm.where(Task.class).equalTo("event.type","HeadSet").equalTo("event.a0","0").findAll();
                        for(int x=0;x<rl.size();x++)
                        {
                            Log.d(TAG,rl.get(x).title);
                            RealmList<Action> actions=rl.get(x).actions;
                            for(int y=0;y<actions.size();y++)
                            {
                                Action a=actions.get(y);
                                perform(context,a.type,a.a0,a.a1,a.a2,a.a3);
                            }
                        }
                        Log.d(TAG,"headset plugged");
                    }
                    if(intent.getIntExtra("state",-1)==0)
                    {
                        i.putExtra("type","unplugged");
                        rl=realm.where(Task.class).equalTo("event.type","HeadSet").equalTo("event.a0","1").findAll();
                        for(int x=0;x<rl.size();x++)
                        {
                            Log.d(TAG,rl.get(x).title);
                        }
                        Log.d(TAG,"headset unplugged");
                    }
                    break;


                case "android.intent.action.ACTION_POWER_CONNECTED": //check again
                    i.putExtra("event","power");
                    i.putExtra("type","connected");
                    rl=realm.where(Task.class).equalTo("event.type","Power").equalTo("event.a0","0").findAll();
                    for(int x=0;x<rl.size();x++)
                    {
                        Log.d(TAG,rl.get(x).title);
                    }
                    break;


                case "android.intent.action.ACTION_POWER_DISCONNECTED":  //check again
                    i.putExtra("event","power");
                    i.putExtra("type","disconnected");
                    rl=realm.where(Task.class).equalTo("event.type","Power").equalTo("event.a0","1").findAll();
                    for(int x=0;x<rl.size();x++)
                    {
                        Log.d(TAG,rl.get(x).title);
                    }
                    break;


                case "android.bluetooth.adapter.action.STATE_CHANGED":  //fine
                    i.putExtra("event","bluetooth");
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    if(state== BluetoothAdapter.STATE_TURNING_ON)
                    {
                        i.putExtra("type","connected");
                        rl = realm.where(Task.class).equalTo("event.type", "Bluetooth").equalTo("event.a0", "0").findAll();
                        for(int x=0;x<rl.size();x++)
                        {
                            Log.d(TAG,rl.get(x).title);
                        }
                        Log.d(TAG,"bluetooth turned on");
                    }
                    else if(state== BluetoothAdapter.STATE_TURNING_OFF)
                    {
                        i.putExtra("type","disconnected");
                        rl = realm.where(Task.class).equalTo("event.type", "Bluetooth").equalTo("event.a0", "1").findAll();
                        for(int x=0;x<rl.size();x++)
                        {
                            Log.d(TAG,rl.get(x).title);
                        }
                        Log.d(TAG,"bluetooth turned off");
                    }
                    break;


                case "android.net.wifi.WIFI_STATE_CHANGED":  //fine
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                    if (WifiManager.WIFI_STATE_ENABLED == wifiState) {
                        rl = realm.where(Task.class).equalTo("event.type", "Wifi").equalTo("event.a0", "0").findAll();
                        for(int x=0;x<rl.size();x++)
                        {
                            Log.d(TAG,rl.get(x).title);
                        }
                        Log.d(TAG,"wifi enabled");
                    }
                    else if(WifiManager.WIFI_STATE_DISABLING == wifiState)
                    {
                        rl = realm.where(Task.class).equalTo("event.type", "Wifi").equalTo("event.a0", "0").findAll();
                        for(int x=0;x<rl.size();x++)
                        {
                            Log.d(TAG,rl.get(x).title);
                        }
                        Log.d(TAG,"wifi disabled");
                    }
            }

            //not starting service,broadcast receiver taking care of actions
           // Log.d(TAG,i.getStringExtra("event"+i.getStringExtra("type")));
            //context.startService(i);
        }

    }
}
