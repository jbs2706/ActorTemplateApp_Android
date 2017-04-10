package nl.hu.fed.actortemplateapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.domain.Project;

public class CreateProject extends AppCompatActivity {
    private EditText titleET, descriptionET;
    private DatabaseReference mDatabase;
    private static final String TAG = "CreateProject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleET = (EditText) findViewById(R.id.aCreateProject_titleEt);
        descriptionET = (EditText) findViewById(R.id.aCreateProject_descriptionEt);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
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
            if(TextUtils.isEmpty(titleET.getText().toString())) { //valideer projectnaam
                Toast.makeText(this, this.getString(R.string.emptyProjectName), Toast.LENGTH_SHORT).show();
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

        return super.onOptionsItemSelected(item);
    }

}
