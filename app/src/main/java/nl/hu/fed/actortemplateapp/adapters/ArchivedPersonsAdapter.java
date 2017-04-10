package nl.hu.fed.actortemplateapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Person;

public class ArchivedPersonsAdapter extends FirebaseRecyclerAdapter<Person, ArchivedPersonsAdapter.MyViewHolder> {

    public ArchivedPersonsAdapter() { //haalt alle persons op waar archived true is
        super(Person.class, R.layout.row_archived_person, ArchivedPersonsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("persons").orderByChild("archived").equalTo(true));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Person person, int position) {
        viewHolder.name.setText(person.getName());
        viewHolder.email.setText(person.getEmail());
        viewHolder.person = person;
        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email, function;
        public Person person;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.rowArchivedPerson_name);
            email = (TextView) view.findViewById(R.id.rowArchivedPerson_email);
            function = (TextView) view.findViewById(R.id.rowArchivedPerson_function);
        }

    }
}