package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.adapters.ProjectsAdapter;

public class ShowAllProjects extends BaseActivity {

	private static final String TAG = "ShowAllProjects";
	private RecyclerView recyclerView;
	private ProjectsAdapter mAdapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_projects);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.aAllProjects_NewProjectFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userRoleInfo = getSharedPreferences("USERID", 0);
                if (!userRoleInfo.getString("userRole", "NotSignedIn").equals("teamlid")) {
                    Intent i = new Intent(ShowAllProjects.this, CreateProject.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.analystAction), Toast.LENGTH_SHORT).show();
                }
            }
        });

		SharedPreferences userInfo = getSharedPreferences("USERID", 0);
		recyclerView = (RecyclerView) findViewById(R.id.aAllProjects_allProjectsRv);
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
