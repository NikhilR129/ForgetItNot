package com.example.nikhilr129.forgetitnot.ActionHandlers;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;

import java.io.IOException;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by nikhilr129 on 22/4/17.
 */

public class PerformAction {
    Context context;
    private final  int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public  PerformAction(Context context) {
        this.context = context;
    }
    public void performWifiAction(String option) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(option.equals("0")){
            wifiManager.setWifiEnabled(true);
        }
        else if(option.equals("1")){
            wifiManager.setWifiEnabled(false);
        }
        else if(option.equals("2")){
            boolean wifiEnabled = wifiManager.isWifiEnabled();
            Toast.makeText(context,String.valueOf(wifiEnabled), Toast.LENGTH_SHORT).show();
            if(wifiEnabled == true)
                wifiManager.setWifiEnabled(false);
            else
                wifiManager.setWifiEnabled(true);
        }
    }
    public void performProfileAction(String option) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if(option.equals("0")){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else if(option.equals("1")){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else if(option.equals("2")){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }

    public void performWallpaperAction(Bitmap bitmap) {
        WallpaperManager wallManager = WallpaperManager.getInstance(context);
        try {
            wallManager.clear();
            if(bitmap != null)
                wallManager.setBitmap(bitmap);
        } catch (IOException ex) {}
    }

    public void performLoadAppAction(String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Package not found " + packageName, Toast.LENGTH_SHORT).show();
        }
    }

    public void performNotifyAppAction(String message) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.action_coverpage)
                        .setContentTitle("Urgent")
                        .setContentText(message);

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    public void performMessageAppAction(int i, String phoneNo, String message) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        //send sms
//        if(i == 0) {
//            try {
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(phoneNo, null, message, null, null);
//                Toast.makeText(context.getApplicationContext(), "Message Sent",
//                        Toast.LENGTH_LONG).show();
//            } catch (Exception ex) {
//                Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(),
//                        Toast.LENGTH_LONG).show();
//                ex.printStackTrace();
//            }
//        }
        Uri uri = Uri.parse("smsto:" + "8563985494");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "my message");
        intent.setPackage("com.whatsapp");
        context.startActivity(intent);
    }
}