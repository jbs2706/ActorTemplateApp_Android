package nl.hu.fed.actortemplateapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.activity.ShowProjectContent;
import nl.hu.fed.actortemplateapp.domain.Project;

public class ProjectsAdapter extends FirebaseRecyclerAdapter<Project, ProjectsAdapter.MyViewHolder> {
    private String analistId;

    public ProjectsAdapter(String analist) {
        super(Project.class, R.layout.row_project, ProjectsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("projects"));
        analistId = analist;

    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Project project, int position) {
        viewHolder.title.setText(project.getTitle());
        viewHolder.description.setText(project.getDescription());
        viewHolder.project = project;

        if(!analistId.equals(project.getAnalist())){
            viewHolder.analist.setVisibility(View.INVISIBLE);
        }

        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, description;
        public ImageView analist;
        public Project project;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.descriptionProject);
            analist = (ImageView) view.findViewById(R.id.analistProject);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ShowProjectContent.class);
            intent.putExtra("key", key);
            v.getContext().startActivity(intent);
        }
    }
}

