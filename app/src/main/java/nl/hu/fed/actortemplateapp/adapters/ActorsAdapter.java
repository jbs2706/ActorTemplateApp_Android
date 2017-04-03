package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import nl.hu.fed.actortemplateapp.R;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import nl.hu.fed.actortemplateapp.activity.ShowActorContent;
import nl.hu.fed.actortemplateapp.domain.Actor;

public class ActorsAdapter extends FirebaseRecyclerAdapter<Actor, ActorsAdapter.MyViewHolder> {

    public ActorsAdapter(String projectKey) {
        super(Actor.class, R.layout.row_actor, ActorsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("actors").orderByChild("projectKey").equalTo(projectKey));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Actor actor, int position) {
        viewHolder.rolename.setText(actor.getRolename());
        viewHolder.description.setText(actor.getTaskdescription());
        viewHolder.actor = actor;
        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rolename, description;
        public Actor actor;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            rolename = (TextView) view.findViewById(R.id.rolename);
            description = (TextView) view.findViewById(R.id.descriptionActor);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ShowActorContent.class);
            intent.putExtra("key", key);
            v.getContext().startActivity(intent);
        }
    }
}

