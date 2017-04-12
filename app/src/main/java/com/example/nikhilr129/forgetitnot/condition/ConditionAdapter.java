package com.example.nikhilr129.forgetitnot.condition;

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

public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.MyViewHolder> {

    private Context mContext;
    private List<Condition> conditionList;

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


    public ConditionAdapter(Context mContext, List<Condition> conditionList) {
        this.mContext = mContext;
        this.conditionList = conditionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Condition condition = conditionList.get(position);
        holder.title.setText(condition.getName());
        //c
        if(condition.isSelected()){
            holder.cardView.setCardElevation(16);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#bdbdbd"));

        }else{
            holder.cardView.setCardElevation(8);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }
        // loading condition cover using Glide library
        Glide.with(mContext).load(condition.getThumbnail()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                condition.setSelected();
                notifyDataSetChanged();
            }
        });
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, condition, holder);
            }
        });
    }

    /**
     * Showing popup menu_main when tapping on 3 dots
     */
    private void showPopupMenu(View view, Condition condition, MyViewHolder holder) {
        // inflate menu_main
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_condition_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(condition, holder));
        popup.show();
    }

    /**
     * Click listener for popup menu_main items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        Condition condition;
        MyViewHolder holder;
        public MyMenuItemClickListener(Condition condition, MyViewHolder holder) {
            this.condition = condition;
            this.holder =  holder;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.discard_condition:
                    Toast.makeText(mContext, "Discard Condition" + condition.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.select_condition:
                    Toast.makeText(mContext, "Select Condition" + condition.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return conditionList.size();
    }
}
