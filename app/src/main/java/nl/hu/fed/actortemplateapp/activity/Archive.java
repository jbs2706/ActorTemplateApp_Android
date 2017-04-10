package nl.hu.fed.actortemplateapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.adapters.ArchivedActorsAdapter;
import nl.hu.fed.actortemplateapp.adapters.ArchivedPersonsAdapter;
import nl.hu.fed.actortemplateapp.adapters.ArchivedProjectsAdapter;

public class Archive extends AppCompatActivity {

    private RecyclerView recyclerViewProjectArchive, recyclerViewActorArchive, recyclerViewPersonArchive;
    private ArchivedProjectsAdapter archiveProjectsAdapter;
    private ArchivedActorsAdapter archiveActorsAdapter;
    private ArchivedPersonsAdapter archivePersonsAdapter;
    private ViewFlipper viewFlipper;
    private ToggleButton showProjects, showActors, showPersons;
    private static final String TAG = "Archive";

    @Override //bevat drie recyclerviews (gearchiveerde personen, projecten en actors)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewProjectArchive = (RecyclerView) findViewById(R.id.aArchive_projectArchiveRv);
        archiveProjectsAdapter = new ArchivedProjectsAdapter();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerViewProjectArchive.setLayoutManager(mLayoutManager1);
        recyclerViewProjectArchive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProjectArchive.setAdapter(archiveProjectsAdapter);

        recyclerViewActorArchive = (RecyclerView) findViewById(R.id.aArchive_actorArchiveRv);
        archiveActorsAdapter = new ArchivedActorsAdapter();
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerViewActorArchive.setLayoutManager(mLayoutManager2);
        recyclerViewActorArchive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewActorArchive.setAdapter(archiveActorsAdapter);

        recyclerViewPersonArchive = (RecyclerView) findViewById(R.id.aArchive_personArchiveRv);
        archivePersonsAdapter = new ArchivedPersonsAdapter();
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        recyclerViewPersonArchive.setLayoutManager(mLayoutManager3);
        recyclerViewPersonArchive.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPersonArchive.setAdapter(archivePersonsAdapter);

        viewFlipper = (ViewFlipper) findViewById(R.id.aArchive_archiveViewflipper);

        showProjects = (ToggleButton) findViewById(R.id.aArchive_showProjectArchiveTb);
        showProjects.setOnClickListener(tb_listener);
        showActors = (ToggleButton) findViewById(R.id.aArchive_showActorArchiveTb);
        showActors.setOnClickListener(tb_listener);
        showPersons = (ToggleButton) findViewById(R.id.aArchive_showPersonArchiveTb);
        showPersons.setOnClickListener(tb_listener);

        //TODO create fuctions on FAB's to delete and restore. Make a item selectable

    }
    //viewflipper wordt gebruikt in combinatie met togglebuttons om van recyclerview te switchen
    private OnClickListener tb_listener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.aArchive_showProjectArchiveTb:
                    viewFlipper.setDisplayedChild(0);
                    break;
                case R.id.aArchive_showActorArchiveTb:
                    viewFlipper.setDisplayedChild(1);
                    break;
                case R.id.aArchive_showPersonArchiveTb:
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
