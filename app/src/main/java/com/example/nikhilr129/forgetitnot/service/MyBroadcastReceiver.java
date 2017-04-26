package com.example.nikhilr129.forgetitnot.service;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
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
                performer.performNotifyAppAction(a0,a1);
                break;
            case "Load App":
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

    public void run_query(Context context,Realm realm,String type,String value)
    {
        RealmResults<Task> rl=realm.where(Task.class).equalTo("event.type",type).equalTo("event.a0",value).findAll();
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
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String id=intent.getStringExtra("id");
        if(id!=null)
        {
            Log.d(TAG,id);
            Realm.init(context);
            Realm realm=Realm.getDefaultInstance();
            Task task=realm.where(Task.class).findFirst();
            RealmList<Action> actions=task.actions;
            for(int i=0;i<actions.size();i++)
                perform(context,actions.get(i).type,actions.get(i).a0,actions.get(i).a1,actions.get(i).a2,actions.get(i).a3);
        }
        if (isInitialStickyBroadcast()) {
            //ignore this broadcast
        } else {
            Realm.init(context);
            Realm realm=Realm.getDefaultInstance();
            RealmResults<Task> rl;
            // Connectivity state has changed

            String condition=intent.getAction();
            if(condition!=null)
            switch(condition)
            {
                case "android.intent.action.HEADSET_PLUG":   //fine

                    if(intent.getIntExtra("state",-1)==1)
                    {
                        run_query(context,realm,"HeadSet","0");
                        Log.d(TAG,"headset plugged");
                    }
                    if(intent.getIntExtra("state",-1)==0)
                    {
                        run_query(context,realm,"HeadSet","1");
                        Log.d(TAG,"headset unplugged");
                    }
                    break;


                case "android.intent.action.ACTION_POWER_CONNECTED": //check again
                    run_query(context,realm,"Power","0");
                    Log.d(TAG,"power connected");
                    break;


                case "android.intent.action.ACTION_POWER_DISCONNECTED":  //check again
                    run_query(context,realm,"Power","1");
                    Log.d(TAG,"power disconnected");
                    break;


                case "android.bluetooth.adapter.action.STATE_CHANGED":  //fine
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    if(state== BluetoothAdapter.STATE_TURNING_ON)
                    {
                        run_query(context,realm,"Bluetooth","0");
                        Log.d(TAG,"bluetooth turned on");
                    }
                    else if(state== BluetoothAdapter.STATE_TURNING_OFF)
                    {
                        run_query(context,realm,"Bluetooth","1");
                        Log.d(TAG,"bluetooth turned off");
                    }
                    break;


                case "android.intent.action.BATTERY_CHANGED":
                    int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                    if(status==BatteryManager.BATTERY_STATUS_FULL)
                    {
                        run_query(context,realm,"Battery","0");
                        Log.d(TAG,"battery full");
                    }
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                    float batteryPct = (level / (float)scale)*100;

                    if(batteryPct<=20)
                    {
                        run_query(context,realm,"Battery","1");
                        Log.d(TAG,"<20%");

                    }
                    break;



                case "android.net.wifi.WIFI_STATE_CHANGED":  //fine
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                    if (WifiManager.WIFI_STATE_ENABLED == wifiState) {
                        run_query(context,realm,"Wifi","0");
                        Log.d(TAG,"wifi enabled");
                    }
                    else if(WifiManager.WIFI_STATE_DISABLING == wifiState)
                    {
                        run_query(context,realm,"Wifi","1");
                        Log.d(TAG,"wifi disabled");
                    }
                    break;
                case "android.intent.action.NEW_OUTGOING_CALL":
                    String savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
                    run_query(context,realm,"Outgoing Call",savedNumber);
                    Log.d(TAG,"outgoing call"+savedNumber);
                    break;
                case "android.intent.action.PHONE_STATE":
                    String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                    String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    if (stateStr!=null && stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        run_query(context,realm,"Incoming Call",number);
                    }
                    break;
                case "GeoLocation":
                    Log.d(TAG,intent.getStringExtra("id"));
            }
        }

    }
}
