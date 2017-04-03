package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.adapters.PersonsAdapter;
import nl.hu.fed.actortemplateapp.domain.Actor;

public class ShowActorContent extends AppCompatActivity {
    private String key, analist, TAG = "ShowPersons";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    private PersonsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_actor_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button newPerson = (Button) findViewById(R.id.button_new_person);
        newPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowActorContent.this, CreatePerson.class);
                i.putExtra("key", key);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("actors").child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        EditText rolenameET = (EditText) findViewById(R.id.rolenameView);
                        EditText taskdescriptionET = (EditText) findViewById(R.id.taskdescriptionView);

                        Actor actor = dataSnapshot.getValue(Actor.class);

                        String rolename = actor.getRolename();
                        rolenameET.setText(rolename);

                        String taskdescription = actor.getTaskdescription();
                        taskdescriptionET.setText(taskdescription);

                        analist = actor.getAnalist();

                        SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                        if (!analist.equals(userInfo.getString("userId", "NotSignedIn"))) {
                            rolenameET.setKeyListener(null);
                            taskdescriptionET.setKeyListener(null);
                            newPerson.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        //add a OnItemClickListener
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_persons);

        mAdapter = new PersonsAdapter(key);
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
                EditText rolenameET = (EditText) findViewById(R.id.rolenameView);
                EditText taskdescriptionET = (EditText) findViewById(R.id.taskdescriptionView);

                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("actors").child(key).child("rolename").setValue(rolenameET.getText().toString());
                mDatabase.child("actors").child(key).child("taskdescription").setValue(taskdescriptionET.getText().toString());
                finish();
                return true;
            }
            if (id == R.id.archiveItem) {
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("actors").child(key).child("archived").setValue(true);
                finish();
                return true;
            }
            if (id == R.id.deleteItem) {
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("actors").child(key).removeValue();
                finish();
                return true;
            }
        } else{
            Toast.makeText(this, this.getString(R.string.analistAction), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
