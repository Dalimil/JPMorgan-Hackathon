package roots.urban.com.urbanroots;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import helper.UrbanRootSharedPreferenceHelper;

public class UrbanRootActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout llMenu;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResId){
        super.setContentView(R.layout.activity_urban_root);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        container.addView(LayoutInflater.from(this).inflate(layoutResId, null));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.main_logo, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        llMenu = (LinearLayout) findViewById(R.id.left_menu);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        initMenu();
    }

    private void initMenu(){
        findViewById(R.id.vandalism).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrbanRootActivity.this, ReportActivity.class);
                intent.putExtra("title", "Report Vandalism");
                startActivity(intent);
            }
        });

        findViewById(R.id.general).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrbanRootActivity.this, ReportActivity.class);
                intent.putExtra("title", "Report General");
                startActivity(intent);
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrbanRootActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrbanRootSharedPreferenceHelper.putString(UrbanRootActivity.this, "email", "");
                UrbanRootSharedPreferenceHelper.putBoolean(UrbanRootActivity.this, "login", false);

                Intent intent = new Intent(UrbanRootActivity.this, HomeActivity.class);
                startActivity(intent);

                finish();
            }
        });

        if(UrbanRootSharedPreferenceHelper.getBoolean(this, "login")){
            findViewById(R.id.login).setVisibility(View.GONE);
            findViewById(R.id.logout).setVisibility(View.VISIBLE);
        } else{
            findViewById(R.id.login).setVisibility(View.VISIBLE);
            findViewById(R.id.logout).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    protected void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
