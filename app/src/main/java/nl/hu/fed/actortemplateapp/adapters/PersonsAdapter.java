package nl.hu.fed.actortemplateapp.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.activity.ShowPersonContent;
import nl.hu.fed.actortemplateapp.camera.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;

public class PersonsAdapter extends FirebaseRecyclerAdapter<Person, PersonsAdapter.MyViewHolder> {

    private CameraFunctions cameraFunctions = new CameraFunctions();

    public PersonsAdapter(String actorKey) {
        super(Person.class, R.layout.row_person, PersonsAdapter.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("persons").orderByChild("actorKey").equalTo(actorKey));
    }

    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, Person person, int position) {
        if (!person.isArchived()){
            viewHolder.name.setText(person.getName());
            viewHolder.email.setText(person.getEmail());
            viewHolder.function.setText(person.getFunction());
            viewHolder.phonenumber.setText(person.getPhonenumber());
            viewHolder.photo.setImageBitmap(cameraFunctions.fromStringToImage(person.getPhoto()));
            viewHolder.person = person;
            viewHolder.key = getRef(position).getKey();
        }else{
            viewHolder.name.setVisibility(View.GONE);
            viewHolder.email.setVisibility(View.GONE);
            viewHolder.function.setVisibility(View.GONE);
            viewHolder.phonenumber.setVisibility(View.GONE);
            viewHolder.photo.setVisibility(View.GONE);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, email, function, phonenumber;
        public ImageView photo;
        public Person person;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            function = (TextView) view.findViewById(R.id.function);
            phonenumber = (TextView) view.findViewById(R.id.phonenumber);
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