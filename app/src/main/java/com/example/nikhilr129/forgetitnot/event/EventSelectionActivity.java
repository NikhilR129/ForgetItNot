package com.example.nikhilr129.forgetitnot.event;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nikhilr129.forgetitnot.Fragments.TimePickerFragment;
import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.ActionSelectionActivity;
import com.example.nikhilr129.forgetitnot.event.eventDialog.IncomingCallDialog;
import com.example.nikhilr129.forgetitnot.event.eventDialog.OutGoingCallDialog;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class EventSelectionActivity extends AppCompatActivity implements TimePickerFragment.OnDataPass {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> eventList;

    //test done by nikhil

    private final int INCOMING_PICK_CONTACT = 1, OUTGOING_PICK_CONTACT= 2, SELECT_LOCATION=3;

    HashMap<String,String> hm;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch(requestCode)
       {

           case INCOMING_PICK_CONTACT:
               if(resultCode==RESULT_OK) {
                    IncomingGetContact(data);
               }
               break;
           case OUTGOING_PICK_CONTACT:
               if(resultCode==RESULT_OK) {
                   IncomingGetContact(data);
               }
               break;

           case SELECT_LOCATION:
               if(resultCode==RESULT_OK) {
                   Place place = PlacePicker.getPlace(this,data);
                   String toastMsg = String.format("Place: %s", place.getName());
                   Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
               }
               break;
       }
    }
    //
    private void IncomingGetContact(Intent data) {
        Uri contactUri = data.getData();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver()
                .query(contactUri, projection, null, null, null);
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String number = cursor.getString(column);
        IncomingCallDialog obj = new IncomingCallDialog(EventSelectionActivity.this);
        obj.create().show();
        View v = obj.getView();
        TextView textView = (TextView) v.findViewById(R.id.event_call_dialog_textView);
        textView.setText(number);
    }
    private void OutGoingGetContact(Intent data) {
        Uri contactUri = data.getData();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver()
                .query(contactUri, projection, null, null, null);
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String number = cursor.getString(column);
        OutGoingCallDialog obj = new OutGoingCallDialog(EventSelectionActivity.this);
        obj.create().show();
        View v = obj.getView();
        TextView textView = (TextView) v.findViewById(R.id.event_call_dialog_textView);
        textView.setText(number);
    }

    @Override
    public void onDataPass(int data1,int data2) {
        Toast.makeText(this, data1+""+data2, Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity_main);
        setToolbar();
        initCollapsingToolbar();
        //initiate hasmap;
        hm=new HashMap<>();

        recyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);

        eventList = new ArrayList<>();
        adapter = new EventAdapter(this, eventList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareEvents();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.event_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * fonction for setting toolbar
     */
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.event_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Events");
        toolbar.setTitleTextColor(getResources().getColor(R.color.iconsTint));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.iconsTint));
    }
    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.event_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.event_appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareEvents() {
        int[] covers = new int[]{
                R.drawable.time,
                R.drawable.incoming,
                R.drawable.outgoing,
                R.drawable.location,
                R.drawable.headset,
                R.drawable.bluetooth,
                R.drawable.battery,
                R.drawable.power
        };

        Event a = new Event("Time", covers[0]);
        eventList.add(a);

        a = new Event("Incoming Call", covers[1]);
        eventList.add(a);

        a = new Event("Outgoing Call", covers[2]);
        eventList.add(a);

        a = new Event("Location",  covers[3]);
        eventList.add(a);

        a = new Event("HeadSet",  covers[4]);
        eventList.add(a);

        a = new Event("Bluetooth",  covers[5]);
        eventList.add(a);

        a = new Event("Battery",  covers[6]);
        eventList.add(a);

        a = new Event("Power",  covers[7]);
        eventList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.check_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.check:
                startActivity(new Intent(EventSelectionActivity.this, ActionSelectionActivity.class));
                break;
            default:
                break;
        }

        return true;
    }
}
