package roots.urban.com.urbanroots;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class UrbanRootActivity extends AppCompatActivity {

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

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.addView(LayoutInflater.from(this).inflate(layoutResId, null));
    }

    protected void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
