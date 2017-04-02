package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.activity.ShowProjectContent;
import nl.hu.fed.actortemplateapp.domain.Project;

public class ProjectsAdapter extends FirebaseRecyclerAdapter<Project, ProjectsAdapter.MyViewHolder> {

    public ProjectsAdapter() {
        super(Project.class, R.layout.row_project, ProjectsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("projects"));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Project project, int position) {
        viewHolder.title.setText(project.getTitle());
        viewHolder.description.setText(project.getDescription());
        viewHolder.project = project;
        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, description;
        public Project project;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.descriptionProject);
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

