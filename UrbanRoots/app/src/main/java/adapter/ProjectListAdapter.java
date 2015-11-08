package adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.Project;
import helper.API;
import helper.UrbanRootSharedPreferenceHelper;
import roots.urban.com.urbanroots.LoginActivity;
import roots.urban.com.urbanroots.R;

/**
 * Created by whdinata on 11/7/15.
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private List<Project> projects = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvNumPeople;
        public TextView tvDate;
        public TextView tvLat;
        public TextView tvLng;
        public TextView tvAddress;
        public TextView tvDescription;
        public Button btJoin;


        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.name);
            tvNumPeople = (TextView) view.findViewById(R.id.num_people);
            tvDate = (TextView) view.findViewById(R.id.date);
            tvLat = (TextView) view.findViewById(R.id.lat);
            tvLng = (TextView) view.findViewById(R.id.lng);
            tvAddress = (TextView) view.findViewById(R.id.address);
            tvDescription = (TextView) view.findViewById(R.id.description);
            btJoin = (Button) view.findViewById(R.id.join);
        }
    }

    public ProjectListAdapter(){
    }

    public ProjectListAdapter(List<Project> projects) {
        this.projects = projects;
    }

    public void setData(List<Project> projects){
        this.projects = projects;
        notifyDataSetChanged();
    }

    public void updateData(List<Project> userProjects){
        List<Project> temps;

        if(userProjects.size() > projects.size()){
            temps = userProjects;
            userProjects = projects;
            projects = temps;
        }

        for(Project userProject : userProjects){
            for(Project project : projects) {
                if (userProject.getId().equals(project.getId())){
                    project.setIsJoin(true);
                    break;
                }
            }
        }

        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProjectListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvName.setText(projects.get(position).getName());
        holder.tvNumPeople.setText(projects.get(position).getNum_people());
        holder.tvDate.setText(projects.get(position).getDate());
//        holder.tvLat.setText(projects.get(position).getLat());
//        holder.tvLng.setText(projects.get(position).getLng());
        holder.tvLat.setVisibility(View.GONE);
        holder.tvLng.setVisibility(View.GONE);
        holder.tvAddress.setText(projects.get(position).getAddress());
        holder.tvDescription.setText(projects.get(position).getDescription());

        holder.btJoin.setTag(projects.get(position).getId());

        if(projects.get(position).isJoin()) {
            holder.btJoin.setText("Cancel");
            holder.btJoin.setBackgroundResource(R.drawable.button_rounded_red);
        } else{
            holder.btJoin.setText("Join");
            holder.btJoin.setBackgroundResource(R.drawable.button_rounded);
        }

        holder.btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UrbanRootSharedPreferenceHelper.getBoolean(v.getContext(), "login")){
                    if(holder.btJoin.getText().toString().equalsIgnoreCase("join")){
                        API.join(v.getContext(), UrbanRootSharedPreferenceHelper.getString(v.getContext(), "email"), (String)v.getTag());
                    } else{
                        API.cancel(v.getContext(), UrbanRootSharedPreferenceHelper.getString(v.getContext(), "email"), (String) v.getTag());
                    }
                } else{
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return projects.size();
    }
}
