package nl.hu.fed.actortemplateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePerson extends AppCompatActivity {
    EditText nameET, emailET, functionET, phonenumberET, notesET, photoET;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //crashed als op terug wordt gedrukt. Komt door key.
        nameET = (EditText) findViewById(R.id.editTextName);
        emailET = (EditText) findViewById(R.id.editTextEmail);
        functionET = (EditText) findViewById(R.id.editTextFunction);
        phonenumberET = (EditText) findViewById(R.id.editTextPhonenumber);
        notesET = (EditText) findViewById(R.id.editTextNotes);
        photoET = (EditText) findViewById(R.id.editTextPhoto);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saveItem) {
            Person person = new Person();
            person.setName(nameET.getText().toString());
            person.setEmail(emailET.getText().toString());
            person.setFunction(functionET.getText().toString());
            person.setPhonenumber(phonenumberET.getText().toString());
            person.setNotes(notesET.getText().toString());
            person.setPhoto(photoET.getText().toString());

            Intent intent = getIntent();
            person.setActorkey(intent.getStringExtra("key"));

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("persons").push().setValue(person);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}