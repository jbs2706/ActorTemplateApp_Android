package nl.hu.fed.actortemplateapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.camera.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;
import nl.hu.fed.actortemplateapp.domain.Project;

public class CreatePerson extends BaseActivity {
    private EditText nameET, emailET, functionET, phonenumberET, notesET;
    private static final int SELECT_PHOTO = 100, TAKE_PHOTO = 200;
    private ImageView photoIV;
    private CameraFunctions cameraFunctions = new CameraFunctions();
    private DatabaseReference mDatabase;
    private static final String TAG = "CreatePerson";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameET = (EditText) findViewById(R.id.aCreatePerson_nameEt);
        emailET = (EditText) findViewById(R.id.aCreatePerson_emailEt);
        functionET = (EditText) findViewById(R.id.aCreatePerson_functionEt);
        phonenumberET = (EditText) findViewById(R.id.aCreatePerson_phonenumberEt);
        notesET = (EditText) findViewById(R.id.aCreatePerson_notesEt);
        photoIV = (ImageView) findViewById(R.id.aCreatePerson_photoIv);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String projectName = intent.getStringExtra("project"); //worden bovenaan scherm getoond
        String actorName = intent.getStringExtra("actor");
        TextView projectAndActorTV = (TextView) findViewById(R.id.aCreatePerson_projectActorTv);
        projectAndActorTV.setText("Project: " + projectName + " -  Actor: " + actorName);

        FloatingActionButton galleryFab = (FloatingActionButton) findViewById(R.id.aCreatePerson_galleryFab);
        galleryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //open de gallery
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, SELECT_PHOTO);
            }
        });

        FloatingActionButton cameraFab = (FloatingActionButton) findViewById(R.id.aCreatePerson_cameraFab);
        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //open de camera
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
            }
        });

        FloatingActionButton saveFab = (FloatingActionButton) findViewById(R.id.aCreatePerson_saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nameET.getText().toString())) { //valideer naam
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.emptyPersonName), Toast.LENGTH_SHORT).show();
                } //valideer email
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
                } else {
                    Person person = new Person();
                    person.setName(nameET.getText().toString());
                    person.setEmail(emailET.getText().toString());
                    person.setFunction(functionET.getText().toString());
                    person.setPhonenumber(phonenumberET.getText().toString());
                    person.setNotes(notesET.getText().toString());

                    if (photoIV.getDrawable() == null) { //als er geen foto genomen of geselecteerd is, standaard image gebruiken
                        person.setPhoto(cameraFunctions.fromImageToString((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.defaultpersonphoto, null)));
                    } else {
                        person.setPhoto(cameraFunctions.fromImageToString((BitmapDrawable) photoIV.getDrawable()));
                    }

                    Intent intent = getIntent();
                    person.setActorKey(intent.getStringExtra("key"));

                    SharedPreferences userInfo = getSharedPreferences("USERID", 0);
                    person.setAnalist(userInfo.getString("userId", "NotSignedIn"));

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("persons").push().setValue(person);

                    finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            Bitmap processedImage = null;
            if (intentData != null) {
                try { //haal de geselecteerde foto op
                    processedImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), intentData.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            photoIV.setImageBitmap(processedImage);
        }
        if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            cameraFunctions.verifyStoragePermissions(this); //proces de genomen foto
            photoIV.setImageBitmap(cameraFunctions.processCapturedImage(intentData));
        }
    }
}