package com.example.joegl.mydribbble.view;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.dribbble.Dribbble;
import com.example.joegl.mydribbble.view.bucket_list.BucketListFragment;
import com.example.joegl.mydribbble.view.shot_list.ShotListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.drawer) NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // 这句话显示back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 这句话并不知道有什么卵用，听说官方文档有这句
        // 删掉似乎不影响。。。
        getSupportActionBar().setHomeButtonEnabled(true);
        
        setupDrawer();


        if (savedInstanceState == null) {
            Log.i("Test1", "savedInstanceState");
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, ShotListFragment.newInstance())
                    .commit();
        }
    }

    // allow the ActionBarToggle to handle the events
    // 三道杠的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,          /* DrawerLayout object */
//                toolbar,
                R.string.open_drawer,         /* "open drawer" description */
                R.string.close_drawer         /* "close drawer" description */
        );

        View headerView = navigationView.getHeaderView(0);

        ((TextView) headerView.findViewById(R.id.nav_header_user_name)).setText(
                Dribbble.getCurrentUser().name);

        headerView.findViewById(R.id.nav_header_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dribbble.logout(MainActivity.this);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // if the item is already selected, close drawer so no need to reload the fragment
                if (item.isChecked()) {
                    drawerLayout.closeDrawers();
                    return true;
                }

                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.drawer_item_home:
                        fragment = ShotListFragment.newInstance();
                        setTitle(R.string.title_home);
                        break;
                    case R.id.drawer_item_likes:
                        fragment = ShotListFragment.newInstance();
                        setTitle(R.string.title_likes);
                        break;
                    case R.id.drawer_item_buckets:
                        fragment = BucketListFragment.newInstance();
                        setTitle(R.string.title_buckets);
                        break;
                }

                drawerLayout.closeDrawers();

                // fragment != null if we selected some different item,
                // replace the current fragment
                // 实际上此处一定不为空，添加判空是防止以后被修改导致逻辑错误挂掉app
                if (fragment != null) {
                    Log.i("Test1", "fragment");
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    return true;
                }

                // in fact, this statement will never be executed since the fragment won't be null
                return false;
            }
        });
    }
}
