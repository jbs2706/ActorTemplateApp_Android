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

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.MyViewHolder> {

    private List<Person> personList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private String TAG = "PersonsAdapter";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, email, function, phonenumber, notes, photo;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            function = (TextView) view.findViewById(R.id.function);
            phonenumber = (TextView) view.findViewById(R.id.phonenumber);
            notes = (TextView) view.findViewById(R.id.notes);
            photo = (TextView) view.findViewById(R.id.photo);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Person newPerson = personList.get(pos);
            Intent intent = new Intent(v.getContext(), ShowPersonContent.class);
            intent.putExtra("key", newPerson.key);
            v.getContext().startActivity(intent);
        }
    }

    public PersonsAdapter() {
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("persons").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Person person = dataSnapshot.getValue(Person.class);
                person.key = dataSnapshot.getKey();
                personList.add(person);
                notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("persons").child(dataSnapshot.getKey()).removeValue();
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
                .inflate(R.layout.row_person, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.name.setText(person.getName());
        holder.email.setText(person.getEmail());
        holder.function.setText(person.getFunction());
        holder.phonenumber.setText(person.getPhonenumber());
        holder.notes.setText(person.getPhoto());
        holder.photo.setText(person.getPhoto());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}

