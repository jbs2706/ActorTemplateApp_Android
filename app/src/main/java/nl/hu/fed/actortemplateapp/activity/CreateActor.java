package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Actor;
import nl.hu.fed.actortemplateapp.domain.Project;

public class CreateActor extends BaseActivity {
    private EditText roleET, descriptionET;
    private DatabaseReference mDatabase;
    private static final String TAG = "CreateActor";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_actor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        roleET = (EditText) findViewById(R.id.aCreateActor_roleNameEt);
        descriptionET = (EditText) findViewById(R.id.aCreateActor_descriptionEt);

        Intent intent = getIntent();
        String projectName = intent.getStringExtra("project"); //wordt bovenaan scherm getoond
        TextView projectTV = (TextView) findViewById(R.id.aCreateActor_projectTv);
        projectTV.setText("Project: " + projectName);

        FloatingActionButton saveFab = (FloatingActionButton) findViewById(R.id.aCreateActor_saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(roleET.getText().toString())) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.emptyActorName), Toast.LENGTH_SHORT).show();
                }else {
                    Actor actor = new Actor();
                    actor.setRolename(roleET.getText().toString());
                    actor.setTaskdescription(descriptionET.getText().toString());
                    Intent intent = getIntent();
                    actor.setProjectKey(intent.getStringExtra("key"));

                    SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                    actor.setAnalist(userInfo.getString("userId", "NotSignedIn"));

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("actors").push().setValue(actor);

                    finish();
                }
            }
        });
    }
}