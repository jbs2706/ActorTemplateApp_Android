package nl.hu.fed.actortemplateapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;
import android.view.View.OnClickListener;
import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.adapters.ArchiveActorsAdapter;
import nl.hu.fed.actortemplateapp.adapters.ArchivePersonsAdapter;
import nl.hu.fed.actortemplateapp.adapters.ArchiveProjectsAdapter;

public class Archive extends AppCompatActivity {

    private RecyclerView recyclerViewProjectArchive, recyclerViewActorArchive, recyclerViewPersonArchive;
    private ArchiveProjectsAdapter archiveProjectsAdapter;
    private ArchiveActorsAdapter archiveActorsAdapter;
    private ArchivePersonsAdapter archivePersonsAdapter;
    private ViewFlipper viewFlipper;
    private ToggleButton showProjects, showActors, showPersons;
    private static final String TAG = "Archive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewProjectArchive = (RecyclerView) findViewById(R.id.rvProjectArchive);
        archiveProjectsAdapter = new ArchiveProjectsAdapter();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerViewProjectArchive.setLayoutManager(mLayoutManager1);
        recyclerViewProjectArchive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProjectArchive.setAdapter(archiveProjectsAdapter);

        recyclerViewActorArchive = (RecyclerView) findViewById(R.id.rvActorArchive);
        archiveActorsAdapter = new ArchiveActorsAdapter();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerViewActorArchive.setLayoutManager(mLayoutManager2);
        recyclerViewActorArchive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActorArchive.setAdapter(archiveActorsAdapter);

        recyclerViewPersonArchive = (RecyclerView) findViewById(R.id.rvPersonArchive);
        archivePersonsAdapter = new ArchivePersonsAdapter();
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        recyclerViewPersonArchive.setLayoutManager(mLayoutManager3);
        recyclerViewPersonArchive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPersonArchive.setAdapter(archivePersonsAdapter);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipperArchive);

        showProjects = (ToggleButton) findViewById(R.id.tbShowProjectArchive);
        showProjects.setOnClickListener(tb_listener);
        showActors = (ToggleButton) findViewById(R.id.tbShowActorArchive);
        showActors.setOnClickListener(tb_listener);
        showPersons = (ToggleButton) findViewById(R.id.tbShowPersonArchive);
        showPersons.setOnClickListener(tb_listener);

        //TODO create fuctions on FAB's to delete and restore. Make a item selectable

    }

    private OnClickListener tb_listener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tbShowProjectArchive:
                    viewFlipper.setDisplayedChild(0);
                    break;
                case R.id.tbShowActorArchive:
                    viewFlipper.setDisplayedChild(1);
                    break;
                case R.id.tbShowPersonArchive:
                    viewFlipper.setDisplayedChild(2);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
