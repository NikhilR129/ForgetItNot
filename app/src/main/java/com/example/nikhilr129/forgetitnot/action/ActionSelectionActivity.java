package com.example.nikhilr129.forgetitnot.action;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nikhilr129.forgetitnot.R;
import com.example.nikhilr129.forgetitnot.action.actionDialog.WallpaperDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanchicoder on 4/10/17.
 */

public class ActionSelectionActivity extends AppCompatActivity{
    private Toolbar toolbar;
    public Bitmap bmp = null;
    private RecyclerView recyclerView;
    private ActionAdapter adapter;
    private List<Action> ActionList;

    //test done by nikhil
    private final int SELECT_PHOTO = 0;
    private void setImage(Uri selectedImage) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
        WallpaperDialog obj = new WallpaperDialog(ActionSelectionActivity.this);
        AlertDialog dialog = obj.create();
        dialog.show();
        View v = obj.getView();
        ImageView img = (ImageView) v.findViewById(R.id.action_wallpaper_imageview);
        img.setImageBitmap(bitmap);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        setImage(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity_main);
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
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

}
