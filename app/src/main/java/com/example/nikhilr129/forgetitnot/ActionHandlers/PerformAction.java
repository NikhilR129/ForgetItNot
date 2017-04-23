package com.example.nikhilr129.forgetitnot.ActionHandlers;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;

import java.io.IOException;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by kanchicoder on 4/19/2017.
 */

public class PerformAction {
    Context context;
    private final  int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public  PerformAction(Context context) {
        this.context = context;
    }
    public void performWifiAction(String option) {   //working fine
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
    public void performProfileAction(String option) {  //working on redmi
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

    public void performNotifyAppAction(String message) { //working fine
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
        //   send sms
        if(i == 0) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                Toast.makeText(context.getApplicationContext(), "Message Sent",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    }
    public void peformSpeakerPhoneAction() {
        AudioManager audioManager =  (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
        Log.v("entered","entered");
    }
    public void performVolumeAction(int media, int ring, int alarm) {
        AudioManager audioManager = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (1.5*media), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, (int) (1.5*alarm), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, (int) (1.5*ring), 0);
    }


    public void performDownloadAction(String url) {
        if (ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        // Here, thisActivity is the current activity
        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
        final String[] separated = url.split("/");
        final String myFile = separated[separated.length - 1];
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Some description");
        request.setTitle("Some title");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, myFile);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}