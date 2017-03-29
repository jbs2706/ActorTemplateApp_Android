package nl.hu.fed.actortemplateapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.MyViewHolder> {

    private List<Actor> actorList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private String TAG = "ActorsAdapter";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rolename, description;

        public MyViewHolder(View view) {
            super(view);
            rolename = (TextView) view.findViewById(R.id.rolename);
            description = (TextView) view.findViewById(R.id.descriptionActor);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Actor newActor = actorList.get(pos);
            Intent intent = new Intent(v.getContext(), ShowActorContent.class);
            intent.putExtra("key", newActor.key);
            v.getContext().startActivity(intent);
        }
    }

    public ActorsAdapter() {
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("actors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Actor e = dataSnapshot.getValue(Actor.class);
                e.key = dataSnapshot.getKey();
                actorList.add(e);
                notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("actors").child(dataSnapshot.getKey()).removeValue();
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_actor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Actor actor = actorList.get(position);
        holder.rolename.setText(actor.getRolename());
        holder.description.setText(actor.getTaskdescription());
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }
}
