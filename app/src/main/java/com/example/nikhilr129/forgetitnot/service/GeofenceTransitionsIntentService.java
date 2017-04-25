package com.example.nikhilr129.forgetitnot.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.ActionHandlers.PerformAction;
import com.example.nikhilr129.forgetitnot.Models.Action;
import com.example.nikhilr129.forgetitnot.Models.Task;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by kanchicoder on 24/4/17.
 */

public class GeofenceTransitionsIntentService extends IntentService {
    public static final String CUSTOM_MESSAGE="GeoLocation";
    private static String TAG="MyBroadcast";

    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    void perform(Context context, String type, String a0, String a1, String a2, String a3)
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
    @Override
    protected void onHandleIntent(Intent intent) {

        GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            int errorCode = geoFenceEvent.getErrorCode();
            Log.e(TAG, "Location Services error: " + errorCode);
        } else {

            int transitionType = geoFenceEvent.getGeofenceTransition();
            if (Geofence.GEOFENCE_TRANSITION_ENTER == transitionType) {
                Log.d(TAG,"entered");
                List<Geofence> triggeringGeofences = geoFenceEvent.getTriggeringGeofences();
                for(int i=0;i<triggeringGeofences.size();i++)
                {
                    long id=Long.parseLong(triggeringGeofences.get(i).getRequestId());
                    Realm.init(this);
                    Realm realm=Realm.getDefaultInstance();
                    Task t=realm.where(Task.class).equalTo("id",id).findFirst();
                    RealmList<Action> actions=t.actions;
                    for(int j=0;j<actions.size();j++)
                        perform(this,actions.get(j).type,actions.get(j).a0,actions.get(j).a1,actions.get(j).a2,actions.get(j).a3);
                }
            }

        }
    }


}
