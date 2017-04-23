package com.example.nikhilr129.forgetitnot.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.event.eventDialog.BatteryDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.BluetoothDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.HeadsetDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.IncomingCallDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.OutGoingCallDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.PowerDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.TimeDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context mContext;
    private List<Event> eventList;
    public String[][] data = new String[8][3];

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView thumbnail;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    //constructor
    public EventAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.title.setText(event.getName());
        //used for changing the background color on click
        if(event.getSelected()){
            holder.cardView.setCardElevation(16);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#bdbdbd"));

        }else{
            holder.cardView.setCardElevation(8);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }
        // loading event cover using Glide library
        Glide.with(mContext).load(event.getThumbnail()).into(holder.thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //function used for fetching data
                if (!anySelected()) {
                    fetchData(holder, event);
                    event.setSelected();
                    notifyDataSetChanged();
                }
                else if(event.getSelected()) {
                    event.setSelected();
                    notifyDataSetChanged();
                }
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //function used for fetching data
                if (!anySelected()) {
                    fetchData(holder, event);
                    event.setSelected();
                    notifyDataSetChanged();
                }
                else if(event.getSelected()) {
                    event.setSelected();
                    notifyDataSetChanged();
                }
            }
        });
    }
    private void fetchData(MyViewHolder holder, Event event) {
        switch (holder.title.getText().toString())
        {
            case "Time":
                TimeDialog tobj = new TimeDialog(mContext, event, this);
                AlertDialog tdialog = tobj.create();
                tdialog.show();
                break;
            case "Incoming Call":
                IncomingCallDialog iobj = new IncomingCallDialog(mContext, event, this);
                iobj.create().show();
                break;

            case "Outgoing Call" :
                OutGoingCallDialog oobj = new OutGoingCallDialog(mContext, event, this);
                oobj.create().show();
                break;
            case "Location":
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    ((Activity)mContext).startActivityForResult(builder.build((Activity)mContext), 3);
                    Log.d("hello","world");
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case "HeadSet":
                HeadsetDialog hobj = new HeadsetDialog(mContext, event, this);
                AlertDialog dialog = hobj.create();
                dialog.show();
                break;
            case "Power":
                PowerDialog pobj = new PowerDialog(mContext, event, this);
                AlertDialog pdialog = pobj.create();
                pdialog.show();
                break;
            case "Bluetooth":
                BluetoothDialog blobj = new BluetoothDialog(mContext, event, this);
                AlertDialog bldialog = blobj.create();
                bldialog.show();
                break;
            case "Battery":
                BatteryDialog baobj = new BatteryDialog(mContext, event, this);
                AlertDialog badialog = baobj.create();
                badialog.show();
                break;
        }
    }
    private boolean anySelected() {
        for(int i = 0; i < eventList.size(); ++i) {
            if(eventList.get(i).getSelected()) return true;
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}