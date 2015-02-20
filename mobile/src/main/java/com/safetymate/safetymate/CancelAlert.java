package com.safetymate.safetymate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by RockerZ on 2/14/15.
 */
public class CancelAlert extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notificationId",0);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notificationId);
        Log.i("Notification Id",""+notificationId);
        //TODO Store values in the database. Get DetectedActivity and heartrate from SafetyMate class
    }
}
