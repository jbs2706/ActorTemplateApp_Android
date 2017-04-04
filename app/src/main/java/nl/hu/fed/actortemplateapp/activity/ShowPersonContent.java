package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.camera.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;

public class ShowPersonContent extends AppCompatActivity {
    private String key, analist;
    private CameraFunctions cameraFunctions = new CameraFunctions();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

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
                        EditText nameField = (EditText)findViewById(R.id.nameView);
                        EditText emailField = (EditText) findViewById(R.id.emailView);
                        EditText functionField = (EditText) findViewById(R.id.functionView);
                        EditText phonenumberField = (EditText) findViewById(R.id.phonenumberView);
                        EditText notesField = (EditText) findViewById(R.id.notesView);
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

                        analist = person.getAnalist();

                        SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                        if(!analist.equals(userInfo.getString("userId", "NotSignedIn"))) {
                            nameField.setKeyListener(null);
                            emailField.setKeyListener(null);
                            functionField.setKeyListener(null);
                            phonenumberField.setKeyListener(null);
                            notesField.setKeyListener(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
        addListenerOnImageButton();
    }

    public void addListenerOnImageButton() {
        ImageButton imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText phonenumberField = (EditText) findViewById(R.id.phonenumberView);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonenumberField.getText().toString()));
                startActivity(intent);
            }
        });
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

        SharedPreferences userInfo = getSharedPreferences("USERID", 0);
        if(analist.equals(userInfo.getString("userId", "NotSignedIn"))) {
            if (id == R.id.editItem) {
                EditText nameField = (EditText) findViewById(R.id.nameView);
                EditText emailField = (EditText) findViewById(R.id.emailView);
                EditText functionField = (EditText) findViewById(R.id.functionView);
                EditText phonenumberField = (EditText) findViewById(R.id.phonenumberView);
                EditText notesField = (EditText) findViewById(R.id.notesView);
                ImageView photoField = (ImageView) findViewById(R.id.photoView);

                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("persons").child(key).child("name").setValue(nameField.getText().toString());
                mDatabase.child("persons").child(key).child("email").setValue(emailField.getText().toString());
                mDatabase.child("persons").child(key).child("function").setValue(functionField.getText().toString());
                mDatabase.child("persons").child(key).child("phonenumber").setValue(phonenumberField.getText().toString());
                mDatabase.child("persons").child(key).child("notes").setValue(notesField.getText().toString());
                mDatabase.child("persons").child(key).child("photo").setValue(cameraFunctions.fromImageToString((BitmapDrawable) photoField.getDrawable()));

                finish();
                return true;
            }
            if (id == R.id.archiveItem) {
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("persons").child(key).child("archived").setValue(true);
                finish();
                return true;
            }
            if (id == R.id.deleteItem) {
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("persons").child(key).removeValue();
                finish();
                return true;
            }
        } else{
            Toast.makeText(this, this.getString(R.string.analistAction), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
