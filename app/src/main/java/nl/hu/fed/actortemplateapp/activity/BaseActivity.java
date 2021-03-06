package nl.hu.fed.actortemplateapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nl.hu.fed.actortemplateapp.R;
//dit is een basisactivity waarin het basismenu geinitialiseerd wordt. Andere activities kunnen deze extenden.
//op deze manier hoeft het menu niet in elke activity geinitialiseerd te worden
public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private static final String TAG = "BaseActivity";
    public static final String ANONYMOUS = "anonymous";
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_navigation, menu);
        return true;
    }

    @Override //init het basismenu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                startActivity(new Intent(this, MyAccount.class));
                return true;

            case R.id.all_projects:
                startActivity(new Intent(this, ShowAllProjects.class));
                return true;

            case R.id.add_project:
                startActivity(new Intent(this, CreateProject.class));
                return true;

            case R.id.archive:
                startActivity(new Intent(this, Archive.class));
                return true;

            case R.id.sign_out:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                //mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

}
