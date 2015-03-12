package com.example.materialdrawerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Drawer.Result result = null;

    private List<Fragment> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Preload our navigation fragments
        LoadPages();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle Toolbar
        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.navdrawer_header)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIdentifier(0),
                        new PrimaryDrawerItem().withName("Keyboard Demo").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Keyboard Demo #2").withIdentifier(2)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {

                            if (drawerItem != null) {

                                Fragment target = pages.get(drawerItem.getIdentifier());
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, target).commit();
                            }
                        }
                    }
                }).build();

        ImageView drawerImage = (ImageView)findViewById(R.id.account_header_drawer_background);
        drawerImage.setImageResource(R.drawable.header_test);

        //Load home fragment as default...
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pages.get(0)).commit();

    }

    @Override
    public void onBackPressed() {
        //close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    private void LoadPages()
    {
        pages = new ArrayList<Fragment>();

        pages.add(new HomeFragment());
        pages.add(new DemoFragment());
        pages.add(new DemoNoScroll());
    }
}