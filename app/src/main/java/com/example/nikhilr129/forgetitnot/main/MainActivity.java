package com.example.nikhilr129.forgetitnot.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.service.HelloService;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {
    private  FloatingActionButton floatingActionButton1;
    private  BottomBar  bottomNavigationBar;
    private  Toolbar toolbar;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private  FloatingActionMenu materialDesignFAM;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set Slide Transition
        if(Build.VERSION.SDK_INT >= 21)
            setFadeTransition();
        //Toolbar support in android
        setToolbar();

        SwitchCompat s=(SwitchCompat)findViewById(R.id.enable_task_switch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    startService(new Intent(MainActivity.this,HelloService.class));
                }
                else
                {
                    stopService(new Intent(MainActivity.this,HelloService.class));

                }
            }
        });

        context=this;


        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareTasks();

        //Create and applying action on floating action Menu
        createAndApplyActionOnFloatingActionMenu();

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

    private int returnDrawable(String type)
    {
        switch(type)
        {
            case "Time":
                return R.drawable.time;
            case "Incoming Call":
                return R.drawable.incoming;
            case "Outgoing Call":
                return R.drawable.outgoing;
            case "Location":
                return R.drawable.location;
            case "Bluetooth":
                return R.drawable.bluetooth;
            case "Battery":
                return R.drawable.battery;
            case "Power":
                return R.drawable.power;
            case "HeadSet":
                return R.drawable.headset;
            default:
                return R.drawable.bell;
        }
    }
    private void prepareTasks() {
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
        Task a;
        Realm.init(this);
        Realm realm=Realm.getDefaultInstance();
        RealmResults<com.example.nikhilr129.forgetitnot.Models.Task> rl=realm.where(com.example.nikhilr129.forgetitnot.Models.Task.class).findAll();
        for(int i=0;i<rl.size();i++)
        {
            com.example.nikhilr129.forgetitnot.Models.Task t=rl.get(i);
            String title=t.title;
            String event_type=t.event.type;
            long id = t.id;
            a=new Task(title,id,event_type,returnDrawable(event_type));
            taskList.add(a);
        }

        adapter.notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setFadeTransition() {
        Fade s = new Fade();
        s.setDuration(200);
        getWindow().setEnterTransition(s);

    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.iconsTint));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.iconsTint));
        SwitchCompat button = (SwitchCompat) findViewById(R.id.enable_task_switch);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Toast.makeText(MainActivity.this, "changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createAndApplyActionOnFloatingActionMenu() {
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item1);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu_main first item clicked
                TaskName obj = new TaskName(MainActivity.this);
                obj.create().show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
                this.finish();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Check out this awesome app.(LINK HERE)It automates most of the boring tasks in Android";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ForgetItNot");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.aboutUs:
                Intent intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;
            case R.id.permissions:

                break;
            default:
                break;
        }

        return true;
    }
}
