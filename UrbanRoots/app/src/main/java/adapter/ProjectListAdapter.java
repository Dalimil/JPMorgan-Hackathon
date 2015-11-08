package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.Project;
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
        public TextView tvLat;
        public TextView tvLng;
        public TextView tvAddress;
        public TextView tvDescription;


        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.name);
            tvNumPeople = (TextView) view.findViewById(R.id.num_people);
            tvLat = (TextView) view.findViewById(R.id.lat);
            tvLng = (TextView) view.findViewById(R.id.lng);
            tvAddress = (TextView) view.findViewById(R.id.address);
            tvDescription = (TextView) view.findViewById(R.id.description);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvName.setText(projects.get(position).getName());
        holder.tvNumPeople.setText(projects.get(position).getNum_people());
        holder.tvLat.setText(projects.get(position).getLat());
        holder.tvLng.setText(projects.get(position).getLng());
        holder.tvAddress.setText(projects.get(position).getAddress());
        holder.tvDescription.setText(projects.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return projects.size();
    }
}
