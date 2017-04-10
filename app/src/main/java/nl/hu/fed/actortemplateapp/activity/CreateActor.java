package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Actor;

public class CreateActor extends AppCompatActivity {
    private EditText roleET, descriptionET;
    private DatabaseReference mDatabase;
    private static final String TAG = "CreateActor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_actor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //crashed als op terug wordt gedrukt. Komt door key. //TODO dit
        roleET = (EditText) findViewById(R.id.aCreateActor_roleNameEt);
        descriptionET = (EditText) findViewById(R.id.aCreateActor_descriptionEt);

        Intent intent = getIntent();
        String projectName = intent.getStringExtra("project"); //wordt bovenaan scherm getoond
        TextView projectTV = (TextView) findViewById(R.id.aCreateActor_projectTv);
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

        if (id == R.id.saveItem) { //valideer actornaam
            if(TextUtils.isEmpty(roleET.getText().toString())) {
                Toast.makeText(this, this.getString(R.string.emptyActorName), Toast.LENGTH_SHORT).show();
            }else {
                Actor actor = new Actor();
                actor.setRolename(roleET.getText().toString());
                actor.setTaskdescription(descriptionET.getText().toString());
                Intent intent = getIntent();
                actor.setProjectKey(intent.getStringExtra("key"));

                SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                actor.setAnalyst(userInfo.getString("userId", "NotSignedIn"));

                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("actors").push().setValue(actor);

                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}