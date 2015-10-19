package com.cmpe.ni.mytube;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cmpe.ni.mytube.R;

public class VideoListActivity extends ActionBarActivity {

    private ViewPager viewPager;
    private TabsPagerAdapter tabsAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Search", "Favorite"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // When the tab is selected, switch to the
                // corresponding page in the ViewPager.
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabsAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(tabListener));
        }



    }

}
