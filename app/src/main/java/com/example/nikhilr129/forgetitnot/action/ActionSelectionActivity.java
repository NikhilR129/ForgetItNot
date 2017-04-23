package com.example.nikhilr129.forgetitnot.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nikhilr129.forgetitnot.Models.Event;
import com.example.nikhilr129.forgetitnot.Models.Task;
import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.actionDialog.MessageDialog;
import com.example.nikhilr129.forgetitnot.action.actionDialog.WallpaperDialog;
import com.example.nikhilr129.forgetitnot.main.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class ActionSelectionActivity extends AppCompatActivity{
    private Toolbar toolbar;
    public Bitmap bmp = null;
    private RecyclerView recyclerView;
    private ActionAdapter adapter;
    private List<Action> ActionList;
    Realm realm;
    private final int SELECT_PHOTO = 0, PICK_CONTACT = 1;
    String title, eventName, actionName, e0, e1, e2, a0, a1, a2;




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    try {
                        setImage(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PICK_CONTACT:
                    if (resultCode == RESULT_OK) {
                        getContact(data);
                    }
        }
    }

    private void getContact(Intent data) {
        Uri contactUri = data.getData();
        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver()
                .query(contactUri, projection, null, null, null);
        cursor.moveToFirst();
        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String number = cursor.getString(column);
        MessageDialog obj = new MessageDialog(ActionSelectionActivity.this, ActionList.get(1), adapter);
        obj.create().show();
        View v = obj.getView();
        TextView textView = (TextView) v.findViewById(R.id.event_call_dialog_textView);
        textView.setText(number);
        adapter.data[1][0]=number;
    }

    private void setImage(Uri selectedImage) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
        WallpaperDialog obj = new WallpaperDialog(ActionSelectionActivity.this, ActionList.get(7), adapter);
        AlertDialog dialog = obj.create();
        dialog.show();
        View v = obj.getView();
        ImageView img = (ImageView) v.findViewById(R.id.action_wallpaper_imageview);
        img.setImageBitmap(bitmap);
        adapter.data[7][0]=String.valueOf(selectedImage);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity_main);
        Intent intent=getIntent();
        eventName=intent.getStringExtra("EVENT_NAME");
        title=intent.getStringExtra("TASK_TITLE");
        e0=intent.getStringExtra("E0");
        e1=intent.getStringExtra("E1");
        e2=intent.getStringExtra("E2");
        setToolbar();
        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.action_recycler_view);

        ActionList = new ArrayList<>();
        adapter = new ActionAdapter(this, ActionList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareActions();

        try {
            Glide.with(this).load(R.drawable.action_coverpage).into((ImageView) findViewById(R.id.action_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fonction for setting toolbar
     */
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Actions");
        toolbar.setTitleTextColor(getResources().getColor(R.color.iconsTint));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.iconsTint));
    }
    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.action_collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.action_appbar);
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
    private void prepareActions() {
        int[] covers = new int[]{
                R.drawable.profile,
                R.drawable.message,
                R.drawable.wifi,
                R.drawable.notify,
                R.drawable.loading_app,
                R.drawable.speakerphone,
                R.drawable.volume,
                R.drawable.wallpaper
        };

        Action a = new Action("Profile", covers[0]);
        ActionList.add(a);

        a = new Action("Message", covers[1]);
        ActionList.add(a);

        a = new Action("Wifi", covers[2]);
        ActionList.add(a);

        a = new Action("Notify",  covers[3]);
        ActionList.add(a);

        a = new Action("Load App",  covers[4]);
        ActionList.add(a);

        a = new Action("Speakerphone",  covers[5]);
        ActionList.add(a);

        a = new Action("Volume",  covers[6]);
        ActionList.add(a);

        a = new Action("Wallpaper",  covers[7]);
        ActionList.add(a);

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
                if(atLeastOneActionSelect())
                    startActivity(new Intent(ActionSelectionActivity.this, MainActivity.class));
                else {
                    new AlertDialog.Builder(ActionSelectionActivity.this)
                            .setTitle("Error")
                            .setMessage("Please select at least one Event")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                break;
            default:
                break;
        }

        return true;
    }
    public long getNextKey(final Class<? extends RealmObject> clazz) {
        Number nextID =  (realm.where(clazz).max("id"));
        if(nextID!=null)
            return nextID.longValue()+1;
        return 0;

    }

    private boolean atLeastOneActionSelect() {
        boolean ans=false;
        Realm.init(ActionSelectionActivity.this);
        realm=Realm.getDefaultInstance();
        Task task=new Task();
        task.id=getNextKey(Task.class);
        task.title=title;
        Event event=new Event();
        event.type=eventName;
        event.a0=e0;
        event.a1=e1;
        event.a2=e2;
        task.event=event;
        RealmList<com.example.nikhilr129.forgetitnot.Models.Action> actions=new RealmList<>();
        for(int i = 0; i < ActionList.size(); ++i) {
            if(ActionList.get(i).getSelected()){
                //Toast.makeText(this, ""+ActionList.get(i).getName(), Toast.LENGTH_LONG).show();
                com.example.nikhilr129.forgetitnot.Models.Action action=new com.example.nikhilr129.forgetitnot.Models.Action();
                action.type=""+ActionList.get(i).getName();
                if(ActionList.get(i).getName().equals("Profile")){
                    //Toast.makeText(this, ""+adapter.data[0][0], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[0][0];
                }else if(ActionList.get(i).getName().equals("Message")){
                    //Toast.makeText(this, ""+adapter.data[1][0]+" "+adapter.data[1][1], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[1][0];
                    action.a1=""+adapter.data[1][1];
                }else if(ActionList.get(i).getName().equals("Wifi")){
                    //Toast.makeText(this, ""+adapter.data[2][0], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[2][0];
                }else if(ActionList.get(i).getName().equals("Notify")){
                    //Toast.makeText(this, ""+adapter.data[3][0]+" "+adapter.data[3][1], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[3][0];
                }else if(ActionList.get(i).getName().equals("Load App")){
                    //Toast.makeText(this, ""+adapter.data[4][0], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[4][0];
                }else if(ActionList.get(i).getName().equals("Speakerphone")){
                    //Toast.makeText(this, ""+adapter.data[5][0], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[5][0];
                }else if(ActionList.get(i).getName().equals("Volume")){
                    //Toast.makeText(this, ""+adapter.data[6][0]+" "+adapter.data[6][1]+" "+adapter.data[6][2], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[6][0];
                    action.a1=""+adapter.data[6][1];
                    action.a2=""+adapter.data[6][2];
                }else if(ActionList.get(i).getName().equals("Wallpaper")){
                    //Toast.makeText(this, ""+adapter.data[7][0], Toast.LENGTH_LONG).show();
                    action.a0=""+adapter.data[7][0];
                }
                actions.add(action);
                ans= true;
            }
        }
        task.actions=actions;
        realm.beginTransaction();
        realm.copyToRealm(task);
        realm.commitTransaction();
        return ans;
    }
}
