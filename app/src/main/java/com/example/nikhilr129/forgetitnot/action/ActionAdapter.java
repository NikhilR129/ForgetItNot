package com.example.nikhilr129.forgetitnot.action;

/**
 * Created by kanchicoder on 4/12/17.
 */

import android.app.AlertDialog;
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
import com.example.nikhilr129.forgetitnot.action.actionDialog.NotifyDialog;
import com.example.nikhilr129.forgetitnot.action.actionDialog.ProfileDialog;
import com.example.nikhilr129.forgetitnot.action.actionDialog.SpeakerDialog;
import com.example.nikhilr129.forgetitnot.action.actionDialog.VolumeDialog;
import com.example.nikhilr129.forgetitnot.action.actionDialog.WifiDialog;

import java.util.List;
/**
 * Created by kanchicoder on 4/10/17.
 */

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.MyViewHolder> {

    private Context mContext;
    private List<Action> ActionList;

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


    public ActionAdapter(Context mContext, List<Action> ActionList) {
        this.mContext = mContext;
        this.ActionList = ActionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Action action = ActionList.get(position);
        holder.title.setText(action.getName());
        //used for changing the background color on click
        if(action.getSelected()){
            holder.cardView.setCardElevation(16);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#bdbdbd"));

        }else{
            holder.cardView.setCardElevation(8);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f5f5f5"));
        }
        // loading Action cover using Glide library
        Glide.with(mContext).load(action.getThumbnail()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //function used for fetching data
                if(!action.getSelected())
                    fetchData(holder);
                action.setSelected();
                notifyDataSetChanged();

            }
        });
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, action, holder);
            }
        });
    }
    /**
     * function for fetching data
     */
    private void fetchData(MyViewHolder holder) {
        //Toast.makeText(mContext, holder.title.getText(), Toast.LENGTH_SHORT).show();
        if(holder.title.getText() == "Profile"){
            ProfileDialog obj  = new ProfileDialog(mContext);
            AlertDialog dialog = obj.create();
            dialog.show();
        }
        else if(holder.title.getText() == "Wifi") {
            WifiDialog obj  = new WifiDialog(mContext);
            AlertDialog dialog = obj.create();
            dialog.show();
        }
        else if(holder.title.getText() == "Speakerphone") {
            SpeakerDialog obj  = new SpeakerDialog(mContext);
            AlertDialog dialog = obj.create();
            dialog.show();
        }
        else if(holder.title.getText() == "Volume") {
            VolumeDialog obj  = new VolumeDialog(mContext);
            AlertDialog dialog = obj.create();
            dialog.show();
        }
        else if(holder.title.getText() == "Notify") {
            NotifyDialog obj  = new NotifyDialog(mContext);
            AlertDialog dialog = obj.create();
            dialog.show();
        }
    }



    /**
     * Showing popup menu_main when tapping on 3 dots
     */
    private void showPopupMenu(View view, Action Action, MyViewHolder holder) {
        // inflate menu_main
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_action_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(Action, holder));
        popup.show();
    }

    /**
     * Click listener for popup menu_main items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        Action Action;
        MyViewHolder holder;
        public MyMenuItemClickListener(Action Action, MyViewHolder holder) {
            this.Action = Action;
            this.holder =  holder;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.discard_Action:
                    Toast.makeText(mContext, "Discard Action" + Action.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.select_Action:
                    Toast.makeText(mContext, "Select Action" + Action.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return ActionList.size();
    }
}
