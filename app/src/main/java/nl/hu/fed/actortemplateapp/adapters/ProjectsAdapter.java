package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
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
    private String analystId;

    public ProjectsAdapter(String analyst) {
        super(Project.class, R.layout.row_project, ProjectsAdapter.MyViewHolder.class, //alleen niet gearchiveerde projecten tonen
                FirebaseDatabase.getInstance().getReference().child("projects").orderByChild("archived").equalTo(false));
        analystId = analyst;
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Project project, int position) {
        if(!project.isArchived()){
            viewHolder.title.setText(project.getTitle());
            viewHolder.description.setText(project.getDescription());
            viewHolder.project = project;
            //als user niet de analist is, editicoon niet tonen
            if(!analystId.equals(project.getAnalist())){
                viewHolder.analyst.setVisibility(View.INVISIBLE);
            }
            viewHolder.key = getRef(position).getKey();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, description;
        public ImageView analyst;
        public Project project;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.rowProject_title);
            description = (TextView) view.findViewById(R.id.rowProject_description);
            analyst = (ImageView) view.findViewById(R.id.rowProject_analyst);
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

