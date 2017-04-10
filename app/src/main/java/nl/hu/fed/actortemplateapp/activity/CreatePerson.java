package nl.hu.fed.actortemplateapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import nl.hu.fed.actortemplateapp.R;
import nl.hu.fed.actortemplateapp.camera.CameraFunctions;
import nl.hu.fed.actortemplateapp.domain.Person;

public class CreatePerson extends AppCompatActivity {
    private EditText nameET, emailET, functionET, phonenumberET, notesET;
    private static final int SELECT_PHOTO = 100, TAKE_PHOTO = 200;
    private ImageView photoIV;
    private ImageButton imageButtonCamera, imageButtonGallery;
    private CameraFunctions cameraFunctions = new CameraFunctions();
    private DatabaseReference mDatabase;
    private static final String TAG = "CreatePerson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        addListenerOnImageButtons(); //make imagebutton do something
    }

    public void addListenerOnImageButtons() {
        imageButtonGallery = (ImageButton) findViewById(R.id.aCreatePerson_galleryIb);
        imageButtonCamera = (ImageButton) findViewById(R.id.aCreatePerson_cameraTv);
        //open de gallery
        imageButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                startActivityForResult(pickIntent, SELECT_PHOTO);
            }
        });
        //open de camera
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
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
            if(TextUtils.isEmpty(nameET.getText().toString())) { //valideer naam
                Toast.makeText(this, this.getString(R.string.emptyPersonName), Toast.LENGTH_SHORT).show();
            } //valideer email
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()) {
                Toast.makeText(this, this.getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
            }
            else {
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
                person.setAnalyst(userInfo.getString("userId", "NotSignedIn"));

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("persons").push().setValue(person);

                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}