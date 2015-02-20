package com.safetymate.safetymate;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

public class ActivityDetector implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    GoogleApiClient mGoogleApiClient;
    Context context;
    PendingIntent pendingActivityIntent;

    public ActivityDetector()
    {
        this.context = SafetyMate.getContext();
    }
    public void startActivityDetection()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }
    public void StopDetection()
    {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleApiClient,pendingActivityIntent);
    }
    @Override
    public void onConnected(Bundle bundle) {
        Intent activityIntent = new Intent(this.context,ActivityDetectorService.class);
        pendingActivityIntent = PendingIntent.getService(this.context,0,activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient,0,pendingActivityIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleApiClient,pendingActivityIntent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
