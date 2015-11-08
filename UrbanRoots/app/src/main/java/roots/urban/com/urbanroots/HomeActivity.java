package roots.urban.com.urbanroots;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import adapter.ProjectListAdapter;
import entity.Project;
import helper.API;
import helper.UrbanRootSharedPreferenceHelper;
import listener.ProjectDataListener;

public class HomeActivity extends UrbanRootActivity implements ProjectDataListener {

    private ProjectListAdapter adapter;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(UrbanRootSharedPreferenceHelper.getBoolean(this, "login")){
            setTitle("My Projects");
            API.getProjects(this, this, "/" + UrbanRootSharedPreferenceHelper.getString(this, "email"));
            API.getProjects(this, this);
        } else {
            setTitle("Project List");
            API.getProjects(this, this);
        }

        initView();
    }

    private void initView(){
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        adapter = new ProjectListAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public synchronized void onError() {

    }

    @Override
    public synchronized void onSuccess(List<Project> projects) {
        counter++;

        if(counter == 1) {
            adapter.setData(projects);
        }else{
            adapter.updateData(projects);
        }
    }
}
