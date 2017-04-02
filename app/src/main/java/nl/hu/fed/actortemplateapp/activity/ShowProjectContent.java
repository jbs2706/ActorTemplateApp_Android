package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.adapters.ActorsAdapter;
import nl.hu.fed.actortemplateapp.domain.Project;

public class ShowProjectContent extends AppCompatActivity {
    private String key, analist, TAG = "ShowActors";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    private ActorsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button newActor = (Button) findViewById(R.id.button_new_actor);
        newActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowProjectContent.this, CreateActor.class);
                i.putExtra("key", key);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("projects").child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        EditText titleET = (EditText)findViewById(R.id.titleView);
                        EditText descriptionET = (EditText) findViewById(R.id.descriptionView);

                        Project project = dataSnapshot.getValue(Project.class);

                        String title = project.getTitle();
                        titleET.setText(title);

                        String content = project.getDescription();
                        descriptionET.setText(content);

                        analist = project.getAnalist();

                        SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                        if(!analist.equals(userInfo.getString("userId", "NotSignedIn"))) {
                            titleET.setKeyListener(null);
                            descriptionET.setKeyListener(null);
                            newActor.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        //add a OnItemClickListener
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_actors);

        mAdapter = new ActorsAdapter(key);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
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
                EditText titleET = (EditText) findViewById(R.id.titleView);
                EditText descriptionET = (EditText) findViewById(R.id.descriptionView);

                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("projects").child(key).child("title").setValue(titleET.getText().toString());
                mDatabase.child("projects").child(key).child("description").setValue(descriptionET.getText().toString());

                finish();
                return true;
            }
            if (id == R.id.archiveItem) {
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("projects").child(key).child("archived").setValue(true);
                finish();
                return true;
            }
            if (id == R.id.deleteItem) {
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("projects").child(key).removeValue();
                finish();
                return true;
            }
        } else{
            Toast.makeText(this, this.getString(R.string.analistAction), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
