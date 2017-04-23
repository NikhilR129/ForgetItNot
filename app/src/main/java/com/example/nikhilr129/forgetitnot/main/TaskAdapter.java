package com.example.nikhilr129.forgetitnot.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nikhilr129.forgetitnot.R;
import com.google.android.gms.vision.text.Text;

import java.util.List;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context mContext;
    private List<Task> taskList;
    public String[][] data = new String[8][3];

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, event;
        private ImageView thumbnail;
        private CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.main_card_view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            event = (TextView) view.findViewById(R.id.event);
        }
    }

    //constructor
    public TaskAdapter(Context mContext, List<Task> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.event.setText(task.getEvent());
        // loading task cover using Glide library
        Glide.with(mContext).load(task.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}