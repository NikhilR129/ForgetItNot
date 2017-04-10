package com.example.nikhilr129.forgetitnot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {


    private  FloatingActionMenu materialDesignFAM;
    private  FloatingActionButton floatingActionButton1;
    private  BottomBar  bottomNavigationBar;
    private  Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar support in android
        setToolbar();

        //Create and applying action on floating action Menu
        createAndApplyActionOnFloatingActionMenu();

        //create BottomBar and add Tab Click Listener
        createBottomBarAndClickListener();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.iconsTint));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.iconsTint));
    }
    private void createAndApplyActionOnFloatingActionMenu() {
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item1);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                startActivity(new Intent(MainActivity.this, SelectEventConditionAction.class));
                finish();
            }
        });
    }
    private void createBottomBarAndClickListener() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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
                String shareBody = "Check out this awesome app.It automates most of the boring tasks in Android";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ForgetItNot");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.aboutUs:
                Toast.makeText(this, "About Us selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }
}
