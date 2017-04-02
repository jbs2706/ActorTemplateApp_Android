package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.cameraMethods.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;

public class ShowPersonContent extends AppCompatActivity {
    String key;
    private CameraFunctions cameraFunctions = new CameraFunctions();
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
                        ImageView photoField = (ImageView) findViewById(R.id.photoView);

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

                        photoField.setImageBitmap(cameraFunctions.fromStringToImage(person.getPhoto()));

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


        if (id == R.id.archiveItem) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("persons").child(key).child("archived").setValue(true);
            finish();
            return true;
        }
        if (id == R.id.deleteItem) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("persons").child(key).removeValue();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
