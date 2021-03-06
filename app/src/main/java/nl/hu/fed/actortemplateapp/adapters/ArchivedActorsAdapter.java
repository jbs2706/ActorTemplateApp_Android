package nl.hu.fed.actortemplateapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Actor;

public class ArchivedActorsAdapter extends FirebaseRecyclerAdapter<Actor, ArchivedActorsAdapter.MyViewHolder> {

    public ArchivedActorsAdapter() { //haalt alle actors op waar archived true is
        super(Actor.class, R.layout.row_archived_actor, ArchivedActorsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("actors").orderByChild("archived").equalTo(true));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Actor actor, int position) {
        viewHolder.rolename.setText(actor.getRolename());
        viewHolder.description.setText(actor.getTaskdescription());
        viewHolder.actor = actor;
        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rolename, description;
        public Actor actor;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            rolename = (TextView) view.findViewById(R.id.rowArchivedActor_rolename);
            description = (TextView) view.findViewById(R.id.rowArchivedActor_description);
        }

    }
}

