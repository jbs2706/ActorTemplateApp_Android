package nl.hu.fed.actortemplateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowPersonContent extends AppCompatActivity {
    String key;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_person_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("persons").child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TextView nameField = (TextView)findViewById(R.id.nameView);
                        TextView emailField = (TextView) findViewById(R.id.emailView);
                        TextView functionField = (TextView) findViewById(R.id.functionView);
                        TextView phonenumberField = (TextView) findViewById(R.id.phonenumberView);
                        TextView notesField = (TextView) findViewById(R.id.notesView);
                        TextView photoField = (TextView) findViewById(R.id.photoView);

                        Person person = dataSnapshot.getValue(Person.class);

                        String name = person.getName();
                        nameField.setText(name);

                        String email = person.getEmail();
                        emailField.setText(email);

                        String function = person.getFunction();
                        functionField.setText(function);

                        String phonenumber = person.getPhonenumber();
                        phonenumberField.setText(phonenumber);

                        String notes = person.getNotes();
                        notesField.setText(notes);

                        String photo = person.getPhoto();
                        photoField.setText(photo);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.deleteitem) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("persons").child(key).removeValue();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
