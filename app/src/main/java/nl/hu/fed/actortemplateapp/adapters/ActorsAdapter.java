package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.activity.ShowActorContent;
import nl.hu.fed.actortemplateapp.domain.Actor;

public class ActorsAdapter extends FirebaseRecyclerAdapter<Actor, ActorsAdapter.MyViewHolder> {

    public static String projectName;
    //parameters zijn de key van het project en de naam van het project
    //de key is nodig voor het filteren in firebase op actoren die aan dit project gekoppeld zijn
    //de naam is nodig voor het tonen van de project in het scherm
    public ActorsAdapter(String projectKey, String projectN) {
        super(Actor.class, R.layout.row_actor, ActorsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("actors").orderByChild("projectKey").equalTo(projectKey));
        projectName = projectN;
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Actor actor, int position) {
        if(!actor.isArchived()){
            viewHolder.rolename.setText(actor.getRolename());
            viewHolder.description.setText(actor.getTaskdescription());
            viewHolder.actor = actor;
            viewHolder.key = getRef(position).getKey();
        }else{ //als actor gearchiveerd is, niet tonen (kan maar 1 order by gebruiken in firebase query)
            viewHolder.rolename.setVisibility(View.GONE);
            viewHolder.description.setVisibility(View.GONE);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rolename, description;
        public Actor actor;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            rolename = (TextView) view.findViewById(R.id.rowActor_rolename);
            description = (TextView) view.findViewById(R.id.rowActor_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ShowActorContent.class);
            intent.putExtra("key", key);
            intent.putExtra("project", projectName);//geef de projectnaam door zodat deze in het scherm getoond kan worden
            v.getContext().startActivity(intent);
        }
    }
}

