package com.safetymate.safetymate;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

/**
 * Created by RockerZ on 2/11/15.
 */
public class Alert{
    Context context;
    public Alert(Context context)
    {
        this.context = context;
    }
    public void sendNotification(){
        int notificationId = 001;
        // Create an intent for the reply action
        Intent actionIntent = new Intent(this.context, Alert.class);
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(context, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.danger,
                        "Cancel", actionPendingIntent)
                        .build();
        long[] pattern = {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};

        // Build the notification and add the action via WearableExtender
        Notification notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.danger)
                        .setContentTitle("Safety Mate")
                        .setContentText("Are you in Danger? Swipe right to stop sending notification")
                        .extend(new WearableExtender().addAction(action))
                        .setVibrate(pattern)
                        .build();
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Issue the notification with notification manager.
        notificationManager.notify(notificationId, notification);

    }
}
