package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.activity.ShowPersonContent;
import nl.hu.fed.actortemplateapp.camera.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;

public class ArchivePersonsAdapter extends FirebaseRecyclerAdapter<Person, ArchivePersonsAdapter.MyViewHolder> {

    private int selectedPos = 0;

    public ArchivePersonsAdapter() {
        super(Person.class, R.layout.archive_row_person, ArchivePersonsAdapter.MyViewHolder.class,
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
            name = (TextView) view.findViewById(R.id.archivePersonName);
            email = (TextView) view.findViewById(R.id.archivePersonEmail);
            function = (TextView) view.findViewById(R.id.archivePersonFunction);
        }

    }
}