package com.sjsu.demo.safetymatedemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

public class MainActivity extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener{

    private static final int SETTINGS_RESULT = 1;
    private String DEBUG_TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected static double latitude, longitude;
    DBConnection dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SafetyMate.setContext(this);
        dbConn = new DBConnection(this);
        buildGoogleApiClient();
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //On button click, open the Settings Fragment
    public void editUserSettings(View view){

        Intent i = new Intent(this, LaunchSettings.class);
        startActivityForResult(i, SETTINGS_RESULT);

    }


    //On button click, open EmergencyContacts activity
    public void openContacts(View view) {
        Intent intent = new Intent(this, EmergencyContacts.class);
        startActivity(intent);
    }

    // To get stored user preferences
    public void getUserPreferences(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean fbIntegration = mySharedPreferences.getBoolean("prefFacebookPost", false);
        Log.d(DEBUG_TAG,"Facebook value "+fbIntegration);
    }

    //On button click, start SendAlertService
    public void issueAlert(View view){
        if(!(mGoogleApiClient.isConnected())){
            mGoogleApiClient.connect();
        }
        List<String> phoneNumbers = dbConn.getContacts();
        String[] numArray = phoneNumbers.toArray(new String[phoneNumbers.size()]);
        Intent sendIntent= new Intent(this, SendAlertService.class);
        sendIntent.putExtra("numArray", numArray);
        sendIntent.putExtra("latitude",latitude);
        sendIntent.putExtra("longitude",longitude);
        this.startService(sendIntent);
        //Toast.makeText(this,"Lat is "+latitude,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("MainActivity","Latitude is "+mLastLocation.getLatitude());
            latitude = mLastLocation.getLatitude();
            Log.d("MainActivity","Longitude is "+mLastLocation.getLongitude());
            longitude = mLastLocation.getLongitude();
        } else {
            Toast.makeText(this, "No location detected", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Jeena", "Connection suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Jeena", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }
}
