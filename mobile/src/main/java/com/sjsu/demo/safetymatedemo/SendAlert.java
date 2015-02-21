package com.sjsu.demo.safetymatedemo;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.TimerTask;

public class SendAlert extends TimerTask implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Context context;
    int notificationId;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected static double latitude, longitude;
    DBConnection dbConn;

    public SendAlert(int notificationId)
    {
        this.context = SafetyMate.getContext();
        this.notificationId = notificationId;
    }
    @Override
    public void run() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
        //TODO store values in the database
        buildGoogleApiClient();
        dbConn = new DBConnection(this.context);
        if(!(mGoogleApiClient.isConnected())){
            mGoogleApiClient.connect();
        }
        List<String> phoneNumbers = dbConn.getContacts();
        String[] numArray = phoneNumbers.toArray(new String[phoneNumbers.size()]);
        Intent sendIntent= new Intent(this.context, SendAlertService.class);
        sendIntent.putExtra("numArray", numArray);
        sendIntent.putExtra("latitude",latitude);
        sendIntent.putExtra("longitude",longitude);
        this.context.startService(sendIntent);

        //Send messages to emergency contacts
        /*
        Notification notification =
                new NotificationCompat.Builder(this.context)
                        .setContentTitle("Safety Mate")
                        .setContentText("Message sent to emergency contacts")
                        .setAutoCancel(true)
                        .setVisibility(1) //public visibility
                        .build();
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Issue the notification with notification manager.
        notificationManager.notify(2, notification);
         */
        //Start Navigation
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("MainActivity", "Latitude is " + mLastLocation.getLatitude());
            latitude = mLastLocation.getLatitude();
            Log.d("MainActivity","Longitude is "+mLastLocation.getLongitude());
            longitude = mLastLocation.getLongitude();
        } else {
            Toast.makeText(this.context, "No location detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
