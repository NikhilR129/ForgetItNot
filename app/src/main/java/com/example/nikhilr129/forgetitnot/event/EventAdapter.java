package com.example.nikhilr129.forgetitnot.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView thumbnail, overflow;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


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

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //function used for fetching data
                if (!event.getSelected())
                    fetchData(holder);
                event.setSelected();
                notifyDataSetChanged();

            }
        });
    }
    private void fetchData(MyViewHolder holder) {
        Intent i;
        switch (holder.title.getText().toString())
        {
            case "Time":
                TimeDialog tobj = new TimeDialog(mContext);
                AlertDialog tdialog = tobj.create();
                tdialog.show();
                break;
            case "Incoming Call":
                IncomingCallDialog iobj = new IncomingCallDialog(mContext);
                iobj.create().show();
                break;

            case "Outgoing Call" :
                OutGoingCallDialog oobj = new OutGoingCallDialog(mContext);
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
                HeadsetDialog hobj = new HeadsetDialog(mContext);
                AlertDialog dialog = hobj.create();
                dialog.show();
                break;
            case "Power":
                PowerDialog pobj = new PowerDialog(mContext);
                AlertDialog pdialog = pobj.create();
                pdialog.show();
                break;
            case "Bluetooth":
                BluetoothDialog blobj = new BluetoothDialog(mContext);
                AlertDialog bldialog = blobj.create();
                bldialog.show();
                break;
            case "Battery":
                BatteryDialog baobj = new BatteryDialog(mContext);
                AlertDialog badialog = baobj.create();
                badialog.show();
                break;
        }
    }

    /**
     * Showing popup menu_main when tapping on 3 dots
     */
    private void showPopupMenu(View view, Event event,MyViewHolder holder) {
        // inflate menu_main
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_event_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(event, holder));
        popup.show();
    }

    /**
     * Click listener for popup menu_main items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        Event event;
        MyViewHolder holder;
        public MyMenuItemClickListener(Event event, MyViewHolder holder) {
            this.event = event;
            this.holder =  holder;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.discard_event:
                    Toast.makeText(mContext, "Discard Event" + event.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.select_event:
                    Toast.makeText(mContext, "Select Event" + event.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
