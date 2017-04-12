package com.example.nikhilr129.forgetitnot.event;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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
            cardView = (CardView) view.findViewById(R.id.event_card_view);
            title = (TextView) view.findViewById(R.id.event_title);
            thumbnail = (ImageView) view.findViewById(R.id.event_thumbnail);
            overflow = (ImageView) view.findViewById(R.id.event_overflow);
        }
    }


    public EventAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.title.setText(event.getName());
        //c
        if(event.isSelected()){
            holder.cardView.setCardElevation(16);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#bdbdbd"));

        }else{
            holder.cardView.setCardElevation(8);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }
        // loading event cover using Glide library
        Glide.with(mContext).load(event.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, event, holder);
            }
        });
    }

    /**
     * Showing popup menu_main when tapping on 3 dots
     */
    private void showPopupMenu(View view, Event event,MyViewHolder holder) {
        // inflate menu_main
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_event, popup.getMenu());
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
                    event.setSelected(false);
                    notifyDataSetChanged();
                    return true;
                case R.id.select_event:
                    Toast.makeText(mContext, "Select Event" + event.getName(), Toast.LENGTH_SHORT).show();
                    event.setSelected(true);
                    notifyDataSetChanged();
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
