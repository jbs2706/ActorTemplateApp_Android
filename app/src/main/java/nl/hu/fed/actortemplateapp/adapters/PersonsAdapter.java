package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.activity.ShowPersonContent;
import nl.hu.fed.actortemplateapp.cameraMethods.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;

public class PersonsAdapter extends FirebaseRecyclerAdapter<Person, PersonsAdapter.MyViewHolder> {

    private CameraFunctions cameraFunctions = new CameraFunctions();

    public PersonsAdapter(String actorKey) {
        super(Person.class, R.layout.row_person, PersonsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("persons").orderByChild("actorKey").equalTo(actorKey));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Person person, int position) {
        viewHolder.name.setText(person.getName());
        viewHolder.email.setText(person.getEmail());
        viewHolder.function.setText(person.getFunction());
        viewHolder.phonenumber.setText(person.getPhonenumber());
        viewHolder.notes.setText(person.getNotes());
        viewHolder.photo.setImageBitmap(cameraFunctions.fromStringToImage(person.getPhoto()));
        viewHolder.person = person;
        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, email, function, phonenumber, notes;
        public ImageView photo;
        public Person person;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            function = (TextView) view.findViewById(R.id.function);
            phonenumber = (TextView) view.findViewById(R.id.phonenumber);
            notes = (TextView) view.findViewById(R.id.notes);
            photo = (ImageView) view.findViewById(R.id.photo);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ShowPersonContent.class);
            intent.putExtra("key", key);
            v.getContext().startActivity(intent);
        }
    }
}