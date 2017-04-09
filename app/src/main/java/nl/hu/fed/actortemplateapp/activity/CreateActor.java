package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Actor;

public class CreateActor extends AppCompatActivity {
    EditText roleET, descriptionET;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_actor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //crashed als op terug wordt gedrukt. Komt door key.
        roleET = (EditText) findViewById(R.id.editTextRoleName);
        descriptionET = (EditText) findViewById(R.id.editTextDescriptionActor);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String projectName = intent.getStringExtra("project");

        TextView projectTV = (TextView) findViewById(R.id.textViewCreateActorProject);
        projectTV.setText("Project: " + projectName);
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
            Actor actor = new Actor();
            actor.setRolename(roleET.getText().toString());
            actor.setTaskdescription(descriptionET.getText().toString());
            Intent intent = getIntent();
            actor.setProjectKey(intent.getStringExtra("key"));

            SharedPreferences userInfo = getSharedPreferences("USERID", 0);
            actor.setAnalist(userInfo.getString("userId", "NotSignedIn"));

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("actors").push().setValue(actor);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}