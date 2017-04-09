package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.adapters.ProjectsAdapter;
import nl.hu.fed.actortemplateapp.domain.User;

public class ShowProjects extends BaseActivity {

	private String TAG = "ShowProjects";
	private RecyclerView recyclerView;
	private ProjectsAdapter mAdapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_projects);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

        SharedPreferences userInfo = getSharedPreferences("USERID", 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userInfo2 = getSharedPreferences("USERID", 0);
                if (!userInfo2.getString("userRole", "NotSignedIn").equals("teamlid")) {
                    Intent i = new Intent(ShowProjects.this, CreateProject.class);
                    startActivity(i);
                }
            }
        });

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view_projects);

		mAdapter = new ProjectsAdapter(userInfo.getString("userId", "NotSignedIn"));
		RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(mAdapter);
	}
    
	@Override
	public void onResume(){ super.onResume(); }
	
	@Override
	public void onPause(){
		super.onPause();
	}

}
