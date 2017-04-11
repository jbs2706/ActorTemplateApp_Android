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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Project;

public class CreateProject extends BaseActivity {
    private EditText titleET, descriptionET;
    private DatabaseReference mDatabase;
    private static final String TAG = "CreateProject";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleET = (EditText) findViewById(R.id.aCreateProject_titleEt);
        descriptionET = (EditText) findViewById(R.id.aCreateProject_descriptionEt);
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        FloatingActionButton saveFab = (FloatingActionButton) findViewById(R.id.aCreateProject_saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(titleET.getText().toString())) { //valideer projectnaam
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.emptyProjectName), Toast.LENGTH_SHORT).show();
                }
                else {
                    Project project = new Project();
                    project.setTitle(titleET.getText().toString());
                    project.setDescription(descriptionET.getText().toString());

                    SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                    project.setAnalist(userInfo.getString("userId", "NotSignedIn"));

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("projects").push().setValue(project);

                    finish();
                }
            }
        });
    }
}
