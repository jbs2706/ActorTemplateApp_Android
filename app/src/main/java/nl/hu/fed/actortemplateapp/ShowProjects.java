package nl.hu.fed.actortemplateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

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

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ShowProjects.this, CreateProject.class );
				startActivity(i);
			}
		});

		//add a OnItemClickListener
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view_projects);

		mAdapter = new ProjectsAdapter();
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
