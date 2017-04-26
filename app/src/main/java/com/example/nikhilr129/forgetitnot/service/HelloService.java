package com.example.nikhilr129.forgetitnot.service;

/**
 * Created by nikhilr129 on 19/4/17.
 */

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.Models.Task;
import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.main.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class HelloService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {
    IntentFilter filter;
    private static String TAG = "MyBroadcast";
    MyBroadcastReceiver receiver;
    Calendar calendar;
    AlarmManager alarmManager;
    final int FOREGROUND_NOTIFICATION_ID = 15966896;
    List<Geofence> mGeofenceList;
    private LocationServices mLocationService;
    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent,mAlarmIntent;
    private GoogleApiClient mApiClient;

    @Override
    public void onCreate() {
        if (!isGooglePlayServicesAvailable()) {
            Log.e(TAG, "Google Play services unavailable.");
            return;
        }

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        // Instantiate a new geofence storage area.

        mGeofenceList = new ArrayList<Geofence>();
        createGeofences();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Forget It Not")
                .setContentText("Relax!! I will do as ordered")
                .setSmallIcon(R.drawable.bell)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(FOREGROUND_NOTIFICATION_ID, notification);


        //for time
        Realm.init(this);
        Realm realm=Realm.getDefaultInstance();
        Task rl=realm.where(Task.class).equalTo("event.type","Time").findFirst();
        if(rl!=null)
        {
            calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(rl.event.a0));
            calendar.set(Calendar.MINUTE, Integer.parseInt(rl.event.a1));

            Intent myIntent=new Intent(this,MyBroadcastReceiver.class);
            myIntent.putExtra("extra", "yes");
            mAlarmIntent = PendingIntent.getBroadcast(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mAlarmIntent);
        }





        receiver = new MyBroadcastReceiver();

        //incoming call
        filter = new IntentFilter();
        registerReceiver(receiver, new IntentFilter("android.intent.action.PHONE_STATE"));

        //outgoing call
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));

        //location to be coded below
        registerReceiver(receiver,new IntentFilter("GeoLocation"));
        //audio
        registerReceiver(receiver, new IntentFilter(AudioManager.ACTION_HEADSET_PLUG));


        //bluetooth on/off
        registerReceiver(receiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        //battery charging completed/battery low
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        //power connected/disconnected
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));

        //wifi plugged/unplugged(extra,not in app yet)
        registerReceiver(receiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));


        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
    }

    private void createGeofences() {
        Realm.init(HelloService.this);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> rl = realm.where(Task.class).equalTo("event.type", "Location").findAll();
        for (int i = 0; i < rl.size(); i++) {
            Task t = rl.get(i);
            Log.d(TAG,t.id+t.event.type+t.event.a0+t.event.a1);
            SimpleGeofence gf = new SimpleGeofence(t.id, Double.parseDouble(t.event.a0), Double.parseDouble(t.event.a1), 100, Geofence.NEVER_EXPIRE, Geofence.GEOFENCE_TRANSITION_ENTER);
           mGeofenceList.add(gf.toGeofence());
        }
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
            alarmManager.cancel(mAlarmIntent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            LocationServices.GeofencingApi.removeGeofences(mApiClient,getGeofenceTransitionPendingIntent());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Get the PendingIntent for the geofence monitoring request.
        // Send a request to add the current geofences.
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mApiClient);
        if (mLastLocation != null) {
            Log.d(TAG,mLastLocation.getLatitude()+" "+mLastLocation.getLongitude()+"");
        }

        mGeofenceRequestIntent = getGeofenceTransitionPendingIntent();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(mGeofenceList.size()>0)
        {
            LocationServices.GeofencingApi.addGeofences(
                    mApiClient,
                    getGeofencingRequest(),
                    getGeofenceTransitionPendingIntent()
            );

            Toast.makeText(this, "Connected to api", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (null != mGeofenceRequestIntent) {
            LocationServices.GeofencingApi.removeGeofences(mApiClient, mGeofenceRequestIntent);
            Log.d(TAG,"removed");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.

            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);

    }

    /**
     * Checks if Google Play services is available.
     * @return true if it is.
     */
    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Google Play services is available.");
            }
            return true;
        } else {
            Log.d(TAG, "Google Play services is unavailable.");
            return false;
        }
    }

    /**
     * Create a PendingIntent that triggers GeofenceTransitionIntentService when a geofence
     * transition occurs.
     */
    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }
}