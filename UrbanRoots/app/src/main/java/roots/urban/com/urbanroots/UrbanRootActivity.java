package roots.urban.com.urbanroots;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import helper.UrbanRootSharedPreferenceHelper;

public class UrbanRootActivity extends AppCompatActivity {

    private static final String REPORT_VANDALISM = "Report Vandalism";
    private static final String REPORT_GENERAL = "Report General";

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
                toolbar, R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
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
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                Intent intent = ReportActivity.createIntent(UrbanRootActivity.this, REPORT_VANDALISM);
                startActivity(intent);
            }
        });

        findViewById(R.id.general).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                Intent intent = ReportActivity.createIntent(UrbanRootActivity.this, REPORT_GENERAL);
                startActivity(intent);
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(UrbanRootActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                UrbanRootSharedPreferenceHelper.putString(UrbanRootActivity.this, UrbanRootSharedPreferenceHelper.EMAIL, "");
                UrbanRootSharedPreferenceHelper.putBoolean(UrbanRootActivity.this, UrbanRootSharedPreferenceHelper.LOGIN, false);

                Intent intent = new Intent(UrbanRootActivity.this, HomeActivity.class);
                startActivity(intent);

                finish();
            }
        });

        if(UrbanRootSharedPreferenceHelper.getBoolean(this, UrbanRootSharedPreferenceHelper.LOGIN)){
            findViewById(R.id.login).setVisibility(View.GONE);
            findViewById(R.id.logout).setVisibility(View.VISIBLE);
        } else{
            findViewById(R.id.login).setVisibility(View.VISIBLE);
            findViewById(R.id.logout).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
