package nl.hu.fed.actortemplateapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Project;

public class ArchiveProjectsAdapter extends FirebaseRecyclerAdapter<Project, ArchiveProjectsAdapter.MyViewHolder> {

    public ArchiveProjectsAdapter() {
        super(Project.class, R.layout.row_archive_project, ArchiveProjectsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("projects").orderByChild("archived").equalTo(true));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Project project, int position) {
            viewHolder.title.setText(project.getTitle());
            viewHolder.description.setText(project.getDescription());
            viewHolder.project = project;
            viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, description;
        public Project project;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.archiveProjectTitle);
            description = (TextView) view.findViewById(R.id.archiveProjectDescription);
        }
    }
}

